package com.project.capstone.repository;

import com.project.capstone.domain.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByChatRoomId(Long chatRoomId);
    Optional<ChatMessage> findFirstByChatRoomIdOrderByTimestampDesc(Long chatRoomId);
}
