package refinery.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import refinery.model.NaverArticle;
import refinery.model.NaverArticleList;
import refinery.model.NaverSection;
import core.aao.EmptyNaverDataAccessException;
import core.aao.NaverAao;
import elixir.model.Article;
import elixir.model.ArticleTest;
import elixir.model.Journal;
import elixir.model.Section;
import elixir.utility.ElixirUtils;

@RunWith(MockitoJUnitRunner.class)
public class NaverServiceTest {
	
	private static final Logger log = LoggerFactory.getLogger(NaverServiceTest.class);
	
	@InjectMocks
	private NaverService naverService;
	
	@Mock
	private NaverAao naverAaoMock;
	
	private NaverArticleList naverArticleList;
	private List<NaverArticle> naverArticles;
	private List<NaverSection> naverSections;
	private Section section;
	private String datehour;

	private List<Article> articles;
	
	@Before
	public void setup() {
		datehour = ElixirUtils.format("yyyyMMddHHmm", ElixirUtils.getNow()).substring(0, 11);
		prepareNaverSections();
		prepareNaverArticles();
		prepareArticles();
	}


	@Test
	public void getUpdatedArticles() {
		// prepare
		prepareNaverArticleList(naverArticles);
		
		// mock
		when(naverAaoMock.getArticleList(datehour)).thenReturn(naverArticleList);
		
		// exec
		List<Article> actualArticles = naverService.getUpdatedArticles(datehour);
		ArticleTest.ASSERTS(actualArticles, articles);
		
	}
	
	@Test(expected=EmptyNaverDataAccessException.class)
	public void getUpdatedArticles_naverArticleSizeIsZero() {
		// prepare
		prepareNaverArticleList(new NaverArticle[] {});
		
		// mock
		when(naverAaoMock.getArticleList(datehour)).thenReturn(naverArticleList);
		
		// exec - except
		naverService.getUpdatedArticles(datehour);
	}
	
	@Test(expected=EmptyNaverDataAccessException.class)
	public void getUpdatedArticles_naverArticleSizeIsNull() {
		// prepare
		prepareNaverArticleList();
		
		// mock
		when(naverAaoMock.getArticleList(datehour)).thenReturn(naverArticleList);
		
		// exec - except
		naverService.getUpdatedArticles(datehour);
	}


	
	@Test
	public void updateArticleContent() {
		// prepare
		String[] contentArr = new String[] { "content1", "content2", "content3" };
		prepareContent(contentArr);
		
		for (int i=0; i<contentArr.length; i++) {
			Article article = articles.get(i);
			NaverArticle naverArticle = naverArticles.get(i);
			when(naverAaoMock.getArticle(article.getJournal().getId(), article.getArticleId())).thenReturn(naverArticle);			
		}
		
		// exec
		for (Article article : articles) {
			naverService.updateArticleContent(article);
		}
		
		// assert
		for (int i=0; i<contentArr.length; i++) {
			assertThat(articles.get(i).getContent(), notNullValue());
			assertThat(articles.get(i).getContent(), is(contentArr[i]));
		}		
	}
	
	@Test(expected=EmptyNaverDataAccessException.class)
	public void updateArticleContent_empty() {
		// prepare
		String[] contentArr = new String[] { "content1", "content2", "content3" };
		
		for (int i=0; i<contentArr.length; i++) {
			Article article = articles.get(i);
			NaverArticle naverArticle = naverArticles.get(i);
			when(naverAaoMock.getArticle(article.getJournal().getId(), article.getArticleId())).thenReturn(naverArticle);			
		}
		
		// exec - except
		for (Article article : articles) {
			naverService.updateArticleContent(article);
		}	
	}




	private void prepareContent(String[] contents) {
		for (int i=0; i<contents.length; i++) {
			naverArticles.get(i).setContent(contents[i]);
		}
	}

	private void prepareNaverArticleList(List<NaverArticle> naverArticles) {
		prepareNaverArticleList();
		naverArticleList.setArticles(naverArticles);
	}
	
	private void prepareNaverArticleList(NaverArticle[] naverArticleArr) {
		prepareNaverArticleList(Arrays.asList(naverArticleArr));
	}
	
	private void prepareNaverArticleList() {
		naverArticleList = new NaverArticleList();
	}
		

	private void prepareNaverSections() {
		naverSections = Arrays.asList(
				new NaverSection[] {
						new NaverSection("111", "sectionName1"),
						new NaverSection("222", "sectionName2"),
						new NaverSection("333", "sectionName3")
				});
		section = NaverSection.convert(naverSections);
	}
	

	private void prepareArticles() {
		articles = Arrays.asList(new Article[] {
				new Article("111", new Journal("001", "officeName1"), "title1", "orgUrl1", section, "20140124", "113202", "imageUrl1"),
				new Article("222", new Journal("002", "officeName2"), "title2", "orgUrl2", section, "20140124", "123202", "imageUrl2"),
				new Article("333", new Journal("003", "officeName3"), "title3", "orgUrl3", section, "20140124", "133202", "imageUrl3")
		});
		
	}

	private void prepareNaverArticles() {
		naverArticles = Arrays.asList(new NaverArticle[] {
				new NaverArticle("001", "officeName1", "111", "title1", "orgUrl1", naverSections, "20140124", "113202", "imageUrl1"),
				new NaverArticle("002", "officeName2", "222", "title2", "orgUrl2", naverSections, "20140124", "123202", "imageUrl2"),
				new NaverArticle("003", "officeName3", "333", "title3", "orgUrl3", naverSections, "20140124", "133202", "imageUrl3")
		});
	}

}