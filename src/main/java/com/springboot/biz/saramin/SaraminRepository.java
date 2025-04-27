package com.springboot.biz.saramin;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaraminRepository extends JpaRepository<Saramin,Integer> {
    List<Saramin> findByKeywords(String keywords);
}
