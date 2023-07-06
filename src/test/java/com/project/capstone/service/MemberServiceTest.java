package com.project.capstone.service;

import com.project.capstone.domain.dto.member.MemberResponseDto;
import com.project.capstone.domain.entity.Member;
import com.project.capstone.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    MemberService memberService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getMyInfoBySecurity_로그인_유저_정보_조회_성공() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("test@example.com")
                .password("password")
                .nickname("test")
                .image("test.jpg")
                .introduction("hello")
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken("1", null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        //when
        MemberResponseDto result = memberService.getMyInfoBySecurity();

        //then
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test@example.com");
        assertThat(result.getNickname()).isEqualTo("test");
    }

    @Test
    void getMyInfoBySecurity_로그인_유저_정보_조회_실패() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("test@example.com")
                .password("password")
                .nickname("test")
                .image("test.jpg")
                .introduction("hello")
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken("2", null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        //when & then
        assertThatThrownBy(() -> memberService.getMyInfoBySecurity())
                .isInstanceOf(RuntimeException.class)
                .hasMessage("로그인 유저 정보가 없습니다.");
    }

    @Test
    void getMemberInfo_일치하는_닉네임이_존재() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("test@example.com")
                .password("password")
                .nickname("test")
                .image("test.jpg")
                .introduction("hello")
                .build();

        when(memberRepository.findByNickname(member.getNickname())).thenReturn(Optional.of(member));

        //when
        MemberResponseDto result = memberService.getMemberInfo(member.getNickname());

        //then
        assertThat(result.getNickname()).isEqualTo(member.getNickname());
        verify(memberRepository, times(1)).findByNickname(member.getNickname());
    }

    @Test
    void getMemberInfo_일치하는_닉네임이_없음() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("test@example.com")
                .password("password")
                .nickname("test")
                .image("test.jpg")
                .introduction("hello")
                .build();

        when(memberRepository.findByNickname(member.getNickname())).thenReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> memberService.getMemberInfo(member.getNickname()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("일치하는 닉네임이 없습니다.");
    }

    @Test
    void searchMemberList_닉네임_단어를_포함하고_있는_회원_리스트_존재() {
        //given
        Member member1 = Member.builder()
                .id(1L)
                .email("test1@example.com")
                .password("password")
                .nickname("test12")
                .image("test1.jpg")
                .introduction("hello1")
                .build();

        Member member2 = Member.builder()
                .id(1L)
                .email("test2@example.com")
                .password("password")
                .nickname("test4545")
                .image("test2.jpg")
                .introduction("hello2")
                .build();

        when(memberRepository.findByNicknameContains("test")).thenReturn(List.of(member1, member2));

        //when
        List<MemberResponseDto> result = memberService.searchMemberList("test");

        //then
        assertThat(result.size()).isEqualTo(2);

        MemberResponseDto responseDto1 = result.get(0);
        assertThat(responseDto1.getNickname()).isEqualTo(member1.getNickname());

        MemberResponseDto responseDto2 = result.get(1);
        assertThat(responseDto2.getNickname()).isEqualTo(member2.getNickname());
    }

    @Test
    void searchMemberList_닉네임_단어를_포함하고_있는_회원_리스트_존재X() {
        //given
        when(memberRepository.findByNicknameContains("test")).thenReturn(Collections.emptyList());

        //when
        List<MemberResponseDto> result = memberService.searchMemberList("test");

        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void changeMemberNickname_닉네임_변경() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("test@example.com")
                .password("password")
                .nickname("test")
                .image("test.jpg")
                .introduction("hello")
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken("1", null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        //when
        String newNickname = "jjanggu";
        MemberResponseDto responseDto = memberService.changeMemberNickname(newNickname);

        //then
        assertThat(responseDto.getNickname()).isEqualTo(newNickname);
    }

    @Test
    void changeMemberPassword_비밀번호_변경_비밀번호_불일치() {
        // given
        String exPassword = "oldPassword";
        String newPassword = "newPassword";

        Member member = Member.builder()
                .id(1L)
                .email("test@example.com")
                .password(passwordEncoder.encode("differentPassword"))
                .nickname("test")
                .image("test.jpg")
                .introduction("hello")
                .build();

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken("1", null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(exPassword, member.getPassword())).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> memberService.changeMemberPassword(exPassword, newPassword))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("비밀번호가 맞지 않습니다");
    }

    @Test
    void changeMemberPassword_비밀번호_변경_성공() {
        //given
        String exPassword = "password";
        String newPassword = "password@123";
        String encodedPassword = "암호화된_비밀번호";
        String newEncodedPassword = "암호화된_비밀번호2";

        Member member = Member.builder()
                .id(1L)
                .email("test@example.com")
                .password(passwordEncoder.encode(exPassword))
                .nickname("test")
                .image("test.jpg")
                .introduction("hello")
                .build();

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken("1", null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        when(passwordEncoder.encode(exPassword)).thenReturn(encodedPassword);
        when(passwordEncoder.encode(newPassword)).thenReturn(newEncodedPassword);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(exPassword, member.getPassword())).thenReturn(true);
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        //when
        MemberResponseDto result = memberService.changeMemberPassword(exPassword, newPassword);

        //then
        Member updatedMember = memberRepository.findById(1L).orElseThrow();
        assertThat(newEncodedPassword).isEqualTo(updatedMember.getPassword());
    }

    @Test
    void changeMemberImage_이미지_변경() {
        //given
        Member member = Member.builder()
                .id(1L)
                .email("test@example.com")
                .password("password")
                .nickname("test")
                .image("test.jpg")
                .introduction("hello")
                .build();

        String newImage = "newImage.jpg";

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken("1", null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        //when
        MemberResponseDto responseDto = memberService.changeMemberImage(newImage);

        //then
        assertThat(responseDto.getImage()).isEqualTo(newImage);
        verify(memberRepository, times(1)).findById(1L);
        verify(memberRepository, times(1)).save(member);
    }
}