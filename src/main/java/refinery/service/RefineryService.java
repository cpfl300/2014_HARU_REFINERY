package refinery.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import elixir.model.Article;
import elixir.model.Hotissue;
import elixir.service.ArticleService;
import elixir.service.HotissueService;
import elixir.utility.ElixirUtils;

@Service
public class RefineryService {
	private static final Logger log = LoggerFactory.getLogger(RefineryService.class);
	
	@Autowired
	private NaverService naverService;
	
	@Autowired
	private HotissueService hotissueService;
	
	@Autowired
	private ArticleService articleService;
	
	@Scheduled(cron="* 0/10 * * * ?")
	public void saveHotissueList() {
		List<Hotissue> hotissues = naverService.getHotissueList();
		hotissueService.addAll(hotissues);
	}
	
//	@Scheduled(cron="* 0/10 * * * ?")
	@Scheduled(cron="0/10 * * * * ?")
	public void saveArticleList() {
		//String datehour = ElixirUtils.format("yyyyMMddHHmm", ElixirUtils.getNow()).substring(0, 11);
		String datehour = "20141221192";
		log.debug("datehour: " + datehour);
		
		List<Article> articles = naverService.getArticleList(datehour);
		articleService.addAll(articles);
		
		updateArticleContent();
		log.debug("saveArticleList method end");
	}
	
	@Async
	private void updateArticleContent() {
		try {
			Thread.sleep(1000);
			log.debug("updateArticleContent exec");
		} catch (InterruptedException e) {
			log.debug("error", e);
		}
		
		
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
