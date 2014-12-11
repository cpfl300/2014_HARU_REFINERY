package refinery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import refinery.model.NaverArticle;
import refinery.model.NaverArticleList;
import core.aao.EmptyNaverDataAccessException;
import core.aao.NaverAao;
import elixir.model.Article;

@Service
public class NaverService {
	
	@Autowired
	private NaverAao naverAao;
	
	public List<Article> getUpdatedArticles(String datehour) {

		NaverArticleList naverArticleList = naverAao.getArticleList(datehour);
		List<NaverArticle> naverArticles = naverArticleList.getArticles();		
		
		if (naverArticles == null || naverArticles.size() == 0) {
			throw new EmptyNaverDataAccessException("naver articles have not updated yet");
		}
		
		return NaverArticle.asArticles(naverArticles);
	}

}
