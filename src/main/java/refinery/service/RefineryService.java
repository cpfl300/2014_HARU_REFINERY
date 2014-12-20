package refinery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import refinery.model.NaverArticle;
import refinery.model.NaverArticleList;
import core.aao.NaverAao;
import elixir.model.Article;
import elixir.service.ArticleService;
import elixir.utility.ElixirUtils;

@Service
public class RefineryService {
	
	@Autowired
	private NaverAao naverAao;
	
//	@Autowired
//	private ArticleService articleSerivce;
	
	@Scheduled(cron="0 0/10 * * * ?")
	public void saveArticles() {
		String datehour = ElixirUtils.format("yyyyMMddHHmm", ElixirUtils.getNow()).substring(0,"yyyyMMddHHmm".length()-1);
		NaverArticleList naverArticleList = naverAao.getArticleList(datehour);
			
		List<NaverArticle> naverArticles = naverArticleList.getArticles();
//		List<Article> articles = NaverArticle.asArticles(naverArticles);
//		
//		articleSerivce.addArticles(articles);
	}
}
