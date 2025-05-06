package com.springboot.biz.calendar;



import com.springboot.biz.saramin.Saramin;
import com.springboot.biz.saramin.UserSaramin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaraminCalendarDto {

    private Integer id;  //따라가야해
    private String companyName;
    private String positionTitle;
    private String postingDate;
    private String expirationDate;
    private String url;
    private boolean applied;

    public SaraminCalendarDto(Saramin s) {
        this.id = s.getId();
        this.companyName = s.getCompanyName();
        this.positionTitle = s.getPositionTitle();
        this.postingDate = s.getPostingDate();
        this.expirationDate = s.getExpirationDate();
        this.url = s.getUrl();
        this.applied = s.isApplied(); // fallback용
    }

public SaraminCalendarDto(UserSaramin us) {
    this(us.getSaramin()); // 기존 생성자 호출
    this.applied = us.isApplied(); // 사용자 기준 값 덮어쓰기
}

}