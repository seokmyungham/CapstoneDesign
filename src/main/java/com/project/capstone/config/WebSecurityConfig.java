package com.project.capstone.config;

import com.project.capstone.config.jwt.JwtAccessDeniedHandler;
import com.project.capstone.config.jwt.JwtAuthenticationEntryPoint;
import com.project.capstone.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Component
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .authorizeHttpRequests() .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .requestMatchers( "/auth/**", "/profile", "/news/**", "/ws/**").permitAll()

                .requestMatchers(HttpMethod.POST, "/post/").authenticated()
                .requestMatchers(HttpMethod.PUT, "/post/").authenticated()
                .requestMatchers(HttpMethod.GET, "/post/change/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/post/one/**").authenticated()

                .requestMatchers(HttpMethod.POST, "/recommend/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/recommend/one/**").authenticated()

                .requestMatchers(HttpMethod.POST, "/comment/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/comment/one/**").authenticated()

                .requestMatchers(HttpMethod.POST, "/chat/room/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/chat/room/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/chat/list").authenticated()

                .anyRequest().permitAll()
                .and()
                .apply(new JwtSecurityConfig(tokenProvider));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("http://box-to-box.s3-website.ap-northeast-2.amazonaws.com");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
