package com.springboot.biz.calendar;


import com.springboot.biz.m3user.M3Service;
import com.springboot.biz.saramin.Saramin;
import com.springboot.biz.saramin.UserSaramin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class CalendarController {


    private final M3Service m3Service;

    @GetMapping("/calendar")
    public String calendar(Model model, Principal principal) {
        // userSeq 타입에 맞게 수정 (Integer → Long)
        Integer userSeq = m3Service.findByUsername(principal.getName()).getUserSeq();

        List<UserSaramin> favorites = m3Service.getFavoriteSaramins(userSeq);

        List<SaraminCalendarDto> dtos = favorites.stream()
                .sorted(Comparator.comparing(us -> LocalDate.parse(us.getSaramin().getPostingDate())))
                .map(SaraminCalendarDto::new)
                .toList();

        model.addAttribute("favorites", dtos);
        return "calendar";
    }

}
