package com.springboot.biz;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String root() {
		return "pages/mainPage";
	}

	@GetMapping("/main")
	public String main() {
		return "pages/mainPage"; // templates/mainPage.html 렌더링
	}

/*
	@GetMapping("/diary")
	public String diary() {
		return "pages/diary";
	}
*/

}
