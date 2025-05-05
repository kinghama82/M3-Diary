package com.springboot.biz.government;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.springboot.biz.m3user.M3Service;
import com.springboot.biz.m3user.M3User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gov")
public class GovernmentController {

	private final GovernmentService governmentService;
	private final M3Service m3Service;
	
	@Value("${gov.api-key}")
	private String apikey;
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	private String apiBaseUrl = "https://www.youthcenter.go.kr/go/ythip/getPlcy";
	
	//정책api 데이터
	@GetMapping("/list")
	public ResponseEntity<?> list(@RequestParam(name = "page",defaultValue = "1") int page,
					@RequestParam(name = "zipCd", required = false)String zipCd) {
		String apiUrl = apiBaseUrl + "?apiKeyNm=" + apikey + "&rtnType=json" 
				+ "&pageNum=" + page
				+ "&pageSize=10";
		
		//지역 선택시 zipCd 추가
		if(zipCd != null && !zipCd.isEmpty()) {
			apiUrl += "&zipCd=" + zipCd;
		}
		
		Object res = restTemplate.getForObject(apiUrl, Object.class);
		return ResponseEntity.ok(res);
	}
	
	//즐겨찾기추가 + 중복검사
	@PostMapping("/bookmark")
	public ResponseEntity<?> bookMark(@RequestBody GovBookmarkDto dto,Principal principal){
		M3User m3User = m3Service.findByUsername(principal.getName());
		String result = governmentService.checkBookmark(dto,m3User);
		return ResponseEntity.ok(result);
	}
	
}
