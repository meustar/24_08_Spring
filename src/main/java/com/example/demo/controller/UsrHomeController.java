package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.util.crawlTest;

@Controller
public class UsrHomeController {
	
	// @ResponseBody 가 있으면 return 값을 화면에 보여주고(직접 응답), 없으면 경로 설정.

//	@ResponseBody
	@RequestMapping("/usr/home/main")
	public String showMain() {
		return "/usr/home/main";
	}
	
	
//	@ResponseBody
	@RequestMapping("/")
	public String showRoot() {
		return "redirect:/usr/home/main";
	}
	
	@RequestMapping("/usr/crawl")
	public String doCrawl() {

		crawlTest.crawl();

		return "redirect:/usr/home/main";
	}
	
}
