package com.springboot.biz.diary;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class DiaryDto {

	private int year;
	private int month;
	private int day;
	private String content;
}
