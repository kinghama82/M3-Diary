package com.springboot.biz.saramin;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;
import java.util.Optional;

@Repository
public interface SaraminRepository extends JpaRepository<Saramin,Integer> {

    List<Saramin> findByKeywords(String keywords);

    Optional<Saramin> findByUrl(String url);


    @Transactional
    @Modifying
    @Query("UPDATE UserSaramin us SET us.applied = :applied WHERE us.m3User.userSeq = :userSeq AND us.saramin.id = :saraminId")
    void updateApplied(@Param("userSeq") Integer userSeq,
                       @Param("saraminId") Integer saraminId,
                       @Param("applied") boolean applied);

}
