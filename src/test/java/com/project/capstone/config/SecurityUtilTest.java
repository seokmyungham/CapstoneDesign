package com.project.capstone.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class SecurityUtilTest {

    @Mock
    Authentication authentication;

    @Mock
    SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
        securityContext = Mockito.mock(SecurityContext.class);
        authentication = Mockito.mock(Authentication.class);
    }

    @Test
    void getCurrentMemberId_인증_정보_반환_성공() {
        //given
        Long memberId = 1L;

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(memberId.toString());

        SecurityContextHolder.setContext(securityContext);

        //when
        Long result = SecurityUtil.getCurrentMemberId();

        //then
        assertThat(result).isEqualTo(memberId);
    }

    @Test
    void getCurrentMemberId_인증_정보_반환_실패() {
        //given
        Long memberId = 1L;

        when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        //when & then
        assertThatThrownBy(SecurityUtil::getCurrentMemberId)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Security Context에 인증 정보가 없습니다.");
    }
}