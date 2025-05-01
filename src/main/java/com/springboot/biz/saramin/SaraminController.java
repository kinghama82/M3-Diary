package com.springboot.biz.saramin;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/saramin")
public class SaraminController {

    private final SaraminService saraminService;


    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "") String keywords, Model model) throws Exception {
        List<Map<String, String>> jobs = saraminService.getJobsFromApi(keywords);
        model.addAttribute("jobs", jobs);
        model.addAttribute("keywords", keywords);
        return "saramin_list"; // job-list.html 렌더링
    }

}