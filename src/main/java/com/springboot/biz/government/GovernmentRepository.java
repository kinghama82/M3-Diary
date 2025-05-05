package com.springboot.biz.government;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.biz.m3user.M3User;

public interface GovernmentRepository extends JpaRepository<GovBookmark, Integer>{

	Optional<GovBookmark> findByPlcyNoAndM3user(String plcyNo, M3User m3user);
	void deleteByPlcyNoAndM3user(String plcyNo, M3User m3user);
	boolean existsByPlcyNoAndM3user(String plcyNo, M3User m3user);
}
