package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.MemberService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Member;
import com.example.demo.vo.ResultData;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsrMemberController {

	@Autowired
	private MemberService memberService;

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData<Member> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {

		if (Ut.isEmptyOrNull(loginId)) {
			return ResultData.from("F-1", Ut.f("%d(을)를 제대로 입력해주세요.", loginId));
		}

		if (Ut.isEmptyOrNull(loginPw)) {
			return ResultData.from("F-2", Ut.f("%d(을)를 제대로 입력해주세요.", loginPw));
		}
		if (Ut.isEmptyOrNull(name)) {
			return ResultData.from("F-3", Ut.f("%d(을)를 제대로 입력해주세요.", name));
		}
		if (Ut.isEmptyOrNull(nickname)) {
			return ResultData.from("F-4", Ut.f("%d(을)를 제대로 입력해주세요.", nickname));
		}
		if (Ut.isEmptyOrNull(cellphoneNum)) {
			return ResultData.from("F-5", Ut.f("%d(을)를 제대로 입력해주세요.", cellphoneNum));
		}
		if (Ut.isEmptyOrNull(email)) {
			return ResultData.from("F-6", Ut.f("%d(을)를 제대로 입력해주세요.", email));
		}

		ResultData doJoinRd = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);

		if (doJoinRd.isFail()) {
			return doJoinRd;
		}

		Member member = memberService.getMemberById((int) doJoinRd.getData1());

		return ResultData.newData(doJoinRd, member);

	}

	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public ResultData<Member> doLogin(HttpSession httpSession, String loginId, String loginPw) {
		
		// 로그인 정보 저장하기. (중복로그인 방지)
		boolean isLogined = false;
		
		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
		}
		
		if (isLogined) {
			return ResultData.from("F-1", "이미 로그인 중입니다.");
		}

		if (Ut.isEmpty(loginId)) {
			return ResultData.from("F-2", "아이디를 입력하지 않았습니다.");
		}
		if (Ut.isEmpty(loginPw)) {
			return ResultData.from("F-3", "비밀번호를 입력하지 않았습니다.");
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return ResultData.from("F-3", Ut.f("%s는(은) 존재하지 않는 아아디 입니다.", loginId));
		}
		if (member.getLoginPw().equals(loginPw) == false ) {
			return ResultData.from("F-4", Ut.f("비밀 번호가 틀립니다."));
		}

		httpSession.setAttribute("loginedMemberId", member.getId());

		return ResultData.from("S-1", Ut.f("%s님 환영합니다.", member.getNickname()));
	}

	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public void doLogout() {

	}

}