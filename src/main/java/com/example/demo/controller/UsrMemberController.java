package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.MemberService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Member;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsrMemberController {

	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogout(HttpServletRequest req) {

		Rq rq = (Rq) req.getAttribute("rq");

		rq.logout();
		
		return Ut.jsReplace("S-1", Ut.f("로그아웃"), "/");
	}
	
	@RequestMapping("/usr/member/login")
	public String showLogin() {
		return "/usr/member/login";
	}
	
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(HttpServletRequest req, String loginId, String loginPw) {

		Rq rq = (Rq) req.getAttribute("rq");

		if (Ut.isEmptyOrNull(loginId)) {
			return Ut.jsHistoryBack("F-2", "아이디를 입력하지 않았습니다.");
		}
		if (Ut.isEmptyOrNull(loginPw)) {
			return Ut.jsHistoryBack("F-3", "비밀번호를 입력하지 않았습니다.");
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.jsHistoryBack("F-3", Ut.f("%s는(은) 존재하지 않는 아아디 입니다.", loginId));
		}
		if (member.getLoginPw().equals(loginPw) == false) {
			return Ut.jsHistoryBack("F-4", Ut.f("비밀 번호가 틀립니다."));
		}

		rq.login(member);

		return Ut.jsReplace("S-1", Ut.f("%s님 환영합니다.", member.getNickname()), "/");
	}

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData<Member> doJoin(HttpServletRequest req, String loginId, String loginPw, String name,
			String nickname, String cellphoneNum, String email) {

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

		return ResultData.newData(doJoinRd, "새로 생성된 member", member);

	}

	

	

}