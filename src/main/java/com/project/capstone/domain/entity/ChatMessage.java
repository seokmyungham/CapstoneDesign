package com.project.capstone.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "chatmessage")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    private String content;

    private String sender;

    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    private void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        chatRoom.getChatMessages().add(this);
    }

    public static ChatMessage createMessage(String content, String sender, LocalDateTime timestamp, ChatRoom chatRoom) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.content = content;
        chatMessage.sender = sender;
        chatMessage.timestamp = timestamp;
        chatMessage.setChatRoom(chatRoom);
        return chatMessage;
    }
}
