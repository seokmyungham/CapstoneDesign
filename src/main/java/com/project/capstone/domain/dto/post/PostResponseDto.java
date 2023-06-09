package com.project.capstone.domain.dto.post;

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
    private String memberImage;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
    private boolean isWritten;

    public static PostResponseDto of(Post post, boolean bool) {
        String createdAt = post.getCreatedDate() != null ?
                post.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

        String updatedAt = post.getCreatedDate() != null ?
                post.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

        return PostResponseDto.builder()
                .postId(post.getId())
                .memberNickName(post.getMember().getNickname())
                .memberImage(post.getMember().getImage())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .isWritten(bool)
                .build();
    }
}
