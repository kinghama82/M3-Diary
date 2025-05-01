package com.springboot.biz.government;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/gov")
public class GovernmentController {

	private final GovernmentService governmentService;
	
	@GetMapping("/list")
	public void list() {
		
	}
	
	
}
