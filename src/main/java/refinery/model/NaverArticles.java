package refinery.model;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import elixir.model.Article;

public class NaverArticles {
	
	private static final Logger log = LoggerFactory.getLogger(NaverArticles.class);

	public static List<Article> convert(List<NaverArticle> naverArticles) {
		List<Article> convertedList = new ArrayList<Article>();
		
		// 마지막 section만 list에 담아 전달
		for (NaverArticle naverArticle : naverArticles) {
			convertedList.add(naverArticle.convert());
		}
		
		return convertedList;
	}

}
