package com.project.capstone.domain.dto.comment;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long postId;
    private String body;
}
