package com.springboot.biz.m3user;

import com.springboot.biz.saramin.Saramin;
import com.springboot.biz.saramin.SaraminDto;
import com.springboot.biz.saramin.SaraminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class M3Service {

    private final M3Repository m3Repository;
    private final PasswordEncoder passwordEncoder; //빈에서 자동 주입됨
    private final SaraminRepository saraminRepository;

@Transactional
    public M3User create(String email, String password, String nickname, String phone, String birthday
    , String address, String addressDetail, String username, String password2) {
        M3User m3User = new M3User();
        m3User.setEmail(email);
        m3User.setUsername(username);
    m3User.setPassword(passwordEncoder.encode(password)); //암호화된 비밀번호 저장!
       /* m3User.setPassword2(password2);*/
        m3User.setNickname(nickname);
        m3User.setPhone(phone);
        m3User.setBirthday(birthday);
        m3User.setAddress(address);
        m3User.setAddressDetail(addressDetail);
        this.m3Repository.save(m3User);
        return m3User;
    }

    /*즐겨찾기 목록 저장에 필요한 유저네임, */
    public M3User findByUsername(String username) {
        return m3Repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }
    public Set<Saramin> getFavoriteSaramins(Integer userSeq) {
        M3User user = m3Repository.findById(userSeq).orElseThrow();
        return user.getFavoriteSaramins();
    }

    /*즐겨찾기 목록 저장x 프론트에서만 */
    public void saveFavoriteSaramin(Integer userSeq, SaraminDto dto) {
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

        user.getFavoriteSaramins().add(saramin);
        m3Repository.save(user);
    }


}
