package refinery.service;

import java.util.List;

import refinery.model.NaverArticle;
import refinery.model.NaverArticleList;
import elixir.model.Article;
import elixir.utility.ElixirUtils;

public class RefineryService {

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
