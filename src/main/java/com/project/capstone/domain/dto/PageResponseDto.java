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
    private String content;
    private String memberNickname;
    private String memberImage;
    private String memberIntroduction;
    private String postContent;
    private String createdAt;

    public static PageResponseDto of(Post post) {
        return PageResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getTitle())
                .postContent(post.getContent())
                .memberNickname(post.getMember().getNickname())
                .memberImage(post.getMember().getImage())
                .memberIntroduction(post.getMember().getIntroduction())
                .createdAt(post.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

}
