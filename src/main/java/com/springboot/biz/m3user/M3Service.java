package com.springboot.biz.m3user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class M3Service {

    private final M3Repository m3Repository;

    public M3User create(String email, String password, String nickname, String phone, String birthday
    , String address, String addressDetail, String username) {
        M3User m3User = new M3User();
        m3User.setEmail(email);
        m3User.setUsername(username);
        m3User.setPassword(password);
        m3User.setNickname(nickname);
        m3User.setPhone(phone);
        m3User.setBirthday(birthday);
        m3User.setAddress(address);
        m3User.setAddressDetail(addressDetail);
        this.m3Repository.save(m3User);
        return m3User;
    }


}
