package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrHomeController {
	
	private int count;
	
	public UsrHomeController() {
		count = 0;
	}
	
	@RequestMapping("/usr/home/main1")
	@ResponseBody
	public String showMain() {
		return "안뇽";
	}
	
	@RequestMapping("/usr/home/main2")
	@ResponseBody
	public String showMain2() {
		return "잘가ㅏ";
	}
	
	@RequestMapping("/usr/home/main3")
	@ResponseBody
	public int showMain3() {
		return 1 + 2;
	}
	
//	@RequestMapping("/usr/home/main4")
//	@ResponseBody
//	public int showMain4() {
//		return count++;
//	}
	@RequestMapping("/usr/home/getCount")
	@ResponseBody
	public int getCount() {
		return count++;
	}
	
//	@RequestMapping("/usr/home/main5")
//	@ResponseBody
//	public String showMain5() {
//		count = 0;
//		return "count 값 0 으로 초기화";
//	}
	
	@RequestMapping("/usr/home/setCount")
	@ResponseBody
	public String setCount() {
		count = 0;
		return "count 값 0 으로 초기화";
	}
	
	@RequestMapping("/usr/home/setCountValue")
	@ResponseBody
	public String setCountValue(int value) {
		count = value;
		return "count 값 value 으로 초기화";
//		http://localhost:8080/usr/home/setCountValue?value=22
	}
}
