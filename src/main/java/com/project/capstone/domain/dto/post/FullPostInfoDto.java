package com.project.capstone.domain.dto.post;

import com.project.capstone.domain.dto.RecommendDto;
import com.project.capstone.domain.dto.comment.CommentResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class FullPostInfoDto {
    PostResponseDto postResponseDto;
    List<CommentResponseDto> commentResponseDtoList;
    RecommendDto recommendDto;
}
