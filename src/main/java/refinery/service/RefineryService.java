package refinery.service;

import java.util.List;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import elixir.model.Article;
import elixir.model.Articles;
import elixir.model.Hotissue;
import elixir.model.Signature;
import elixir.service.ArticleService;
import elixir.service.HotissueService;

@Service
public class RefineryService {
	private static final Logger log = LoggerFactory.getLogger(RefineryService.class);
	
	@Autowired
	private NaverService naverService;
	
	@Autowired
	private HotissueService hotissueService;
	
	@Autowired
	private ArticleService articleService;
	
	
	public void saveHotissueList() {
		List<Hotissue> hotissues = naverService.getHotissueList();
		hotissueService.addAll(hotissues);
	}
	
	
	
	public void saveArticleList(String datehour) {		
		List<Article> articles = naverService.getArticleList(datehour);
		List<Signature> signatures = Articles.sign(articles);
		articleService.addAll(articles);
		
		//updateContentOfArticleList(signatures);
	}
	
	@Async
	public Future<Integer> updateContentOfArticleList(List<Signature> signatures) {
		
		for (Signature signature : signatures) {
			updateContentOfArticle(signature);
		}
		
		return new AsyncResult<Integer>(-2);
	}

	@Async
	public Future<Integer> updateContentOfArticle(Signature signature) {
		
		try {
			Thread.sleep(1000);
			log.debug("in updateCA");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Article article = naverService.getArticle(signature);
//		articleService.updateContent(article);
		
		return new AsyncResult<Integer>(-2);
	}
}
	
	
//	@Autowired
//	private ArticleService articleSerivce;
	
//	@Scheduled(cron="0 0/10 * * * ?")
//	public void saveArticles() {
//		String datehour = ElixirUtils.format("yyyyMMddHHmm", ElixirUtils.getNow()).substring(0,"yyyyMMddHHmm".length()-1);
//		NaverArticleList naverArticleList = naverAao.getArticleList(datehour);
//			
//		List<NaverArticle> naverArticles = naverArticleList.getArticles();
////		List<Article> articles = NaverArticle.asArticles(naverArticles);
////		
////		articleSerivce.addArticles(articles);
//	}
//}
	
//	@Scheduled(cron="0 0/10 * * * ?")
//	public void saveArticles() {
//		String datehour = ElixirUtils.format("yyyyMMddHHmm", ElixirUtils.getNow()).substring(0,"yyyyMMddHHmm".length()-1);
//		NaverArticleList naverArticleList = naverAao.getArticleList(datehour);
//			
//		List<NaverArticle> naverArticles = naverArticleList.getArticles();
////		List<Article> articles = NaverArticle.asArticles(naverArticles);
////		
////		articleSerivce.addArticles(articles);
//	}
//}
