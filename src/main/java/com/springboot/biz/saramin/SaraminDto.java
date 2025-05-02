package com.springboot.biz.saramin;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


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

    private List<String> dateRange;
    public List<String> getDateRange() {return dateRange;}
    public void setDateRange(List<String> dateRange) {this.dateRange = dateRange;}
}
