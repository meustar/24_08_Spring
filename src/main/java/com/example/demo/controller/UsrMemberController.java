package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.MemberService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Member;
import com.example.demo.vo.ResultData;

@Controller
public class UsrMemberController {

	@Autowired
	private MemberService memberService;

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		
		if(Ut.isEmptyOrNull(loginId)) {
			return ResultData.from("F-1", Ut.f("%d(을)를 제대로 입력해주세요.", loginId));
		}
		
		if(Ut.isEmptyOrNull(loginPw)) {
			return ResultData.from("F-2", Ut.f("%d(을)를 제대로 입력해주세요.", loginPw));
		}
		if(Ut.isEmptyOrNull(name)) {
			return ResultData.from("F-3", Ut.f("%d(을)를 제대로 입력해주세요.", name));
		}
		if(Ut.isEmptyOrNull(nickname)) {
			return ResultData.from("F-4", Ut.f("%d(을)를 제대로 입력해주세요.", nickname));
		}
		if(Ut.isEmptyOrNull(cellphoneNum)) {
			return ResultData.from("F-5", Ut.f("%d(을)를 제대로 입력해주세요.", cellphoneNum));
		}
		if(Ut.isEmptyOrNull(email)) {
			return ResultData.from("F-6", Ut.f("%d(을)를 제대로 입력해주세요.", email));
		}
		
		int id = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		if (id == -1) {
			return ResultData.from("F-7", Ut.f("%d(은)는 이미 사용중인 아이디 입니다.", loginId));
		}
		
		if (id == -2) {
			return ResultData.from("F-8", Ut.f("이미 사용중인 이름(%s)과 이메일(%s) 입니다.",  name, email));
		}
		
		Object member = memberService.getMemberById(id);
		
		return (ResultData) member;
		
	}

}