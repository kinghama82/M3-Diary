package com.springboot.biz;


import com.springboot.biz.m3user.M3Repository;
import com.springboot.biz.m3user.M3User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final M3Repository m3Repository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        String email = (String) response.get("email");
        String name = (String) response.get("name");

        //유저가 DB에 없으면 저장
        M3User user = m3Repository.findByEmail(email).orElseGet(() -> {
            M3User newUser = new M3User();
            newUser.setEmail(email);
            newUser.setNickname(name);
            newUser.setUsername(email); // OAuth 유저는 email을 username처럼 사용
            newUser.setPassword("");    // 비밀번호 없음
            return m3Repository.save(newUser);
        });

        // 인증 객체 반환
        return new DefaultOAuth2User(
                Collections.singleton(() -> "ROLE_USER"),
                response,
                "email" // 이 값이 getName()으로 사용됨
        );
    }
}
