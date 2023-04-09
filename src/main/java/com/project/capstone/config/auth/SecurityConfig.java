package com.project.capstone.config.auth;


import com.project.capstone.config.jwt.JwtAuthFilter;
import com.project.capstone.config.jwt.TokenService;
import com.project.capstone.domain.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler successHandler;
    private final TokenService tokenService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout(logout -> logout
                        .logoutSuccessUrl("/"))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/css/**", "/images/**",
                                "/js/**", "/h2-console/**", "/token/**", "/profile").permitAll()
                        .requestMatchers("/api/**").hasRole(Role.USER.name())
                        .anyRequest().authenticated())
                .addFilterBefore(new JwtAuthFilter(tokenService),
                        UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/token/expired")
                        .successHandler(successHandler)
                        .userInfoEndpoint()
                        .userService(customOAuth2UserService));

        return http.addFilterBefore(new JwtAuthFilter(tokenService), UsernamePasswordAuthenticationFilter.class).build();
    }
}