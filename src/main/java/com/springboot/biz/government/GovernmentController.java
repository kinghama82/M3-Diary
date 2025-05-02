package com.springboot.biz.government;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	//정책api 데이터
	@GetMapping("/list")
	public ResponseEntity<?> list(@RequestParam(name = "page",defaultValue = "1") int page) {
		String apiUrl = apiBaseUrl + "?apiKeyNm=" + apikey + "&rtnType=json" 
				+ "&pageNum=" + page
				+ "&pageSize=10";
		
		Object res = restTemplate.getForObject(apiUrl, Object.class);
		return ResponseEntity.ok(res);
	}
	
	//즐겨찾기추가 + 중복검사
	@PostMapping("/bookmark")
	public ResponseEntity<?> bookMark(@RequestBody GovBookmarkDto dto){
		String result = governmentService.checkBookmark(dto);
		return ResponseEntity.ok(result);
	}
	
}
