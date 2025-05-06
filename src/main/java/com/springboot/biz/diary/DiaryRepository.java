package com.springboot.biz.diary;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.biz.m3user.M3User;

public interface DiaryRepository extends JpaRepository<Diary, Integer>{

	Optional<Diary> findByYearAndMonthAndDayAndM3user(int year, int month, int day,M3User user);
	
	//월별 메모 리스트
	List<Diary> findByYearAndMonthAndM3user(int year, int month, M3User user);



}
