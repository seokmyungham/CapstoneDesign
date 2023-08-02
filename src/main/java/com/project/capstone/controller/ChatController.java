package com.project.capstone.controller;

import com.project.capstone.domain.dto.chat.ChatMessageDto;
import com.project.capstone.domain.dto.chat.ChatRequestDto;
import com.project.capstone.domain.dto.chat.ChatResponseDto;
import com.project.capstone.domain.dto.chat.MessageDto;
import com.project.capstone.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/send")
    public void chat(@RequestBody ChatMessageDto messageDto) {
        chatService.saveMessage(messageDto);
        messagingTemplate.convertAndSend("/sub/topic/" + messageDto.getChatRoomId(), messageDto);
        messagingTemplate.convertAndSend("/sub/topic/chatList/" + messageDto.getChatRoomId(), messageDto);
    }

    @PostMapping("/room")
    public ResponseEntity<ChatResponseDto> joinChatRoom(@RequestBody ChatRequestDto requestDto) {
        return ResponseEntity.ok(chatService.chatRoom(requestDto.getNickname()));
    }

    @DeleteMapping("/room")
    public ResponseEntity<MessageDto> deleteChatRoom(@RequestParam(name = "id") Long id) {
        chatService.deleteChatRoom(id);
        return ResponseEntity.ok(new MessageDto("ChatRoom Delete Success"));
    }


    @GetMapping("/list")
    public ResponseEntity<List<ChatResponseDto>> getChatRoomList() {
        return ResponseEntity.ok(chatService.getRoomListAndMemberInfo());
    }
}
