package com.project.capstone.config;

import com.project.capstone.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final TokenProvider tokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        System.out.println(message);
        if (accessor.getCommand() == StompCommand.CONNECT) {
            String authorizationHeader = accessor.getFirstNativeHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                if (!tokenProvider.validateToken(token)) {
                    try {
                        throw new AccessDeniedException("토큰 인증 실패");
                    } catch (AccessDeniedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return message;
    }
}
