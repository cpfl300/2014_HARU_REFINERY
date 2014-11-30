package refinery.scheduler.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import elixir.model.Article;
import elixir.service.ArticleService;
import refinery.service.NaverNewsService;

@Component
public class NaverNewsTask {
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private NaverNewsService naverNewsService;
	
	
	public int getNews() {
		
		if (naverNewsService.isUpdated()) {
			
			return 0;
		}
		
		List<Article> articles = naverNewsService.getArticles();
		
		return articleService.addArticles(articles);
		
	}

}
