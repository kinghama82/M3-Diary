package com.springboot.biz.m3user;

import com.springboot.biz.saramin.Saramin;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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

	private String phone; //유저 폰번호

	private String birthday; //유저 생일

	private String address;  //주소

	private String addressDetail;  //상세주소


	@ManyToMany
	@JoinTable(
			name = "user_favorite_saramin",
			joinColumns = @JoinColumn(name = "user_seq"),
			inverseJoinColumns = @JoinColumn(name = "saramin_id")
	)
	private Set<Saramin> favoriteSaramins = new HashSet<>();


}
