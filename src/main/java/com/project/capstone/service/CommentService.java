package com.project.capstone.service;

import com.project.capstone.config.SecurityUtil;
import com.project.capstone.domain.dto.comment.CommentResponseDto;
import com.project.capstone.domain.entity.Comment;
import com.project.capstone.domain.entity.Member;
import com.project.capstone.domain.entity.Post;
import com.project.capstone.repository.CommentRepository;
import com.project.capstone.repository.MemberRepository;
import com.project.capstone.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    public List<CommentResponseDto> getComment(Post post) {
        List<Comment> comments = post.getComments();
        if (comments.isEmpty()) {
            return Collections.emptyList();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == "anonymousUser") {
            return comments
                    .stream()
                    .map(comment -> CommentResponseDto.of(comment, false))
                    .collect(Collectors.toList());
        } else {
            Member member = memberRepository.findById(Long.parseLong(authentication.getName())).orElseThrow();
            Map<Boolean, List<Comment>> collect = comments.stream()
                    .collect(Collectors.partitioningBy(
                                    comment -> comment.getMember().equals(member)
                            )
                    );
            List<CommentResponseDto> trueCollect = collect.get(true).stream()
                    .map(t -> CommentResponseDto.of(t, true))
                    .collect(Collectors.toList());

            List<CommentResponseDto> falseCollect = collect.get(false).stream()
                    .map(f -> CommentResponseDto.of(f, false))
                    .collect(Collectors.toList());

            return Stream
                    .concat(trueCollect.stream(), falseCollect.stream())
                    .sorted(Comparator.comparing(CommentResponseDto::getCommentId))
                    .collect(Collectors.toList());
        }
    }

    @Transactional
    public CommentResponseDto createComment(Long id, String text) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));

        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시물이 없습니다"));

        Comment comment = Comment.createComment(text, member, post);

        log.info("{} 님이 댓글을 달았습니다. = {}", member.getNickname(), text);
        return CommentResponseDto.of(commentRepository.save(comment), true);
    }

    @Transactional
    public void removeComment(Long id) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("댓글이 없습니다."));
        if (!comment.getMember().equals(member)) {
            throw new RuntimeException("작성자와 로그인이 일치하지 않습니다");
        }
        log.info("{} 님이 댓글을 삭제하였습니다. = {}", member.getNickname(), comment.getText());
        commentRepository.delete(comment);
    }

}
