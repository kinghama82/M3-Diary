package com.springboot.biz;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String root() {
		return "pages/mainPage";
	}

/*
	@GetMapping("/diary")
	public String diary() {
		return "pages/diary";
	}
*/

}
