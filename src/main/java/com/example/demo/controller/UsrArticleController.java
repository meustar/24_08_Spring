package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ArticleService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;

@Controller
public class UsrArticleController {

	// 서비스의 생성자가 없는데도 사용할수 있다.
	@Autowired
	private ArticleService articleService;

	// 액션 메서드, 컨트롤 메서드
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(int id) {

		Article article = articleService.getArticleById(id);

		if (article == null) {
			return ResultData.from("F-9", Ut.f("%d번 게시글은 없습니다.", id));
		}

		return ResultData.from("S-1", Ut.f("%d번 게시글 입니다.", id), article);
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<Article> doModify(int id, String title, String body) {

		Article article = articleService.getArticleById(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시글은 없습니다.", id));
		}

		// getter, setter 잘 알아보자... ㅠㅠ
//		article.setTitle(title);
//		article.setBody(body);
		articleService.modifyArticle(id, title, body);
		
		article = articleService.getArticleById(id);

		return ResultData.from("S-1", Ut.f("%d번 게시글을 수정했습니다.", id), article);
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData<Integer> doDelete(int id) {

		// id가 있는지부터 알아야 함.
		Article article = articleService.getArticleById(id);

		if (article == null) {
			return ResultData.from("F-11", Ut.f("%d번 게시글은 없습니다.", id), id);
		}

		// (id - 1)로 인덱스를 사용해서 지우게 되면. 중간값의 인덱스를 지우면
		// 그 뒷 열의 모든 인덱스가 망가져서 엉뚱한 글을 지우게 된다.
//		articles.remove(article);
		articleService.deleteArticle(id);

		return ResultData.from("S-1", Ut.f("%d번 게시글을 삭제했습니다.", id), id);
	}

	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public ResultData<Article> doWrite(String title, String body) {

		if (Ut.isEmptyOrNull(title)) {
			return ResultData.from("F-1", "제목을 입력해주세요.");
		}
		if (Ut.isEmptyOrNull(body)) {
			return ResultData.from("F-2", "내용을 입력해주세요.");
		}
		
		
		ResultData writeArticleRd = articleService.writeArticle(title, body);
		
		int id  = (int) writeArticleRd.getData1();
		
		Article article = articleService.getArticleById(id);
		
		return ResultData.from(writeArticleRd.getResultCode(), writeArticleRd.getMsg(), article);
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public ResultData<List<Article>> getArticles() {

		List<Article> articles = articleService.getArticles();
		return ResultData.from("S-1", "Article List", articles);
	}

}
