package com.project.capstone.config.auth.dto;


import com.project.capstone.domain.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberDto {

    private String name;
    private String email;
    private String picture;
    private Role role;

    @Builder
    public MemberDto(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }
}
