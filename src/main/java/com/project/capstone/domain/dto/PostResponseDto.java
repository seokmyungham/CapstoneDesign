package com.project.capstone.domain.dto;

import com.project.capstone.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponseDto {
    private Long postId;
    private String memberNickName;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
    private boolean isWritten;

    public static PostResponseDto of(Post post, boolean bool) {
        return PostResponseDto.builder()
                .postId(post.getId())
                .memberNickName(post.getMember().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .updatedAt(post.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .isWritten(bool)
                .build();
    }


}
