package com.project.capstone.service;

import com.project.capstone.config.SecurityUtil;
import com.project.capstone.domain.dto.PageResponseDto;
import com.project.capstone.domain.dto.PostResponseDto;
import com.project.capstone.domain.entity.Member;
import com.project.capstone.domain.entity.Post;
import com.project.capstone.repository.MemberRepository;
import com.project.capstone.repository.PageRepository;
import com.project.capstone.repository.RecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final PageRepository postRepository;
    private final MemberRepository memberRepository;
    private final RecommendRepository recommendRepository;

    public List<PageResponseDto> pageResponse() {
        List<Post> posts = postRepository.findAll();
        return posts
                .stream()
                .map(PageResponseDto::of)
                .collect(Collectors.toList());
    }

    public Page<PageResponseDto> pagePost(int pageNum) {
        return postRepository.searchAll(PageRequest.of(pageNum - 1, 20));
    }

    public PostResponseDto onePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("글이 없습니다"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == "anonymousUser") {
            return PostResponseDto.of(post, false);
        } else {
            Member member = memberRepository.findById(Long.parseLong(authentication.getName())).orElseThrow();
            boolean result = post.getMember().equals(member);
            return PostResponseDto.of(post, result);
        }
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
