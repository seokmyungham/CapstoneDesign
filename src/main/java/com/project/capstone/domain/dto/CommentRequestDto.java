package com.project.capstone.domain.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long postId;
    private String body;
}
