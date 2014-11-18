package refinery.service;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import refinery.model.Article;
import core.naver.model.Response;
import core.naver.model.ResponseArticle;
import core.naver.news.api.API;

@Service
public class NaverNewsService {
	
	private static final Logger log = LoggerFactory.getLogger(NaverNewsService.class);
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private API api;
	
//	@Autowired
//	public void setAPI(API api) {
//		this.api = api;
//	}

	@Transactional
	public int getArticles() {
		
		List<ResponseArticle> rArticles = api.get("/article", Response.class).getResponseArticles();
		int size = rArticles.size();
		
		log.debug("response article size: " + size);
		
		Iterator<ResponseArticle> ir = rArticles.iterator();
		while (ir.hasNext()) {
			Article article = ir.next().toArticle();
			articleService.add(article);
		}
		
		return size;
		
	}

}
