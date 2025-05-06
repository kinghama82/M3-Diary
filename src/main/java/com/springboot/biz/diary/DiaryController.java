package com.springboot.biz.diary;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.biz.m3user.M3Service;
import com.springboot.biz.m3user.M3User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary")
public class DiaryController {

	private final DiaryService diaryService;
	private final M3Service m3Service;
	
	//해당 날짜의 메모 조회
	@GetMapping("/{year}/{month}/{day}")
	public ResponseEntity<?> getDiary(
					@PathVariable(name = "year")int year,
					@PathVariable(name = "month")int month,
					@PathVariable(name = "day")int day,
					Principal principal){
		
		M3User m3User = m3Service.findByUsername(principal.getName());
		Optional<Diary> post = diaryService.getPost(year, month, day, m3User);
		
		return post.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.noContent().build());
	}
	//해당 월의 메모 조회
	@GetMapping("/list/{year}/{month}")
	public ResponseEntity<?> memoList(@PathVariable(name = "year")int year,
					@PathVariable(name = "month")int month,
					Principal principal){
		M3User user = m3Service.findByUsername(principal.getName());
		List<Integer> memo = diaryService.getMemoList(year, month, user);
		return ResponseEntity.ok(memo);
		
	}
	
	//메모 작성 수정
    @PostMapping("/save")
    public ResponseEntity<?> saveDiary(@RequestBody DiaryDto dto,Principal principal) {
        M3User m3User = m3Service.findByUsername(principal.getName());
    	
        try {
        	Diary diary = diaryService.saveOrUpdate(
                    dto.getYear(),
                    dto.getMonth(),
                    dto.getDay(),
                    dto.getContent(),
                    m3User
            );
        	return ResponseEntity.ok(diary);
			 
		} catch (IllegalStateException e) {
			return ResponseEntity
					.status(HttpStatus.CONFLICT)  //409
					.body(e.getMessage());
		}        
    }

}
