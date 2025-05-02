package com.springboot.biz.government;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GovernmentService {

	private final GovernmentRepository governmentRepository;
	private final ModelMapper modelMapper;
	
	public void addBookmark(GovBookmarkDto dto) {
		GovBookmark entity = modelMapper.map(dto, GovBookmark.class);
		governmentRepository.save(entity);
	}
}
