package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.vo.Article;

@Controller
public class UsrArticleController {

	int lastArticleId;

	List<Article> articles;

	// 생성자
	public UsrArticleController() {
		lastArticleId = 0;
		articles = new ArrayList<>();

		makeTestData();
	}

	// 액션 메서드, 컨트롤 메서드

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {

		// id가 있는지부터 알아야 함.
		Article article = getArticleById(id);

		if (article == null) {
			return id + "번 글은 없습니다.";
		}

		// (id - 1)로 인덱스를 사용해서 지우게 되면. 중간값의 인덱스를 지우면
		// 그 뒷 열의 모든 인덱스가 망가져서 엉뚱한 글을 지우게 된다.
		articles.remove(article);

		return id + "번 글이 삭제되었습니다.";
	}

	private Article getArticleById(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				return article;
			}
		}

		return null;
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public Object doModify(int id, String title, String body) {
	
		Article article = getArticleById(id);

		if (article == null) {
			return id + "번 글은 없습니다.";
		}
		
		// getter, setter 잘 알아보자... ㅠㅠ
		article.setTitle(title);
		article.setBody(body);
		
		return article;
	}


	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {

		Article article = writeArticle(title, body);
		return article;
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {

		return articles;
	}

	// 서비스 메서드

	public void makeTestData() {

		for (int i = 1; i <= 10; i++) {
			String title = "제목" + i;
			String body = "내용" + i;

			writeArticle(title, body);
		}
	}

	private Article writeArticle(String title, String body) {

		int id = lastArticleId + 1;

		Article article = new Article(id, title, body);

		articles.add(article);

		lastArticleId++;

		return article;

	}

}
