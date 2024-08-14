package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ArticleService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsrArticleController {

	// 서비스의 생성자가 없는데도 사용할수 있다.
	@Autowired
	private ArticleService articleService;

	// 액션 메서드, 컨트롤 메서드
	@RequestMapping("/usr/article/detail")
	public String showDetail(HttpServletRequest req, Model model, int id) {

		Rq rq = (Rq) req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		model.addAttribute("article", article);
		
		return "usr/article/detail";
	}

	// 로그인 체크 -> 유무 체크 -> 권한 체크 -> 수정
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<Article> doModify(HttpServletRequest req, int id, String title, String body) {
		
		
		Rq rq = (Rq) req.getAttribute("rq");
		
		if (rq.isLogined() == false) {
			return ResultData.from("F-A", "로그인 후 이용하실 수 있습니다.");
		}// -1-

		Article article = articleService.getArticleById(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시글은 없습니다.", id));
		}

		// -3- 권한 체크
		ResultData userCanModifyRd = articleService.userCanModify(rq.getLoginedMemberId(), article);
		
		if (userCanModifyRd.isFail()) {
			return userCanModifyRd;
		}
		if (userCanModifyRd.isSuccess()) {
			articleService.modifyArticle(id, title, body);
			
		}
		
		// getter, setter 잘 알아보자... ㅠㅠ
//		article.setTitle(title);
//		article.setBody(body);
		
		article = articleService.getArticleById(id);

		return ResultData.from(userCanModifyRd.getResultCode(), userCanModifyRd.getMsg(),"수정 된 게시글", 
				article);
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {
		
		Rq rq = (Rq) req.getAttribute("rq");
		
		if (rq.isLogined() == false) {
//			return ResultData.from("F-A", "로그인 후 이용하실 수 있습니다.");
			return Ut.jsReplace("F-A", "로그인 후 이용하세요", "../member/login");
		}

		// id가 있는지부터 알아야 함.
		Article article = articleService.getArticleById(id);

		if (article == null) {
//			return ResultData.from("F-11", Ut.f("%d번 게시글은 없습니다.", id),"입력한 id", id);
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 게시글은 없습니다", id));
		}
		
		ResultData userCanDeleteRd = articleService.userCanDelete(rq.getLoginedMemberId(), article);
		
		if (userCanDeleteRd.isFail()) {
			return Ut.jsHistoryBack(userCanDeleteRd.getResultCode(), userCanDeleteRd.getMsg());
		}
		
		if (userCanDeleteRd.isSuccess()) {
			articleService.deleteArticle(id);
		}

		return Ut.jsReplace(userCanDeleteRd.getResultCode(), userCanDeleteRd.getMsg(), "../article/list");
	}

	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public ResultData<Article> doWrite(HttpServletRequest req, String title, String body) {
		
		Rq rq = (Rq) req.getAttribute("rq");
		
		if (rq.isLogined() == false) {
			return ResultData.from("F-A", "로그인 후 이용하실 수 있습니다.");
		}

		if (Ut.isEmptyOrNull(title)) {
			return ResultData.from("F-1", "제목을 입력해주세요.");
		}
		if (Ut.isEmptyOrNull(body)) {
			return ResultData.from("F-2", "내용을 입력해주세요.");
		}
		
		
		ResultData writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body);
		
		int id  = (int) writeArticleRd.getData1();
		
		Article article = articleService.getArticleById(id);
		
		return ResultData.newData(writeArticleRd,"생성된 게시글", article);
	}

	@RequestMapping("/usr/article/list")
	public String showList(Model model) {

		List<Article> articles = articleService.getArticles();
		
		model.addAttribute("articles", articles);

		return "usr/article/list";
	}

}
