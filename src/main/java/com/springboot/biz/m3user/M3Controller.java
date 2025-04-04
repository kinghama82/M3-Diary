package com.springboot.biz.m3user;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class M3Controller {


    private final M3Service m3Service;

    @GetMapping("/loging")
    public String signup(M3userForm m3userForm) {
        return "loging";
    }

    @PostMapping("/loging")
    public String loging(@Valid M3userForm m3userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "loging";
        }
        if (!m3userForm.getPassword().equals(m3userForm.getPassword2())) {
            bindingResult.rejectValue("password2", "password오류",
                    "비밀번호가 틀립니다.");
            return "loging";
        }
        try {
            m3Service.create(m3userForm.getUsername(),m3userForm.getPassword(),m3userForm.getNickname(),
                    m3userForm.getEmail(),m3userForm.getBirthday(),
                    m3userForm.getAddress(),m3userForm.getPhone(),m3userForm.getAddressDetail());
        }catch (DataIntegrityViolationException e) {
            e.printStackTrace();

        bindingResult.reject("logingFailed", "중복된 사용자 입니다.");
        return "loging";
    }catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("logingFailed", e.getMessage());
            return "loging";
        }
        return "redirect:/user/login";

    }


}
