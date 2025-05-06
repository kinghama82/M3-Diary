package com.springboot.biz.saramin;




import com.springboot.biz.m3user.M3User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserSaraminRepository extends JpaRepository<UserSaramin, Long> {
    Optional<UserSaramin> findByM3UserAndSaramin(M3User m3User, Saramin saramin);
    List<UserSaramin> findAllByM3User(M3User m3User);


    @Transactional
    @Modifying
    @Query("UPDATE UserSaramin us SET us.applied = :applied WHERE us.m3User.userSeq = :userSeq AND us.saramin.id = :saraminId")
    void updateApplied(@Param("userSeq") Integer userSeq,
                       @Param("saraminId") Integer saraminId,
                       @Param("applied") boolean applied);
}
