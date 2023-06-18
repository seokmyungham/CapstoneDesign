package com.project.capstone.domain.dto.member;

import com.project.capstone.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto {
    private String email;
    private String nickname;
    private String image;
    private String introduction;

    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .image(member.getImage())
                .introduction(member.getIntroduction())
                .build();
    }
}
