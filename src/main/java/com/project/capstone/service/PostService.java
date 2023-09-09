package com.project.capstone.service;

import com.project.capstone.config.SecurityUtil;
import com.project.capstone.domain.dto.PageResponseDto;
import com.project.capstone.domain.dto.post.FullPostInfoDto;
import com.project.capstone.domain.dto.post.MyPageResponseDto;
import com.project.capstone.domain.dto.post.PostResponseDto;
import com.project.capstone.domain.entity.Member;
import com.project.capstone.domain.entity.Post;
import com.project.capstone.repository.CommentRepository;
import com.project.capstone.repository.MemberRepository;
import com.project.capstone.repository.PostRepository;
import com.project.capstone.repository.RecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final RecommendRepository recommendRepository;
    private final CommentService commentService;
    private final RecommendService recommendService;

    public Page<PageResponseDto> pagePost(int pageNum) {
        Page<Post> posts = postRepository.searchAll(PageRequest.of(pageNum - 1, 20));

        List<PageResponseDto> pageResponseDtos = posts.getContent()
                .stream()
                .map(post -> PageResponseDto.of(post, recommendService.allRecommend(post.getRecommends())))
                .collect(Collectors.toList());

        return new PageImpl<>(pageResponseDtos, posts.getPageable(), posts.getTotalElements());
    }

    public Page<MyPageResponseDto> myPagePost(String nickname, int pageNum) {
        return postRepository.searchByWriter(nickname, PageRequest.of(pageNum - 1, 9));
    }

    public FullPostInfoDto onePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글이 없습니다"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        FullPostInfoDto fullPostInfoDto = new FullPostInfoDto();
        fullPostInfoDto.setCommentResponseDtoList(commentService.getComment(post));
        fullPostInfoDto.setRecommendDto(recommendService.allRecommend_OnePost(post));

        if (authentication == null || authentication.getPrincipal() == "anonymousUser") {
            fullPostInfoDto.setPostResponseDto(PostResponseDto.of(post, false));
        } else {
            Member member = memberRepository.findById(Long.parseLong(authentication.getName())).orElseThrow();
            boolean result = post.getMember().equals(member);
            fullPostInfoDto.setPostResponseDto(PostResponseDto.of(post, result));
        }

        return fullPostInfoDto;
    }

    @Transactional
    public PostResponseDto createPost(String title, String content) {
        Member member = isMemberCurrent();
        Post post = Post.createPost(title, content, member);
        return PostResponseDto.of(postRepository.save(post), true);
    }

    @Transactional
    public PostResponseDto changePost(Long id, String title, String content) {
        Post post = authorizationPostWriter(id);
        return PostResponseDto.of(postRepository.save(Post.updatePost(post, title, content)), true);
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = authorizationPostWriter(id);
        post.deletePost(post);
        postRepository.delete(post);
    }

    public Member isMemberCurrent() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    public Post authorizationPostWriter(Long id) {
        Member member = isMemberCurrent();
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("글이 없습니다"));
        if (!post.getMember().equals(member)) {
            throw new RuntimeException("로그인한 유저와 작성 유저가 같지 않습니다.");
        }
        return post;
    }
}
