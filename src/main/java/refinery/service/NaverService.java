package refinery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import refinery.model.NaverArticle;
import refinery.model.NaverHotissue;
import core.aao.EmptyNaverDataAccessException;
import core.aao.NaverAao;
import elixir.model.Article;
import elixir.model.Hotissue;

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
		
		return NaverArticle.convert(naverArticles);
	}

	public void updateArticleContent(Article article) {
		NaverArticle naverArticle = naverAao.getArticle(article.getOffice().getOfficeId(), article.getArticleId());
		
		String content = naverArticle.getContent();
		if (content == null || content.length() == 0) {
			throw new EmptyNaverDataAccessException("content of naver article is empty");
		}
		
		article.setContent(content);
	}

	public List<Hotissue> getHotissueList() {
		NaverHotissueList naverHotissueList = naverAao.getHotissueList();
		List<NaverHotissue> naverHotissue = naverHotissueList.getHotissues();
		
		if (naverHotissue == null || naverHotissue.size() == 0) {
			throw new EmptyNaverDataAccessException("naver hotissues have not updated yet");
		}
		
		return NaverHotissue.convert(naverHotissue);
	}

	public List<Article> getArticleListByHotissueId(String hotissueId) {
		NaverArticleList naverArticleList = naverAao.getArticleListByHotissueId(hotissueId);
		List<NaverArticle> naverArticles = naverArticleList.getArticles();
		
		if (naverArticles == null || naverArticles.size() == 0) {
			throw new EmptyNaverDataAccessException("naver articles of hotissue have not existed");
		}
		
		return NaverArticle.convert(naverArticles);
	}

}
