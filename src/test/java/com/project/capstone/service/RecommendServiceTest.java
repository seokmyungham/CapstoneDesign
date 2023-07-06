package com.project.capstone.service;

import com.project.capstone.domain.dto.RecommendDto;
import com.project.capstone.domain.entity.Member;
import com.project.capstone.domain.entity.Post;
import com.project.capstone.domain.entity.Recommend;
import com.project.capstone.repository.MemberRepository;
import com.project.capstone.repository.PageRepository;
import com.project.capstone.repository.RecommendRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class RecommendServiceTest {

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
    RecommendService recommendService;

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
    void allRecommend_내가_누른_추천이_존재할_경우() {
        //given
        Long memberId = 1L;
        Long postId = 1L;

        Member member2 = Member.builder()
                .id(2L)
                .build();

        Member member3 = Member.builder()
                .id(3L)
                .build();

        Recommend recommend1 = Recommend.createRecommend(member1, post);
        Recommend recommend2 = Recommend.createRecommend(member2, post);
        Recommend recommend3 = Recommend.createRecommend(member3, post);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(recommendRepository.findAllByPost(post)).thenReturn(List.of(recommend1, recommend2, recommend3));

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member1));

        //when
        RecommendDto recommendDto = recommendService.allRecommend(postId);

        //then
        Assertions.assertThat(recommendDto.getRecommendNum()).isEqualTo(3);
        Assertions.assertThat(recommendDto.isRecommended()).isTrue();
    }

    @Test
    void allRecommend_내가_누른_추천이_존재하지_않을_경우() {
        //given
        Long memberId = 1L;
        Long postId = 1L;

        Member member2 = Member.builder()
                .id(2L)
                .build();

        Member member3 = Member.builder()
                .id(3L)
                .build();

        Recommend recommend2 = Recommend.createRecommend(member2, post);
        Recommend recommend3 = Recommend.createRecommend(member3, post);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(recommendRepository.findAllByPost(post)).thenReturn(List.of(recommend2, recommend3));

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member1));

        //when
        RecommendDto recommendDto = recommendService.allRecommend(postId);

        //then
        Assertions.assertThat(recommendDto.getRecommendNum()).isEqualTo(2);
        Assertions.assertThat(recommendDto.isRecommended()).isFalse();
    }

    @Test
    void allRecommend_로그인_정보가_유효하지_않을_시_isRecommended는_항상_false를_반환() {
        //given
        Long memberId = 1L;
        Long postId = 1L;

        Member member2 = Member.builder()
                .id(2L)
                .build();

        Member member3 = Member.builder()
                .id(3L)
                .build();

        Recommend recommend1 = Recommend.createRecommend(member1, post);
        Recommend recommend2 = Recommend.createRecommend(member2, post);
        Recommend recommend3 = Recommend.createRecommend(member3, post);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(recommendRepository.findAllByPost(post)).thenReturn(List.of(recommend1, recommend2, recommend3));

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn("anonymousUser");

        //when
        RecommendDto recommendDto = recommendService.allRecommend(postId);

        //then
        Assertions.assertThat(recommendDto.getRecommendNum()).isEqualTo(3);
        Assertions.assertThat(recommendDto.isRecommended()).isFalse();
    }

    @Test
    void allRecommend_추천_수가_0개_입니다() {
        //given
        Long memberId = 1L;
        Long postId = 1L;

        Member member2 = Member.builder()
                .id(2L)
                .build();

        Member member3 = Member.builder()
                .id(3L)
                .build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(recommendRepository.findAllByPost(post)).thenReturn(List.of());

        //when
        RecommendDto recommendDto = recommendService.allRecommend(postId);

        //then
        Assertions.assertThat(recommendDto.getRecommendNum()).isEqualTo(0);
    }

    @Test
    void createRecommend_성공() {
        //given
        Long memberId = 1L;
        Long postId = 1L;

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member1));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        Recommend recommend = Recommend.createRecommend(member1, post);

        //when
        recommendService.createRecommend(postId);

        //then
        ArgumentCaptor<Recommend> recommendCaptor = ArgumentCaptor.forClass(Recommend.class);
        verify(recommendRepository).save(recommendCaptor.capture());
        Recommend value = recommendCaptor.getValue();

        Assertions.assertThat(value.getMember()).isEqualTo(recommend.getMember());
        Assertions.assertThat(value.getPost()).isEqualTo(recommend.getPost());
    }

    @Test
    void createRecommend_로그인한_유저의_정보가_DB에_없을_경우() {
        //given
        Long memberId = 1L;
        Long postId = 1L;

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        //when & then
        Assertions.assertThatThrownBy(() -> recommendService.createRecommend(postId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("로그인 유저 정보가 없습니다");
    }

    @Test
    void createRecommend_게시물이_DB에_존재하지_않는경우() {
        //given
        Long memberId = 1L;
        Long postId = 1L;

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member1));
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        //when & then
        Assertions.assertThatThrownBy(() -> recommendService.createRecommend(postId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("글이 없습니다");
    }

    @Test
    void removeRecommend_성공() {
        //given
        Long memberId = 1L;
        Long postId = 1L;

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member1));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        Member member2 = Member.builder()
                .id(2L)
                .build();

        Member member3 = Member.builder()
                .id(3L)
                .build();

        Recommend recommend1 = Recommend.createRecommend(member1, post);
        Recommend recommend2 = Recommend.createRecommend(member2, post);
        Recommend recommend3 = Recommend.createRecommend(member3, post);

        when(recommendRepository.findAllByPost(post)).thenReturn(List.of(recommend1, recommend2, recommend3));

        //when
        recommendService.removeRecommend(postId);

        //then
        verify(recommendRepository).delete(recommend1);
    }

    @Test
    void removeRecommend_게시글에_자신이_한_추천이_없는경우_추천을_취소할시_에러발생() {
        //given
        Long memberId = 1L;
        Long postId = 1L;

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member1));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        Member member2 = Member.builder()
                .id(2L)
                .build();

        Member member3 = Member.builder()
                .id(3L)
                .build();

        Recommend recommend2 = Recommend.createRecommend(member2, post);
        Recommend recommend3 = Recommend.createRecommend(member3, post);

        when(recommendRepository.findAllByPost(post)).thenReturn(List.of(recommend2, recommend3));

        //when & then
        Assertions.assertThatThrownBy(() -> recommendService.removeRecommend(postId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("추천이 없습니다.");
    }
}