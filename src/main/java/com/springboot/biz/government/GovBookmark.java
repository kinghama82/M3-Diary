package com.springboot.biz.government;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
}
