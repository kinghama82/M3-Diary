package com.springboot.biz.calendar;


import com.springboot.biz.m3user.M3Service;
import com.springboot.biz.saramin.Saramin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class CalendarController {


    private final M3Service m3Service;

    @GetMapping("/calendar")
    public String calendar(Model model, Principal principal) {
        Integer userSeq = m3Service.findByUsername(principal.getName()).getUserSeq();
        Set<Saramin> favorites = m3Service.getFavoriteSaramins(userSeq);

        List<SaraminCalendarDto> dtos = favorites.stream()
                .map(SaraminCalendarDto::new)
                .toList();

        model.addAttribute("favorites", dtos);
        return "calendar";
    }


}
