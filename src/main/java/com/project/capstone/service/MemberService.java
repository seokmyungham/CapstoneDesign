package com.project.capstone.service;

import com.project.capstone.config.SecurityUtil;
import com.project.capstone.domain.dto.member.MemberResponseDto;
import com.project.capstone.domain.entity.Member;
import com.project.capstone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponseDto getMyInfoBySecurity() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    public MemberResponseDto getMemberInfo(String nickname) {
        return memberRepository.findByNickname(nickname)
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 닉네임이 없습니다."));
    }

    public List<MemberResponseDto> searchMemberList(String nickname) {
        return memberRepository.findByNicknameContains(nickname)
                .stream()
                .map(MemberResponseDto::of).collect(Collectors.toList());
    }

    @Transactional
    public MemberResponseDto changeMemberNickname(String nickname) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        member.changeNickname(nickname);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    @Transactional
    public MemberResponseDto changeMemberPassword(String exPassword, String newPassword) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        if (!passwordEncoder.matches(exPassword, member.getPassword())) {
            throw new RuntimeException("비밀번호가 맞지 않습니다");
        }
        member.changePassword(passwordEncoder.encode(newPassword));
        return MemberResponseDto.of(memberRepository.save(member));
    }

    @Transactional
    public MemberResponseDto changeMemberImage(String image) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
        member.changeImage(image);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    @Transactional
    public MemberResponseDto changeMemberIntroduction(String introduction) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
        member.changeIntroduction(introduction);
        return MemberResponseDto.of(memberRepository.save(member));
    }
}
