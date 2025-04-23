package com.springboot.biz.m3user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class M3Service {

    private final M3Repository m3Repository;
    private final PasswordEncoder passwordEncoder; //빈에서 자동 주입됨

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


}
