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
		
		ResultData doJoinRd = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		if (doJoinRd.isFail()) {
			return doJoinRd;
		}
		
		Member member = memberService.getMemberById((int)doJoinRd.getData1());
		
		return ResultData.newData(doJoinRd, member);
		
	}

}