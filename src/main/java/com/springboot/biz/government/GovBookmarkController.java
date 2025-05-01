package com.springboot.biz.government;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/gov")
@RequiredArgsConstructor
public class GovBookmarkController {

	private final GovernmentService governmentService;
	
	@GetMapping("/list")
	public String list() {
		return "pages/gov_list";
	}
}
