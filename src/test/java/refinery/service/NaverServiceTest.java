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
import refinery.model.NaverHotissue;
import refinery.model.NaverHotissueList;
import refinery.model.NaverSection;
import core.aao.EmptyNaverDataAccessException;
import core.aao.NaverAao;
import elixir.model.Article;
import elixir.model.ArticleTest;
import elixir.model.Hotissue;
import elixir.model.HotissueTest;
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
	private NaverHotissueList naverHotissueList;
	
	private List<NaverHotissue> naverHotissues;
	private List<NaverArticle> naverArticles;
	private List<NaverSection> naverSections;
	
	private Section section;
	private String datehour;

	private List<Article> articles;
	private List<Hotissue> hotissues;
	
	@Before
	public void setup() {
		datehour = ElixirUtils.format("yyyyMMddHHmm", ElixirUtils.getNow()).substring(0, 11);
		prepareNaverSections();
		prepareNaverArticles();
		prepareNaverHotissues();
//		prepareArticles();
		
		
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
	
	@Test
	public void getHotissueList() {
		//prepare
		prepareNaverHotissueList(naverHotissues);
		when(naverAaoMock.getHotissueList()).thenReturn(naverHotissueList);
		
		// exec
		List<Hotissue> actuals = naverService.getHotissueList();
		HotissueTest.ASSERTS(actuals, hotissues);
	}
	
	@Test(expected=EmptyNaverDataAccessException.class)
	public void getHotissueList_naverHotissueIsZero() {
		//prepare
		prepareNaverHotissueList(new NaverHotissue[]{});
		when(naverAaoMock.getHotissueList()).thenReturn(naverHotissueList);
		
		// exec - expcet
		List<Hotissue> actuals = naverService.getHotissueList();
		HotissueTest.ASSERTS(actuals, hotissues);
	}
	
	@Test(expected=EmptyNaverDataAccessException.class)
	public void getHotissueList_naverHotissueIsNull() {
		//prepare
		prepareNaverHotissueList();
		when(naverAaoMock.getHotissueList()).thenReturn(naverHotissueList);
		
		// exec - expcet
		List<Hotissue> actuals = naverService.getHotissueList();
		HotissueTest.ASSERTS(actuals, hotissues);
	}
	
	@Test
	public void getArticleListOfHotissue() {
		// prepare
		String hotissueId = "887522";
		prepareArticleListOfHotissue();
		prepareNaverArticleList(naverArticles);
		
		when(naverAaoMock.getArticleListByHotissueId(hotissueId)).thenReturn(naverArticleList);
		
		// exec
		List<Article> actuals = naverService.getArticleListByHotissueId(hotissueId);
		ArticleTest.ASSERTS(actuals, NaverArticle.convert(naverArticles));
	}

	@Test(expected=EmptyNaverDataAccessException.class)
	public void getArticleListOfHotissue_naverArticleIsZero() {
		// prepare
		String hotissueId = "887522";
		prepareNaverArticleList(new NaverArticle[]{});
		
		when(naverAaoMock.getArticleListByHotissueId(hotissueId)).thenReturn(naverArticleList);
		
		// exec - except
		List<Article> actuals = naverService.getArticleListByHotissueId(hotissueId);
		ArticleTest.ASSERTS(actuals, NaverArticle.convert(naverArticles));
	}
	
	@Test(expected=EmptyNaverDataAccessException.class)
	public void getArticleListOfHotissue_naverArticleIsNull() {
		// prepare
		String hotissueId = "887522";
		prepareNaverArticleList();
		
		when(naverAaoMock.getArticleListByHotissueId(hotissueId)).thenReturn(naverArticleList);
		
		// exec - except
		List<Article> actuals = naverService.getArticleListByHotissueId(hotissueId);
		ArticleTest.ASSERTS(actuals, NaverArticle.convert(naverArticles));
	}
	
	private void prepareArticleListOfHotissue() {
		naverArticles = Arrays.asList(new NaverArticle[]{
				new NaverArticle("001", "officeName1", "111", "title1", "20140124", "113202"),
				new NaverArticle("002", "officeName2", "222", "title2", "20140124", "123202"),
				new NaverArticle("003", "officeName3", "333", "title3", "20140124", "133202")
		});
		
	}




	private void prepareContent(String[] contents) {
		for (int i=0; i<contents.length; i++) {
			naverArticles.get(i).setContent(contents[i]);
		}
	}

	private void prepareNaverHotissueList(List<NaverHotissue> naverHotissues) {
		prepareNaverHotissueList();
		naverHotissueList.setHotissues(naverHotissues);
	}

	private void prepareNaverHotissueList(NaverHotissue[] naverHotisuseArr) {
		prepareNaverHotissueList(Arrays.asList(naverHotisuseArr));
	}

	private void prepareNaverHotissueList() {
		naverHotissueList = new NaverHotissueList();
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
		naverSections = Arrays.asList(new NaverSection[] {
				new NaverSection("111", "sectionName1"),
				new NaverSection("222", "sectionName2"),
				new NaverSection("333", "sectionName3")
		});
		
		section = NaverSection.convert(naverSections);
	}
	

	private void prepareNaverHotissues() {
		naverHotissues = Arrays.asList(new NaverHotissue[] {
				new NaverHotissue(
						"mbs.875.101", "887522", "연애지침서",
						"http://m.news.naver.com/issueGroup.nhn?sid1=103&pid=mbs.875.103&cid=887522&type=issue"),
				new NaverHotissue(
						"mbs.875.102", "893847", "화제의 판결",
						"http://m.news.naver.com/issueGroup.nhn?sid1=102&pid=mbs.875.102&cid=893847&type=issue"),
				new NaverHotissue(
						"mbs.875.103", "887553", "따뜻한 세상",
						"http://m.news.naver.com/issueGroup.nhn?sid1=102&pid=mbs.875.102&cid=893670&type=issue")
		});
		
		hotissues = NaverHotissue.convert(naverHotissues);
		
	}

	private void prepareNaverArticles() {
		naverArticles = Arrays.asList(new NaverArticle[] {
				new NaverArticle("001", "officeName1", "111", "title1", "orgUrl1", naverSections, "20140124", "113202", "imageUrl1"),
				new NaverArticle("002", "officeName2", "222", "title2", "orgUrl2", naverSections, "20140124", "123202", "imageUrl2"),
				new NaverArticle("003", "officeName3", "333", "title3", "orgUrl3", naverSections, "20140124", "133202", "imageUrl3")
		});
		
		articles = NaverArticle.convert(naverArticles);
	}

}