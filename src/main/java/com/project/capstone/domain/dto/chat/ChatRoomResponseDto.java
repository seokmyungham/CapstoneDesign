package com.project.capstone.domain.dto.chat;

import com.project.capstone.domain.dto.member.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomResponseDto {

    private Long chatRoomId;
    private MemberResponseDto memberInfo;
    private ChatMessageDto mostRecentMessage;

    public static ChatRoomResponseDto of(Long chatRoomId, MemberResponseDto memberInfo, ChatMessageDto mostRecentMessage) {
        return ChatRoomResponseDto.builder()
                .chatRoomId(chatRoomId)
                .memberInfo(memberInfo)
                .mostRecentMessage(mostRecentMessage)
                .build();
    }
}
