package com.project.capstone.service;

import com.project.capstone.config.SecurityUtil;
import com.project.capstone.config.dto.MemberResponseDto;
import com.project.capstone.domain.dto.ChatMessageDto;
import com.project.capstone.domain.dto.ChatResponseDto;
import com.project.capstone.domain.entity.ChatMessage;
import com.project.capstone.domain.entity.ChatRoom;
import com.project.capstone.domain.entity.Member;
import com.project.capstone.repository.ChatMessageRepository;
import com.project.capstone.repository.ChatRoomRepository;
import com.project.capstone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public ChatResponseDto chatRoom(String nickname) {
        Member member1 = isMemberCurrent();
        Member member2 = memberRepository.findByNickname(nickname).orElseThrow(() -> new RuntimeException("닉네임을 가진 유저가 없습니다."));

        MemberResponseDto memberInfo = MemberResponseDto.of(member2);

        ChatRoom chatRoom = chatRoomRepository.findByMembers(member1, member2)
                .stream()
                .findFirst()
                .orElseGet(() -> {
                    ChatRoom newChatRoom = ChatRoom.createRoom(member1, member2);
                    chatRoomRepository.save(newChatRoom);
                    return newChatRoom;
                });

        List<ChatMessage> chatHistory = chatMessageRepository.findByChatRoomId(chatRoom.getId());

        if (chatHistory.isEmpty()) {
            return ChatResponseDto.of(chatRoom.getId(), memberInfo, Collections.emptyList());
        }

        List<ChatMessageDto> collect = chatHistory.stream()
                .map(ChatMessageDto::of)
                .collect(Collectors.toList());

        return ChatResponseDto.of(chatRoom.getId(), memberInfo, collect);
    }

    @Transactional
    public void deleteChatRoom(Long id) {
        Member member = isMemberCurrent();
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow(() -> new RuntimeException("채팅 방이 없습니다."));
        if (!chatRoom.getRoomMaker().equals(member) && !chatRoom.getGuest().equals(member)) {
            throw new RuntimeException("채팅 방에 소속된 멤버가 아닙니다.");
        }

        chatRoomRepository.delete(chatRoom);
    }

    public List<ChatResponseDto> getRoomListAndMemberInfo() {
        Member member = isMemberCurrent();
        List<ChatRoom> chatRoomList = chatRoomRepository.findChatRoomList(member);

        List<ChatResponseDto> result = new ArrayList<>();

        for (ChatRoom chatRoom : chatRoomList) {
            Member otherPerson = chatRoom.getRoomMaker().equals(member) ? chatRoom.getGuest() : chatRoom.getRoomMaker();

            Optional<Member> optionalOtherPerson = memberRepository.findById(otherPerson.getId());
            MemberResponseDto memberInfo = optionalOtherPerson.map(MemberResponseDto::of).orElseThrow(() -> new RuntimeException("해당 아이디를 가진 회원이 없습니다."));

            List<ChatMessage> chatHistory = chatMessageRepository.findByChatRoomId(chatRoom.getId());

            if (chatHistory.isEmpty()) {
                result.add(ChatResponseDto.of(chatRoom.getId(), memberInfo, Collections.emptyList()));
                continue;
            }

            List<ChatMessageDto> collect = chatHistory.stream()
                    .map(ChatMessageDto::of)
                    .collect(Collectors.toList());

            result.add(ChatResponseDto.of(chatRoom.getId(), memberInfo, collect));
        }

        return result;
    }

    @Transactional
    public void saveMessage(ChatMessageDto messageDto) {
        ChatRoom chatRoom = chatRoomRepository.findById(messageDto.getChatRoomId()).orElseThrow(() -> new RuntimeException("유효하지 않은 채팅 방입니다."));
        ChatMessage message = ChatMessage.createMessage(messageDto.getContent(), messageDto.getSender(), messageDto.getTimestamp(), chatRoom);
        chatMessageRepository.save(message);
    }

    private Member isMemberCurrent() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }
}
