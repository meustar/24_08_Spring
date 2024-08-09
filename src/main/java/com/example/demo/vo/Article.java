package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
	int id;
	String title;
	String body;
	
	
	public Article(String title, String body) {
		super();
		this.title = title;
		this.body = body;
	}
	
}

