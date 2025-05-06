package com.springboot.biz.saramin;


import com.springboot.biz.m3user.M3User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class UserSaramin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private M3User m3User;

    @ManyToOne
    private Saramin saramin;

    private boolean applied;
}
