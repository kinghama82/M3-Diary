package com.springboot.biz.government;

import com.springboot.biz.m3user.M3User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class GovBookmark {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "plcyNo")
	private String plcyNo; //정책번호
	
	@Column(name = "plcyNm")
	private String plcynm;  //정책이름
	
	@Column(name = "aplyYmd")
	private String aplyYmd; //신청기간
	
	@ManyToOne
	private M3User m3user;
	
	
}
