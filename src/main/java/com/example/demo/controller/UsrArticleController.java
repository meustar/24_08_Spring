package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ArticleService;
import com.example.demo.vo.Article;

@Controller
public class UsrArticleController {

	// 서비스의 생성자가 없는데도 사용할수 있다.
	@Autowired
	private ArticleService articleService;

	// 액션 메서드, 컨트롤 메서드
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public Object getArticle(int id) {

		Article article = articleService.getArticleById(id);

		if (article == null) {
			return id + "번 글은 없습니다.";
		}

		return article;
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public Object doModify(int id, String title, String body) {

		Article article = articleService.getArticleById(id);

		if (article == null) {
			return id + "번 글은 없습니다.";
		}

		// getter, setter 잘 알아보자... ㅠㅠ
//		article.setTitle(title);
//		article.setBody(body);
		articleService.modifyArticle(id, title, body);

		return article;
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {

		// id가 있는지부터 알아야 함.
		Article article = articleService.getArticleById(id);

		if (article == null) {
			return id + "번 글은 없습니다.";
		}

		// (id - 1)로 인덱스를 사용해서 지우게 되면. 중간값의 인덱스를 지우면
		// 그 뒷 열의 모든 인덱스가 망가져서 엉뚱한 글을 지우게 된다.
//		articles.remove(article);
		articleService.deleteArticle(id);

		return id + "번 글이 삭제되었습니다.";
	}

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {

		Article article = articleService.writeArticle(title, body);
		return article;
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {

		return articleService.getArticles();
	}

}
