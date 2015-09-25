package com.gohuinuo.web.showcase.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("cms/article")
public class ArticleController {

	@RequestMapping
	public String toArticle(){
		return "cms/article/article";
	}
	
}
