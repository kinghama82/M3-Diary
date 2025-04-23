package com.springboot.biz.m3user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface M3Repository extends JpaRepository<M3User, Integer> {

    Optional<M3User> findByUsername(String username);
    Optional<M3User> findByEmail(String email);


}
