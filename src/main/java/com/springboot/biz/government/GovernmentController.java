package com.springboot.biz.government;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gov")
public class GovernmentController {

	private final GovernmentService governmentService;
	
	@Value("${gov.api-key}")
	private String apikey;
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	private String apiBaseUrl = "https://www.youthcenter.go.kr/go/ythip/getPlcy";
	
	@GetMapping("/list")
	public ResponseEntity<?> list() {
		String apiUrl = apiBaseUrl + "?apiKeyNm=" + apikey + "&rtnType=json";
		
		Object res = restTemplate.getForObject(apiUrl, Object.class);
		return ResponseEntity.ok(res);
	}
	
	
}
