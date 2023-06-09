package com.project.capstone.domain.dto.comment;

import com.project.capstone.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {
    private Long commentId;
    private String memberNickName;
    private String commentText;
    private String createdAt;
    private boolean isWritten;

    public static CommentResponseDto of(Comment comment, boolean bool) {
        String createdAt = comment.getCreatedDate() != null ?
                comment.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .memberNickName(comment.getMember().getNickname())
                .commentText(comment.getText())
                .createdAt(createdAt)
                .isWritten(bool)
                .build();
    }
}
