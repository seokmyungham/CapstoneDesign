package com.project.capstone.domain.dto.post;

import com.project.capstone.domain.dto.PageResponseDto;
import com.project.capstone.domain.dto.RecommendDto;
import com.project.capstone.domain.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter @Setter @Builder
public class MyPageResponseDto {

    private Long postId;
    private String postTitle;
    private String memberNickname;
    private String memberImage;
    private String postContent;
    private String createdAt;

    public static MyPageResponseDto of(Post post) {
        String createdAt = post.getCreatedDate() != null ?
                post.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

        return MyPageResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getTitle())
                .postContent(post.getContent())
                .memberNickname(post.getMember().getNickname())
                .memberImage(post.getMember().getImage())
                .createdAt(createdAt)
                .build();
    }
}
