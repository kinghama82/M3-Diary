package com.springboot.biz.saramin;

import com.springboot.biz.calendar.SaraminCalendarDto;
import com.springboot.biz.m3user.M3Service;
import com.springboot.biz.m3user.M3User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ListController {

    private final M3Service m3Service;
    private final SaraminService saraminService;


    // 다이어리 페이지 - 즐겨찾기 공고 리스트 조회
    @GetMapping("/diary")
    public String diaryList(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        M3User user = m3Service.findByUsername(userDetails.getUsername());
        List<UserSaramin> favorites = m3Service.getFavoriteSaramins(user.getUserSeq());

        List<SaraminCalendarDto> dtos = favorites.stream()
                .sorted(Comparator.comparing(us -> us.getSaramin().getId()))
                .map(SaraminCalendarDto::new)
                .toList();

        model.addAttribute("favorites", dtos);
        return "pages/diary";
    }

    @PostMapping("/saramin/apply")
    public String applyToSaramin(@RequestParam("saraminId") Integer saraminId,
                                 @RequestParam("applied") boolean applied,
                                 Principal principal) {
        String username = principal.getName();
        saraminService.updateAppliedStatus(username, saraminId, applied);


        return "redirect:/diary"; // 다시 다이어리로 리디렉트
    }



}
