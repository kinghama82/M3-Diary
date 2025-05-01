package com.springboot.biz.government;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class GovBookmarkDto {

	private String plcyNo; //정책번호
	private String plcyNm; //정책이름
	private String aplyYmd; //신청기간
}
