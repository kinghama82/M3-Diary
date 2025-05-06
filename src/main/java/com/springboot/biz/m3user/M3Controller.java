package com.springboot.biz.m3user;


import com.springboot.biz.calendar.SaraminCalendarDto;
import com.springboot.biz.saramin.Saramin;
import com.springboot.biz.saramin.SaraminDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class M3Controller {


    private final M3Service m3Service;

    @GetMapping("/signup")
    public String signup(M3userForm m3userForm) {
        return "pages/signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@Valid M3userForm m3userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pages/signup";
        }
        if (!m3userForm.getPassword().equals(m3userForm.getPassword2())) {
            bindingResult.rejectValue("password2", "password오류",
                    "비밀번호가 틀립니다.");
            return "pages/signup";
        }
        try {
            m3Service.create(m3userForm.getEmail(),
                    m3userForm.getPassword(),
                    m3userForm.getNickname(),
                    m3userForm.getPhone(),
                    m3userForm.getBirthday(),
                    m3userForm.getAddress(),
                    m3userForm.getAddressDetail(),
                    m3userForm.getUsername(),
                    m3userForm.getPassword2());
        }catch (DataIntegrityViolationException e) {
            e.printStackTrace();

        bindingResult.reject("logingFailed", "중복된 사용자 입니다.");
        return "pages/signup";
    }catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("logingFailed", e.getMessage());
            return "pages/signup";
        }
        return "redirect:/user/login";

    }
    @GetMapping("/login")
    public String loginpage() {

        return "pages/mainPage";
    }

    @PostMapping("/favorite/save")
    @ResponseBody
    public String saveFavorite(@ModelAttribute SaraminDto dto, Principal principal) {
        Integer userId = m3Service.findByUsername(principal.getName()).getUserSeq();
        boolean added = m3Service.saveFavoriteSaramin(userId, dto); // true면 찜, false면 해제
        return added ? "공고가 찜되었습니다." : "공고가 해제되었습니다.";
    }
    @GetMapping("/gov/list")
    public String policyPage() {
        return "pages/policy";  // templates/pages/policy.html 로 이동
    }







}
