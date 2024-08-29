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
	private Rq rq;

	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogout(HttpServletRequest req) {

		rq.logout();
		
		return Ut.jsReplace("S-1", Ut.f("로그아웃"), "/");
	}
	
	@RequestMapping("/usr/member/login")
	public String showLogin(HttpServletRequest req) {
		return "/usr/member/login";
	}
	
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(HttpServletRequest req, String loginId, String loginPw, String afterLoginUri) {

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
		
		if (afterLoginUri.length() > 0) {
			
			return Ut.jsReplace("S-1", Ut.f("%s님 환영합니다", member.getNickname()), afterLoginUri);
		}

		return Ut.jsReplace("S-1", Ut.f("%s님 환영합니다.", member.getNickname()), "/");
	}
	
	@RequestMapping("/usr/member/join")
	public String showJoin(HttpServletRequest req) {

		return "/usr/member/join";
	}

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(HttpServletRequest req, String loginId, String loginPw, String name, String nickname,
			String cellphoneNum, String email) {

		if (Ut.isEmptyOrNull(loginId)) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d(을)를 제대로 입력해주세요.", loginId));
		}

		if (Ut.isEmptyOrNull(loginPw)) {
			return Ut.jsHistoryBack("F-2", Ut.f("%d(을)를 제대로 입력해주세요.", loginPw));
		}
		if (Ut.isEmptyOrNull(name)) {
			return Ut.jsHistoryBack("F-3", Ut.f("%d(을)를 제대로 입력해주세요.", name));
		}
		if (Ut.isEmptyOrNull(nickname)) {
			return Ut.jsHistoryBack("F-4", Ut.f("%d(을)를 제대로 입력해주세요.", nickname));
		}
		if (Ut.isEmptyOrNull(cellphoneNum)) {
			return Ut.jsHistoryBack("F-5", Ut.f("%d(을)를 제대로 입력해주세요.", cellphoneNum));
		}
		if (Ut.isEmptyOrNull(email)) {
			return Ut.jsHistoryBack("F-6", Ut.f("%d(을)를 제대로 입력해주세요.", email));
		}

		ResultData joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);

		if (joinRd.isFail()) {
			return Ut.jsHistoryBack(joinRd.getResultCode(), joinRd.getMsg());
		}

		Member member = memberService.getMemberById((int) joinRd.getData1());

		return Ut.jsReplace(joinRd.getResultCode(), joinRd.getMsg(), "../member/login");

	}
	
	@RequestMapping("/usr/member/myPage")
	public String showmyPage() {
		return "usr/member/myPage";
	}

	@RequestMapping("/usr/member/checkPw")
	public String showCheckPw() {
		return "usr/member/checkPw";
	}
	
	@RequestMapping("/usr/member/doCheckPw")
	@ResponseBody
	public String doCheckPw(String loginPw) {
		if (Ut.isEmptyOrNull(loginPw)) {
			return Ut.jsHistoryBack("F-1", "비밀번호를 입력해주세요.");
		}

		if (rq.getLoginedMember().getLoginPw().equals(loginPw) == false) {
			return Ut.jsHistoryBack("F-2", "비밀번호가 틀렸습니다.");
		}

		return Ut.jsReplace("S-1", Ut.f("비밀번호 확인 성공"), "modify");
	}

	@RequestMapping("/usr/member/modify")
	public String showmyModify() {
		return "usr/member/modify";
	}
	
	@RequestMapping("/usr/member/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, String loginPw, String name, String nickname, String cellphoneNum, String email) {

		Rq rq = (Rq) req.getAttribute("rq");
		
		// 비번을 입력하지 않아도 회원정보 수정이 가능하도록 만들어야 함.(비번은 바꾸기 싫을때.)
		// 비번은 안바꾸는거 가능(사용자 입장). 비번 null 체크 X
		
		if (Ut.isEmptyOrNull(name)) {
			return Ut.jsHistoryBack("F-3", Ut.f("%d(을)를 제대로 입력해주세요.", name));
		}
		if (Ut.isEmptyOrNull(nickname)) {
			return Ut.jsHistoryBack("F-4", Ut.f("%d(을)를 제대로 입력해주세요.", nickname));
		}
		if (Ut.isEmptyOrNull(cellphoneNum)) {
			return Ut.jsHistoryBack("F-5", Ut.f("%d(을)를 제대로 입력해주세요.", cellphoneNum));
		}
		if (Ut.isEmptyOrNull(email)) {
			return Ut.jsHistoryBack("F-6", Ut.f("%d(을)를 제대로 입력해주세요.", email));
		}
		
		ResultData modifyRd;
		
		if (Ut.isEmptyOrNull(loginPw)) {
			
			modifyRd = memberService.modifyWithoutPw(rq.getLoginedMemberId(), name, nickname, cellphoneNum, email);
		
		} else {
			modifyRd = memberService.modify(rq.getLoginedMemberId(), loginPw, name, nickname, cellphoneNum, email);
		}
		

		return Ut.jsReplace(modifyRd.getResultCode(), modifyRd.getMsg(), "../member/myPage");
	
	}

	

	

}