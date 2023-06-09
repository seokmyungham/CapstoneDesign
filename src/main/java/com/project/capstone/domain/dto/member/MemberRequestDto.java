package com.project.capstone.domain.dto.member;

import com.project.capstone.domain.entity.Member;
import com.project.capstone.domain.entity.Role;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {
    private String email;
    private String password;
    private String nickname;
    private String image;
    private String introduction;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .image(image)
                .introduction(introduction)
                .role(Role.USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
