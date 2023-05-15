package com.project.capstone.domain;

import com.project.capstone.domain.entity.Member;
import com.project.capstone.domain.entity.Role;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    protected void setMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("멤버는 필수 항목입니다.");
        }

        if (member.getRole() != Role.USER) {
            throw new IllegalArgumentException("일반 사용자만 게시글을 생성할 수 있습니다.");
        }
    }
}
