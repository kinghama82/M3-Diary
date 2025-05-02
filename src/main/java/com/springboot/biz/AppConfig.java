package com.springboot.biz;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
    public ModelMapper getMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
        		//필드 이름 기준으로 매핑
                .setFieldMatchingEnabled(true)
                //private 필드에도 접근 가능
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                //필드명이 완벽히 일치하지 않아도 매핑시도
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
}
