package refinery.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import refinery.model.Article;
import core.naver.model.Response;
import core.naver.model.ResponseArticle;
import core.naver.news.api.API;

@Service
public class NaverNewsService {
	
	@Autowired
	private API api;
	
	@Autowired
	private ArticleService articleService;
	
	public List<Article> getArticles() {
		List<ResponseArticle> responseArticles = api.get("/article", Response.class).getResponseArticles();
		List<Article> articles = new ArrayList<Article>();
		
		Iterator<ResponseArticle> ir = responseArticles.iterator();
		while (ir.hasNext()) {
			Article article = ir.next().toArticle();
			articles.add(article);
		}
		
		return articles;
		
	}

	public boolean isUpdated() {
		
		List<Article> articles = this.getArticles();
		boolean result = false;
		for (int i=0; i<3; i++) {
			result |= articleService.has(articles.get(i).hashCode());
			if (result) break;
		}
		
		return result;
	}

}
