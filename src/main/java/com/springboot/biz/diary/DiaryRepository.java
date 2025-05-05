package com.springboot.biz.diary;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.biz.m3user.M3User;

public interface DiaryRepository extends JpaRepository<Diary, Integer>{

	Optional<Diary> findByYearAndMonthAndDayAndM3user(int year, int month, int day,M3User user);
}
