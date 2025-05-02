package com.springboot.biz.calendar;



import com.springboot.biz.saramin.Saramin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaraminCalendarDto {

    private String positionTitle;
    private String postingDate;
    private String expirationDate;

    public SaraminCalendarDto(Saramin s) {
        this.positionTitle = s.getPositionTitle();
        this.postingDate = s.getPostingDate();       // yyyy-MM-dd 형식이어야 함
        this.expirationDate = s.getExpirationDate();
    }
}

