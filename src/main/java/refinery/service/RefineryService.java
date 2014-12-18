package refinery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import refinery.model.NaverArticle;
import refinery.model.NaverArticleList;
import core.aao.NaverAao;
import elixir.model.Article;
import elixir.utility.ElixirUtils;

@Service
public class RefineryService {
	
	@Autowired
	private NaverAao naverAao;

	public void saveArticles() {
		String datehour = ElixirUtils.format("yyyyMMddHHmm", ElixirUtils.getNow()).substring(0,NaverService.DATE_FORMAT.length()-1);
		NaverArticleList naverArticleList = naverAao.getArticleList(datehour);
			
		List<NaverArticle> naverArticles = naverArticleList.getArticles();
		List<Article> articles = NaverArticle.asArticles(naverArticles);
		
		articleService.addArticles(articles);
	}

	//	@Scheduled(cron="0 0/10 * * * ?")
//	public int saveArticles() {
//		String datehour = ElixirUtils.format(NaverService.DATE_FORMAT, ElixirUtils.getNow()).substring(0,NaverService.DATE_FORMAT.length()-1);
//		
//		NaverArticleList naverArticleList = naverAao.getArticleListPer10Min(datehour);
//		List<NaverArticle> naverArticles = naverArticleList.getArticles();
//		List<Article> articles = NaverArticle.asArticles(naverArticles);
//		
//		return articleService.addArticles(articles); 
//	}
}
