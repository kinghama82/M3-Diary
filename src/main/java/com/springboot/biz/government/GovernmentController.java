package com.springboot.biz.government;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gov")
public class GovernmentController {

	private final GovernmentService governmentService;
	
	@Value("${gov.api-key}")
	private String apikey;
	
	
	@GetMapping("/list")
	public void list() {
		
	}
	
	
}
