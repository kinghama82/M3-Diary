package com.springboot.biz.diary;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DiaryController {

	@GetMapping("/diary")
	public String page() {
		return "/pages/diary";
	}
}
