package com.springboot.biz.government;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/gov")
public class governmentController {

	private final governmentService governmentService;
	
	@GetMapping("/list")
	public void list() {
		
	}
	
	
}
