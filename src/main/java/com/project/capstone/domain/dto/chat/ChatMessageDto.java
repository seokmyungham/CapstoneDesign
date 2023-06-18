package com.project.capstone.domain.dto.chat;

import com.project.capstone.domain.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {

    private String content;
    private String sender;
    private LocalDateTime timestamp;
    private Long chatRoomId;

    public static ChatMessageDto of(ChatMessage chatMessage) {
        return ChatMessageDto.builder()
                .content(chatMessage.getContent())
                .sender(chatMessage.getSender())
                .timestamp(chatMessage.getTimestamp())
                .chatRoomId(chatMessage.getChatRoom().getId())
                .build();
    }
}
