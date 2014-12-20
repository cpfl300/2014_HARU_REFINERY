package refinery.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import elixir.model.Hotissue;
import elixir.service.HotissueService;

@Service
public class RefineryService {
	private static final Logger log = LoggerFactory.getLogger(RefineryService.class);
	
	@Autowired
	private NaverService naverService;
	
	@Autowired
	private HotissueService hotissueService;
	
	@Scheduled(cron="0/10 * * * * ?")
	public void saveHotissueList() {
		List<Hotissue> hotissues = naverService.getHotissueList();
		hotissueService.addAll(hotissues);
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
