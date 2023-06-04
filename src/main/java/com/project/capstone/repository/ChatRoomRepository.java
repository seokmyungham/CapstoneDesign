package com.project.capstone.repository;

import com.project.capstone.domain.entity.ChatRoom;
import com.project.capstone.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("select cr from ChatRoom cr where (cr.roomMaker = :member1 and cr.guest = :member2) or " +
            "(cr.roomMaker = :member2 and cr.guest = :member1)")
    List<ChatRoom> findByMembers(@Param("member1") Member member1, @Param("member2") Member member2);

    @Query("select cr from ChatRoom cr where cr.roomMaker = :member or cr.guest = :member")
    List<ChatRoom> findChatRoomList(@Param("member") Member member);
}
