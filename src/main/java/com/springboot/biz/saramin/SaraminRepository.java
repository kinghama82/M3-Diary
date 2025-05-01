package com.springboot.biz.saramin;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaraminRepository extends JpaRepository<Saramin,Integer> {
    List<Saramin> findByKeywords(String keywords);
    Optional<Saramin> findByUrl(String url);
}
