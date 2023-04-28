package com.project.capstone.domain.dto;

import com.project.capstone.domain.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class PageResponseDto {
    private Long postId;
    private String postTitle;
    private String memberNickname;
    private String postContent;
    private String createdAt;

    public static PageResponseDto of(Post post) {
        return PageResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getTitle())
                .memberNickname(post.getMember().getNickname())
                .createdAt(post.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

}
