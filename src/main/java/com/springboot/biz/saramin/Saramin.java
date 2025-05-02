package com.springboot.biz.saramin;

import com.springboot.biz.m3user.M3User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Saramin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String keywords;

    private String companyName;

    private String positionTitle;  //공고제목

    private String postingDate; //접수 시작일

    private String expirationDate; //마감일

    private String active; //마감 진행중

    private String closeType; //마감일 형식 1접수마감일, 2채용시, 3상시, 4수시

    private String url;

    /*즐겨찾기*/
    @ManyToMany(mappedBy = "favoriteSaramins")
    @JsonIgnore
    private Set<M3User> likedUsers = new HashSet<>();


}
