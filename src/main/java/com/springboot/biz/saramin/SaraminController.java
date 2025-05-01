package com.springboot.biz.saramin;


import com.springboot.biz.m3user.M3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/saramin")
public class SaraminController {

    private final SaraminService saraminService;
    private final M3Service m3Service;


    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "") String keywords, Model model) throws Exception {
        /*List<Saramin> jobs = saraminService.getJobsFromApiAndSave(keywords); */
        List<Map<String, String>> jobs = saraminService.getJobsFromApi(keywords); //  저장 안됨
        model.addAttribute("jobs", jobs);
        model.addAttribute("keywords", keywords);
        return "saramin_list"; // ← HTML에 job.id 사용 가능
    }




}