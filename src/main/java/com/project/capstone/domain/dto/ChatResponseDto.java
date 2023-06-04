package com.project.capstone.domain.dto;

import com.project.capstone.config.dto.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponseDto {

    private Long chatRoomId;
    private MemberResponseDto memberInfo;
    private List<ChatMessageDto> chatHistory = new ArrayList<>();

    public static ChatResponseDto of(Long chatRoomId, MemberResponseDto memberInfo, List<ChatMessageDto> chatHistory) {
        return ChatResponseDto.builder()
                .chatRoomId(chatRoomId)
                .memberInfo(memberInfo)
                .chatHistory(chatHistory)
                .build();
    }
}
