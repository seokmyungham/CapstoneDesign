package com.project.capstone.domain.dto;

import com.project.capstone.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {
    private Long commentId;
    private String memberNickName;
    private String commentText;
    private Long createdAt;
    private boolean isWritten;

    public static CommentResponseDto of(Comment comment, boolean bool) {
        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .memberNickName(comment.getMember().getNickname())
                .commentText(comment.getText())
                .createdAt(Timestamp.valueOf(comment.getCreatedDate()).getTime())
                .isWritten(bool)
                .build();
    }


}
