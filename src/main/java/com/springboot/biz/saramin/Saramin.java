package com.springboot.biz.saramin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

}
