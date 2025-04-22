package com.springboot.biz.m3user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 🔐 URL 인증 정책 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/user/signup", "/css/**", "/js/**").permitAll()  // 누구나 접근 가능
                        .anyRequest().authenticated()  // 그 외는 로그인 필요
                )
                // 🔐 로그인 설정
                .formLogin(form -> form
                        .loginPage("/user/login")                 // 로그인 페이지 (GET)
                        .loginProcessingUrl("/user/login")        // 로그인 처리 URL (POST)
                        .defaultSuccessUrl("/")                   // 로그인 성공 시 이동할 경로
                        .permitAll()
                )
                // 🔐 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")                    // 로그아웃 후 이동할 경로
                        .permitAll()
                );

        return http.build();
    }

    // 🔐 비밀번호 암호화용 Encoder 등록 (회원가입 때 사용)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
