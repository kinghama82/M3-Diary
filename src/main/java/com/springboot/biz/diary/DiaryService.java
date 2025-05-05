package com.springboot.biz.diary;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.springboot.biz.m3user.M3Repository;
import com.springboot.biz.m3user.M3User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiaryService {

	private final DiaryRepository diaryRepository;
	private final M3Repository m3Repository;
	
	public Diary saveOrUpdate(int year, int month, int day, String content,M3User user) {
        Optional<Diary> optional = diaryRepository.findByYearAndMonthAndDayAndM3user(year, month, day,user);

        if (optional.isPresent()) {
            // 기존 데이터가 있으면 수정
            Diary diary = optional.get();
            diary.setContent(content);
            return diaryRepository.save(diary);
        } else {
            // 없으면 새로 저장
            Diary newPost = new Diary();
            	newPost.setYear(year);
            	newPost.setMonth(month);
            	newPost.setDay(day);
            	newPost.setM3user(user);
            	newPost.setContent(content);
            try {
            	return diaryRepository.save(newPost);
			} catch (DataIntegrityViolationException e) {
				//중복 저장시 예외 발생
				throw new IllegalStateException("이미 해당 날짜에 메모가 존재합니다.");
			}      
            
        }
    }

    public Optional<Diary> getPost(int year, int month, int day, M3User user) {
        return diaryRepository.findByYearAndMonthAndDayAndM3user(year, month, day, user);
    }
}
