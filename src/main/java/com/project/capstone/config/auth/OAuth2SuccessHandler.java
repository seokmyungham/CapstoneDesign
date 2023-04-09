package com.project.capstone.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.capstone.config.auth.dto.MemberDto;
import com.project.capstone.config.auth.dto.UserRequestMapper;
import com.project.capstone.config.jwt.Token;
import com.project.capstone.config.jwt.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
@Component
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;
    private final UserRequestMapper userRequestMapper;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        MemberDto memberDto = userRequestMapper.toDto(oAuth2User);


        log.info("Principal 에서 꺼낸 OAuth2User = {}", oAuth2User);
        log.info("토근 발행 시작");

        Token token = tokenService.generateToken(memberDto.getEmail(), "USER");

        log.info("{}", token);
//        writeTokenResponse(response, token);


        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/oauth2/redirect/")
                .queryParam("token", token)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
//
//    private void writeTokenResponse(HttpServletResponse response, Token token) throws IOException {
//        response.setContentType("text/html;charset=UTF-8");
//
//        response.addHeader("Auth", token.getToken());
//        response.addHeader("Refresh", token.getRefreshToken());
//        response.setContentType("application/json;charset=UTF-8");
//
//        PrintWriter writer = response.getWriter();
//        writer.println(objectMapper.writeValueAsString(token));
//        writer.flush();
//    }
}
