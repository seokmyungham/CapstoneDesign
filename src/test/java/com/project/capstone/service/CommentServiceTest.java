package com.project.capstone.service;

import com.project.capstone.domain.dto.comment.CommentResponseDto;
import com.project.capstone.domain.entity.Comment;
import com.project.capstone.domain.entity.Member;
import com.project.capstone.domain.entity.Post;
import com.project.capstone.repository.CommentRepository;
import com.project.capstone.repository.MemberRepository;
import com.project.capstone.repository.PageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CommentServiceTest {

    @Mock
    PageRepository postRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    CommentRepository commentRepository;

    @Mock
    SecurityContext securityContext;

    @Mock
    Authentication authentication;

    @InjectMocks
    CommentService commentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        securityContext = Mockito.mock(SecurityContext.class);
        authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);
    }

    Member member1 = Member.builder()
            .id(1L)
            .email("test@example.com")
            .password("password")
            .nickname("test")
            .image("test.jpg")
            .introduction("hello")
            .build();

    Post post = Post.createPost("title1.jpg", "content1", member1);

    @Test
    void createComment_성공() {
        //given
        Long memberId = 1L;
        Long postId = 1L;
        String commentText = "댓글";

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member1));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        Comment comment = Comment.createComment(commentText, member1, post);

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        //then
        CommentResponseDto result = commentService.createComment(postId, commentText);

        //when
        assertThat(result.getCommentText()).isEqualTo(commentText);
        assertThat(result.getMemberNickName()).isEqualTo(member1.getNickname());
        assertThat(result.isWritten()).isEqualTo(true);
    }

    @Test
    void createComment_로그인한_유저의_정보가_DB에_없을_경우() {
        //given
        Long memberId = 1L;
        Long postId = 1L;
        String commentText = "댓글";

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        Comment comment = Comment.createComment(commentText, member1, post);

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        //when & then
        assertThatThrownBy(() -> commentService.createComment(postId, commentText))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("로그인 유저 정보가 없습니다");
    }

    @Test
    void createComment_게시물이_DB에_존재하지_않는경우() {
        //given
        Long memberId = 1L;
        Long postId = 1L;
        String commentText = "댓글";

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member1));
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        Comment comment = Comment.createComment(commentText, member1, post);

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        //when & then
        assertThatThrownBy(() -> commentService.createComment(postId, commentText))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("게시물이 없습니다");
    }

    @Test
    void removeComment_댓글_삭제를_시도하는_유저와_작성자가_일치하지_않을경우() {
        //given
        Long memberId = 1L;
        Long commentId = 1L;
        String commentText = "댓글";

        Member member2 = Member.builder()
                .id(2L)
                .build();

        Comment comment = Comment.createComment(commentText, member2, post);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member1));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        //when & then
        assertThatThrownBy(() -> commentService.removeComment(commentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("작성자와 로그인이 일치하지 않습니다");
    }
}