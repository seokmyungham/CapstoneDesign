package com.project.capstone.service;

import com.project.capstone.domain.dto.chat.ChatResponseDto;
import com.project.capstone.domain.entity.ChatMessage;
import com.project.capstone.domain.entity.ChatRoom;
import com.project.capstone.domain.entity.Member;
import com.project.capstone.repository.ChatMessageRepository;
import com.project.capstone.repository.ChatRoomRepository;
import com.project.capstone.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatServiceTest {

    @Mock
    ChatRoomRepository chatRoomRepository;

    @Mock
    ChatMessageRepository chatMessageRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    SecurityContext securityContext;

    @Mock
    Authentication authentication;

    @InjectMocks
    ChatService chatService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        securityContext = Mockito.mock(SecurityContext.class);
        authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);
    }

    Member member1 = Member.builder()
            .id(1L)
            .email("test@example.com")
            .password("password")
            .nickname("test")
            .image("test.jpg")
            .introduction("hello")
            .build();

    Member otherPerson = Member.builder()
            .id(1L)
            .email("otherPerson@example.com")
            .password("password")
            .nickname("otherPerson")
            .image("otherPersonImage.jpg")
            .introduction("hello")
            .build();

    @Test
    void chatRoom_상대방의_닉네임을_DB에서_찾지_못했을_경우() {
        //given
        Long memberId = 1L;
        String nickname = "otherPerson";

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member1));
        when(memberRepository.findByNickname(nickname)).thenReturn(Optional.empty());

        //when & then
        Assertions.assertThatThrownBy(() -> chatService.chatRoom(nickname))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("닉네임을 가진 유저가 없습니다.");
    }

    @Test
    void chatRoom_기존의_채팅방이_없을_경우_새로_채팅방을_생성() {
        //given
        Long memberId = 1L;
        String nickname = "otherPerson";

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member1));
        when(memberRepository.findByNickname(nickname)).thenReturn(Optional.of(otherPerson));

        when(chatRoomRepository.findByMembers(member1, otherPerson)).thenReturn(List.of());

        //when & then
        ArgumentCaptor<ChatRoom> chatRoomCaptor = ArgumentCaptor.forClass(ChatRoom.class);
        ChatResponseDto chatResponseDto = chatService.chatRoom(nickname);

        verify(chatRoomRepository, times(1)).save(chatRoomCaptor.capture());
    }

    @Test
    void chatRoom_채팅_내역이_존재하지_않을_경우_emptyList를_반환() {
        //given
        Long memberId = 1L;
        String nickname = "otherPerson";

        ChatRoom chatRoom = ChatRoom.createRoom(member1, otherPerson);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member1));
        when(memberRepository.findByNickname(nickname)).thenReturn(Optional.of(otherPerson));

        when(chatRoomRepository.findByMembers(member1, otherPerson)).thenReturn(List.of(chatRoom));

        when(chatMessageRepository.findByChatRoomId(chatRoom.getId())).thenReturn(List.of());

        //when
        ChatResponseDto chatResponseDto = chatService.chatRoom(nickname);

        //then
        Assertions.assertThat(chatResponseDto.getChatHistory().size()).isEqualTo(0);
        Assertions.assertThat(chatResponseDto.getMemberInfo().getNickname()).isEqualTo(nickname);
        Assertions.assertThat(chatResponseDto.getMemberInfo().getImage()).isEqualTo(otherPerson.getImage());
    }

    @Test
    void chatRoom_채팅_내역이_존재할_경우_채팅_기록을_포함해서_반환() {
        //given
        Long memberId = 1L;
        String nickname = "otherPerson";

        ChatRoom chatRoom = ChatRoom.createRoom(member1, otherPerson);

        ChatMessage message1 = ChatMessage.createMessage("hello", "test", LocalDateTime.now(), chatRoom);
        ChatMessage message2 = ChatMessage.createMessage("hi", "otherPerson", LocalDateTime.now(), chatRoom);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member1));
        when(memberRepository.findByNickname(nickname)).thenReturn(Optional.of(otherPerson));

        when(chatRoomRepository.findByMembers(member1, otherPerson)).thenReturn(List.of(chatRoom));

        when(chatMessageRepository.findByChatRoomId(chatRoom.getId())).thenReturn(List.of(message1, message2));

        //when
        ChatResponseDto chatResponseDto = chatService.chatRoom(nickname);

        //then
        Assertions.assertThat(chatResponseDto.getChatHistory().size()).isEqualTo(2);
        Assertions.assertThat(chatResponseDto.getMemberInfo().getNickname()).isEqualTo(nickname);
        Assertions.assertThat(chatResponseDto.getMemberInfo().getImage()).isEqualTo(otherPerson.getImage());
    }

    @Test
    void deleteChatRoom_삭제_성공() {
        //given
        Long memberId = 1L;
        String nickname = "otherPerson";

        ChatRoom chatRoom = ChatRoom.createRoom(member1, otherPerson);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member1));

        when(chatRoomRepository.findById(chatRoom.getId())).thenReturn(Optional.of(chatRoom));

        //when
        chatService.deleteChatRoom(chatRoom.getId());

        //then
        verify(chatRoomRepository).delete(chatRoom);
    }

    @Test
    void deleteChatRoom_DB에_채팅방이_존재하지_않을_경우_에러_발생() {
        //given
        Long memberId = 1L;
        String nickname = "otherPerson";

        ChatRoom chatRoom = ChatRoom.createRoom(member1, otherPerson);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member1));

        when(chatRoomRepository.findById(1L)).thenReturn(Optional.empty());

        //when & then
        Assertions.assertThatThrownBy(() -> chatService.deleteChatRoom(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("채팅 방이 없습니다.");
    }

    @Test
    void deleteChatRoom_채팅방에_소속된_멤버가_아닐경우_에러_발생() {
        //given
        Long memberId = 1L;
        String nickname = "otherPerson";

        Member ham = Member.builder()
                .id(1L)
                .email("ham@example.com")
                .password("password")
                .nickname("ham")
                .image("ham.jpg")
                .introduction("hello")
                .build();

        ChatRoom chatRoom = ChatRoom.createRoom(ham, otherPerson);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member1));

        when(chatRoomRepository.findById(chatRoom.getId())).thenReturn(Optional.of(chatRoom));

        //when & then
        Assertions.assertThatThrownBy(() -> chatService.deleteChatRoom(chatRoom.getId()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("채팅 방에 소속된 멤버가 아닙니다.");
    }
}