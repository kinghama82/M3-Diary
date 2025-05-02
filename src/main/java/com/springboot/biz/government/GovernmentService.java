package com.springboot.biz.government;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GovernmentService {

	private final GovernmentRepository governmentRepository;
	private final ModelMapper modelMapper;
	
	//즐겨찾기 추가
	public void addBookmark(GovBookmarkDto dto) {
		GovBookmark entity = modelMapper.map(dto, GovBookmark.class);
		governmentRepository.save(entity);
	}
	
	//즐겨찾기 중복검사
	public String checkBookmark(GovBookmarkDto dto) {
		if(governmentRepository.existsByPlcyNo(dto.getPlcyNo())) {
			governmentRepository.deleteByPlcyNo(dto.getPlcyNo());
			return "즐겨찾기 삭제됨";
		}
		GovBookmark entity = modelMapper.map(dto, GovBookmark.class);
		governmentRepository.save(entity);
		return "즐겨찾기 저장됨";
	}
}
