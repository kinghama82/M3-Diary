package com.springboot.biz.saramin;

import lombok.Getter;
import lombok.Setter;




@Getter
@Setter
public class SaraminDto {
    private String companyName;
    private String positionTitle;
    private String postingDate;
    private String expirationDate;
    private String active;
    private String closeType;
    private String url;
}
