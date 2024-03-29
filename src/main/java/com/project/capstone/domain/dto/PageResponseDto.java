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
    private String memberImage;
    private String postContent;
    private String createdAt;
    private RecommendDto recommendDto;

    public static PageResponseDto of(Post post, RecommendDto recommendDto) {
        String createdAt = post.getCreatedDate() != null ?
                post.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

        return PageResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getTitle())
                .postContent(post.getContent())
                .memberNickname(post.getMember().getNickname())
                .memberImage(post.getMember().getImage())
                .createdAt(createdAt)
                .recommendDto(recommendDto)
                .build();
    }

}
