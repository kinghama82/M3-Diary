package com.springboot.biz.m3user;

import com.springboot.biz.saramin.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class M3Service {

    private final M3Repository m3Repository;
    private final PasswordEncoder passwordEncoder;
    private final SaraminRepository saraminRepository;
    private final UserSaraminRepository userSaraminRepository;

    @Transactional
    public M3User create(String email, String password, String nickname, String phone, String birthday,
                         String address, String addressDetail, String username, String password2) {
        M3User m3User = new M3User();
        m3User.setEmail(email);
        m3User.setUsername(username);
        m3User.setPassword(passwordEncoder.encode(password));
        m3User.setNickname(nickname);
        m3User.setPhone(phone);
        m3User.setBirthday(birthday);
        m3User.setAddress(address);
        m3User.setAddressDetail(addressDetail);
        this.m3Repository.save(m3User);
        return m3User;
    }

    public M3User findByUsername(String username) {
        return m3Repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    public List<UserSaramin> getFavoriteSaramins(Integer userSeq) {
        M3User user = m3Repository.findById(userSeq)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        return userSaraminRepository.findAllByM3User(user);
    }

    @Transactional
    public boolean saveFavoriteSaramin(Integer userSeq, SaraminDto dto) {
        M3User user = m3Repository.findById(userSeq).orElseThrow();

        Optional<Saramin> optional = saraminRepository.findByUrl(dto.getUrl());
        Saramin saramin = optional.orElseGet(() -> {
            Saramin s = new Saramin();
            s.setCompanyName(dto.getCompanyName());
            s.setPositionTitle(dto.getPositionTitle());
            s.setPostingDate(dto.getPostingDate());
            s.setExpirationDate(dto.getExpirationDate());
            s.setActive(dto.getActive());
            s.setCloseType(dto.getCloseType());
            s.setUrl(dto.getUrl());
            return saraminRepository.save(s);
        });

        // UserSaramin 기준으로 찜 여부 확인 및 저장/해제
        Optional<UserSaramin> existing = userSaraminRepository.findByM3UserAndSaramin(user, saramin);
        if (existing.isPresent()) {
            userSaraminRepository.delete(existing.get());
            return false; // 해제됨
        } else {
            UserSaramin us = new UserSaramin();
            us.setM3User(user);
            us.setSaramin(saramin);
            userSaraminRepository.save(us);
            return true; // 찜됨
        }
    }
}
