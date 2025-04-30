package com.springboot.biz.m3user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class M3User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userSeq;

	private String username; // 아이디 cicd 확인용
	
	private String email; // 이메일
	
	private String nickname; // 이름
	
	private String password; // 비밀번호

	private String password2; // 비밀번호 확인

	private String phone; //유저 폰번호

	private String birthday; //유저 생일

	private String address;  //주소

	private String addressDetail;  //상세주소



}
