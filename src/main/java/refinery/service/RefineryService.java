package refinery.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import refinery.model.NaverHotissue;
import core.aao.NaverAao;

@Service
public class RefineryService {
	private static final Logger log = LoggerFactory.getLogger(RefineryService.class);
	
	@Autowired
	private NaverAao naverAao;
	
	@Scheduled(cron="0/10 * * * * ?")
	public void getHotissues() {
		List<NaverHotissue> naverHotissues = naverAao.getHotissueList();
		log.debug("size: " + naverHotissues.size());
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
