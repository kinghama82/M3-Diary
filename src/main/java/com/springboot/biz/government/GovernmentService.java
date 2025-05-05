package com.springboot.biz.government;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.springboot.biz.m3user.M3Repository;
import com.springboot.biz.m3user.M3User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GovernmentService {

	private final GovernmentRepository governmentRepository;
	private final M3Repository m3Repository;
	private final ModelMapper modelMapper;
	
//	//즐겨찾기 추가
//	public void addBookmark(GovBookmarkDto dto) {
//		GovBookmark entity = modelMapper.map(dto, GovBookmark.class);
//		governmentRepository.save(entity);
//	}
	
	//즐겨찾기 중복검사
	public String checkBookmark(GovBookmarkDto dto,M3User m3User) {
		Optional<GovBookmark> exist = governmentRepository.findByPlcyNoAndM3user(dto.getPlcyNo(), m3User);
		if(exist.isPresent()) {
			governmentRepository.delete(exist.get());
			return "삭제됨";
		}
		GovBookmark entity = modelMapper.map(dto, GovBookmark.class);
		
		entity.setM3user(m3User);
		governmentRepository.save(entity);
		return "저장됨";
	}
}
