package com.springboot.biz;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String root() {
		return "mainPage";
	}

	@GetMapping("/main")
	public String main() {
		return "mainPage"; // templates/mainPage.html 렌더링
	}

}
