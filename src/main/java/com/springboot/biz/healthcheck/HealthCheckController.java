package com.springboot.biz.healthcheck;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

	
	//서버 살아있나 체크하는 컨트롤러
	@GetMapping("/health")
	public ResponseEntity<String> healthCheck(){
		return ResponseEntity.ok("OK");
	}
}
