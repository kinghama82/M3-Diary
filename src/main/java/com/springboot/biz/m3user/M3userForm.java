package com.springboot.biz.m3user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class M3userForm {

//
    @Size(min =3, max=20)
    @NotEmpty(message = "아이디를 입력하세요")
    private  String username;

    @NotEmpty(message = "비밀번호를 입력하세요")
    private  String password;

    @NotEmpty(message = "비밀번호가 동일하지 않습니다")
    private  String password2;

    @Size(min =3, max=20)
    @NotEmpty(message = "닉네임을 입력하세요")
    private  String nickname;

    @Email
    @NotEmpty(message = "이메일을를 입력하세요")
    private  String email;

    @NotEmpty(message = "전화번호를 입력하세요")
    private  String phone;

    @Size(max = 8)
    @NotEmpty(message = "생년월일 8자리를 입력하세요")
    private String birthday;

    @NotEmpty(message = "주소를 입력하세요")
    private String address;

    @NotEmpty(message = "동,읍,면 까지 입력하세요")
    private String addressDetail;

}
