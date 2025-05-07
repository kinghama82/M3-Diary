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
    private final UserSaraminRepository userSaraminRepository;


    @GetMapping("/search")
    public String search(@RequestParam(name = "keywords",defaultValue = "") String keywords,
                         @RequestParam(defaultValue = "0") int page, Model model) throws Exception {

        List<Map<String, String>> alljobs = saraminService.getJobsFromApi(keywords); //  저장 안됨

        int pageSize =10;
        int start = page * pageSize;
        int end = Math.min(start + pageSize, alljobs.size());
        List<Map<String ,String>> jobs = alljobs.subList(start,end);
        int totalPages = (int) Math.ceil((double) alljobs.size() / pageSize);

        model.addAttribute("jobs", jobs);
        model.addAttribute("keywords", keywords);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "saramin_list";
    }
    @PostMapping("/saramin/update-applied")
    @ResponseBody
    public String updateApplied(@RequestParam("saraminId") Integer id,
                                @RequestParam("applied") boolean applied,
                                Principal principal) {
        Integer userId = m3Service.findByUsername(principal.getName()).getUserSeq();
        userSaraminRepository.updateApplied(userId, id, applied);
        return "상태 변경 완료";
    }




}