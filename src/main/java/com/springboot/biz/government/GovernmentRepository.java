package com.springboot.biz.government;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GovernmentRepository extends JpaRepository<GovBookmark, Integer>{

	Optional<GovBookmark> findByPlcyNo(String plcyNo);
	void deleteByPlcyNo(String plcyNo);
	boolean existsByPlcyNo(String plcyNo);
}
