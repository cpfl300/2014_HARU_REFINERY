package scheduler.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import refinery.model.Article;
import refinery.model.Hotissue;
import refinery.service.ArticleService;
import refinery.service.HalfDayService;
import refinery.service.HotissueService;

@Service
public class HalfDayTask {
	
	@Autowired
	private HalfDayService HalfDayService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private HotissueService hotissueService;

	@Transactional
	public int extract(String timestamp, String[] dates, int count) {

		articleService.calcScore(dates[0], dates[1]);
		hotissueService.calcScore(dates[0], dates[1]);
		
		List<Hotissue> hotissues = hotissueService.getWithArticlesByOrderedScore(count);
		List<Article> articles = Article.asListWithSequenceIncludeTimestamp(hotissues, timestamp);
		
		return HalfDayService.addArticles(articles);
	}

}
