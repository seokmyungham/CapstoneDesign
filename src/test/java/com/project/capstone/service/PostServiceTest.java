package com.project.capstone.service;

import com.project.capstone.domain.dto.PageResponseDto;
import com.project.capstone.domain.dto.post.PostResponseDto;
import com.project.capstone.domain.entity.Member;
import com.project.capstone.domain.entity.Post;
import com.project.capstone.repository.MemberRepository;
import com.project.capstone.repository.PageRepository;
import com.project.capstone.repository.RecommendRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostServiceTest {

    @Mock
    PageRepository postRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    RecommendRepository recommendRepository;

    @Mock
    SecurityContext securityContext;

    @Mock
    Authentication authentication;

    @InjectMocks
    PostService postService;

    @BeforeEach
    public void setup() {
        SecurityContextHolder.clearContext();
        MockitoAnnotations.openMocks(this);
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

    @Test
    void pageResponse() {
        //given
        List<Post> posts = new ArrayList<>();
        posts.add(Post.createPost("title1.jpg", "content1", member1));
        posts.add(Post.createPost("title2.jpg", "content2", member1));
        when(postRepository.findAll()).thenReturn(posts);

        //when
        List<PageResponseDto> result = postService.pageResponse();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getPostTitle()).isEqualTo("title1.jpg");
        assertThat(result.get(1).getPostTitle()).isEqualTo("title2.jpg");
    }

    @Test
    void onePost_게시물_조회_자신이_작성한_게시물() {
        //given
        Post post = Post.createPost("title1.jpg", "content1", member1);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn("1");
        when(authentication.getName()).thenReturn("1");
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member1));

        //when
        PostResponseDto result = postService.onePost(1L);

        //then
        assertThat(result.getTitle()).isEqualTo("title1.jpg");
        assertThat(result.getContent()).isEqualTo("content1");
        assertThat(result.isWritten()).isTrue();
    }

    @Test
    void onePost_게시물_조회_다른_사용자의_게시물() {
        //given
        Post post = Post.createPost("title1.jpg", "content1", member1);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(securityContext.getAuthentication()).thenReturn(null);

        //when
        PostResponseDto result = postService.onePost(1L);

        //then
        assertThat(result.getTitle()).isEqualTo("title1.jpg");
        assertThat(result.getContent()).isEqualTo("content1");
        assertThat(result.isWritten()).isFalse();
    }

    @Test
    void authorizationPostWriter_자신이_작성한_게시물인지_검사합니다_성공() {
        //given
        Long memberId = 1L;
        Long postId = 1L;

        securityContext = mock(SecurityContext.class);
        authentication = mock(Authentication.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());
        SecurityContextHolder.setContext(securityContext);
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member1));

        Post post = Post.createPost("title1.jpg", "content1", member1);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        //then
        Post result = postService.authorizationPostWriter(postId);

        //when
        assertThat(result).isEqualTo(post);
    }

    @Test
    void authorizationPostWriter_해당_아이디를_가진_게시글이_없을경우() {
        //given
        Long memberId = 1L;
        Long postId = 1L;

        securityContext = mock(SecurityContext.class);
        authentication = mock(Authentication.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());
        SecurityContextHolder.setContext(securityContext);
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member1));

        Post post = Post.createPost("title1.jpg", "content1", member1);
        when(postRepository.findById(2L)).thenReturn(Optional.of(post));

        //when & then
        assertThatThrownBy(() -> postService.authorizationPostWriter(postId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("글이 없습니다");
    }

    @Test
    void authorizationPostWriter_로그인한_유저가_게시글의_주인이_아닐경우() {
        //given
        Long memberId = 1L;
        Long postId = 1L;

        Member member2 = Member.builder()
                .id(2L)
                .build();

        securityContext = mock(SecurityContext.class);
        authentication = mock(Authentication.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());
        SecurityContextHolder.setContext(securityContext);
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member1));

        Post post = Post.createPost("title1.jpg", "content1", member2);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        //when & then
        assertThatThrownBy(() -> postService.authorizationPostWriter(postId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("로그인한 유저와 작성 유저가 같지 않습니다.");
    }
}