package com.springboot.biz;

import com.springboot.biz.m3user.M3Repository;
import com.springboot.biz.m3user.M3User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class JwtController {

    private final M3Repository m3Repository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password) {
        Optional<M3User> user = m3Repository.findByUsername(username);

        // 비밀번호는 실제로는 암호화 검증해야 함 (BCryptPasswordEncoder)
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            String token = jwtTokenProvider.createToken(username);
            return ResponseEntity.ok().body(token);
        }

        return ResponseEntity.status(401).body("Invalid username or password");
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.replace("Bearer ", "");

        if (jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.getUsername(token);
            return ResponseEntity.ok().body("현재 로그인한 사용자: " + username);
        }

        return ResponseEntity.status(401).body("유효하지 않은 토큰입니다.");
    }
}
