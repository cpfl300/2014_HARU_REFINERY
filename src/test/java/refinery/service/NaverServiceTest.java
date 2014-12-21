package refinery.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import refinery.aao.NaverAao;
import refinery.model.NaverArticle;
import refinery.model.NaverArticleTest;
import refinery.model.NaverHotissue;
import refinery.model.NaverHotissueTest;
import refinery.model.NaverHotissues;
import refinery.model.NaverSection;
import refinery.model.NaverSectionTest;
import refinery.model.NaverSections;
import refinery.model.NaverSectionsTest;
import elixir.model.Article;
import elixir.model.ArticleTest;
import elixir.model.Hotissue;
import elixir.model.HotissueTest;
import elixir.model.OfficeTest;
import elixir.model.Section;
import elixir.model.Signature;
import elixir.model.SignatureTest;
import elixir.utility.ElixirUtils;

@RunWith(MockitoJUnitRunner.class)
public class NaverServiceTest {
	
	private static final Logger log = LoggerFactory.getLogger(NaverServiceTest.class);
	
	@InjectMocks
	private NaverService naverService;
	
	@Mock
	private NaverAao naverAaoMock;
	
	private List<NaverHotissue> naverHotissues;
	private List<NaverArticle> naverArticles;
	private List<NaverSection> naverSections;
	
	private Section section;
	private String datehour;

	private List<Article> articles;
	private List<Hotissue> hotissues;
	private List<List<NaverSection>> naverSectionsList;
	private List<Section> sections;
	private List<Signature> signatures;
	
	@Before
	public void setup() {
		datehour = ElixirUtils.format("yyyyMMddHHmm", ElixirUtils.getNow()).substring(0, 11);
		naverSectionsList = new ArrayList<List<NaverSection>>();
		naverSectionsList.add(naverSections);
		naverSectionsList.add(naverSections);
		naverSectionsList.add(naverSections);
		
		naverSections = NaverSectionTest.preparedList();
		sections = NaverSections.convert(naverSections);
		
		naverHotissues = NaverHotissueTest.preparedList();
		hotissues = NaverHotissues.convert(naverHotissues);
		
		signatures = SignatureTest.preparedList();
		prepareArticles();
	}

	

	// getHotissueList
	@Test
	public void getHotissueList() {
		// mock
		when(naverAaoMock.getHotissueList()).thenReturn(naverHotissues);
		
		// exec
		List<Hotissue> actuals = naverService.getHotissueList();
		HotissueTest.ASSERTS(actuals, hotissues);
	}
	
	
	// getArticleList
	@Test
	public void getArticleList() {
		// mock
		when(naverAaoMock.getArticleList(datehour)).thenReturn(naverArticles);
		
		// exec
		List<Article> actuals = naverService.getArticleList(datehour);
		
		verify(naverAaoMock, times(1)).getArticleList(datehour);
	}

	
	@Test
	public void getArticle() {
		for (int i=0; i<signatures.size(); i++) {
			Signature signature = signatures.get(i);
			NaverArticle naverArticle = naverArticles.get(i);
			when(naverAaoMock.getArticle(signature)).thenReturn(naverArticle);
			
			Article actual = naverService.getArticle(signature);
			assertThat(actual.getContent(), notNullValue());
			assertThat(actual.getContent(), not(is("")));
		}
	}

	
	// getUpdateArticles
//	@Test
//	public void getUpdatedArticles() {
//		// prepare
//		prepareNaverArticleList(naverArticles);
//		prepareMockForGetUpdateArticles();
//		
//		// exec
//		List<Article> actualArticles = naverService.getUpdatedArticles(datehour);
//		ArticleTest.ASSERTS(actualArticles, articles);
//		
//	}
//	
//	@Test(expected=EmptyNaverDataAccessException.class)
//	public void getUpdatedArticles_naverArticleSizeIsZero() {
//		// prepare
//		prepareNaverArticleList(new NaverArticle[] {});
//		prepareMockForGetUpdateArticles();
//		
//		// exec - except
//		naverService.getUpdatedArticles(datehour);
//	}
//	
//	@Test(expected=EmptyNaverDataAccessException.class)
//	public void getUpdatedArticles_naverArticleSizeIsNull() {
//		// prepare
//		prepareNaverArticleList();
//		prepareMockForGetUpdateArticles();
//		
//		// exec - except
//		naverService.getUpdatedArticles(datehour);
//	}
//
//
//	// updateArticleContent
//	@Test
//	public void updateArticleContent() {
//		// prepare
//		String[] contentArr = new String[] { "content1", "content2", "content3" };
//		prepareContent(contentArr);
//		prepareMocksForUpdateArticleContent(contentArr);
//		
//		// exec
//		for (Article article : articles) {
//			naverService.updateArticleContent(article);
//		}
//		
//		// assert
//		for (int i=0; i<contentArr.length; i++) {
//			assertThat(articles.get(i).getContent(), notNullValue());
//			assertThat(articles.get(i).getContent(), is(contentArr[i]));
//		}		
//	}
//
//	@Test(expected=EmptyNaverDataAccessException.class)
//	public void updateArticleContent_empty() {
//		// prepare
//		String[] contentArr = new String[] { "content1", "content2", "content3" };
//		prepareMocksForUpdateArticleContent(contentArr);
//		
//		// exec - except
//		for (Article article : articles) {
//			naverService.updateArticleContent(article);
//		}	
//	}
//	
//	
//	// getArticleListOfHotissue
//	@Test
//	public void getArticleListOfHotissue() {
//		final String hotissueId = "887522";
//		
//		// prepare
//		createArticleListOfHotissue();
//		prepareNaverArticleList(naverArticles);
//		prepareMocksGetArticleListOfHotissue(hotissueId);
//		
//		// exec
//		List<Article> actuals = naverService.getArticleListByHotissueId(hotissueId);
//		ArticleTest.ASSERTS(actuals, NaverArticle.convert(naverArticles));
//	}
//
//
//	@Test(expected=EmptyNaverDataAccessException.class)
//	public void getArticleListOfHotissue_naverArticleIsZero() {
//		final String hotissueId = "887522";
//		
//		// prepare
//		prepareNaverArticleList(new NaverArticle[]{});
//		prepareMocksGetArticleListOfHotissue(hotissueId);
//		
//		// exec - except
//		List<Article> actuals = naverService.getArticleListByHotissueId(hotissueId);
//		ArticleTest.ASSERTS(actuals, NaverArticle.convert(naverArticles));
//	}
//	
//	@Test(expected=EmptyNaverDataAccessException.class)
//	public void getArticleListOfHotissue_naverArticleIsNull() {
//		final String hotissueId = "887522";
//		
//		// prepare
//		prepareNaverArticleList();
//		prepareMocksGetArticleListOfHotissue(hotissueId);
//		
//		// exec - except
//		List<Article> actuals = naverService.getArticleListByHotissueId(hotissueId);
//		ArticleTest.ASSERTS(actuals, NaverArticle.convert(naverArticles));
//	}
//
//	
//	
//	// prepare mocks
//	private void prepareMocksGetArticleListOfHotissue(String hotissueId) {
//		when(naverAaoMock.getArticleListByHotissueId(hotissueId)).thenReturn(naverArticleList);		
//	}
//	
//	private void prepareMockForGetUpdateArticles() {
//		when(naverAaoMock.getArticleList(datehour)).thenReturn(naverArticleList);
//	}	
//	
//	private void prepareMocksForUpdateArticleContent(String[] contentArr) {
//		// mock
//		for (int i=0; i<contentArr.length; i++) {
//			Article article = articles.get(i);
//			NaverArticle naverArticle = naverArticles.get(i);
//			
//			String officeId = article.getOffice().getOfficeId();
//			String articleId = article.getArticleId();
//			
//			when(naverAaoMock.getArticle(officeId, articleId)).thenReturn(naverArticle);			
//		}
//	}
//	
//	
//	// prepare etcs
//
//	private void prepareContent(String[] contents) {
//		for (int i=0; i<contents.length; i++) {
//			naverArticles.get(i).setContent(contents[i]);
//		}
//	}
//
//	private void prepareNaverHotissueList(List<NaverHotissue> naverHotissues) {
//		prepareNaverHotissueList();
//		naverHotissueList.setHotissues(naverHotissues);
//	}
//
//	private void prepareNaverHotissueList(NaverHotissue[] naverHotisuseArr) {
//		prepareNaverHotissueList(Arrays.asList(naverHotisuseArr));
//	}
//
//	private void prepareNaverHotissueList() {
//		naverHotissueList = new NaverHotissueList();
//	}
//
//	private void prepareNaverArticleList(List<NaverArticle> naverArticles) {
//		prepareNaverArticleList();
//		naverArticleList.setArticles(naverArticles);
//	}
//	
//	private void prepareNaverArticleList(NaverArticle[] naverArticleArr) {
//		prepareNaverArticleList(Arrays.asList(naverArticleArr));
//	}
//	
//	private void prepareNaverArticleList() {
//		naverArticleList = new NaverArticleList();
//	}
//		
//	
//	// create objs
//
//
//
//	private void createNaverArticles() {
//		naverArticles = NaverArticleTest.preparedList(naverSectionsList);
//		
//		articles = NaverArticle.convert(naverArticles);
//	}
//	
//	
//	private void createArticleListOfHotissue() {
//		naverArticles = NaverArticleTest.preparedList(
//				naverSectionsList, new String[]{"officeId", "officeName", "articleId", "title", "serviceDate", "serviceTime"});
//	}
	
	private void prepareArticles() {
		List<List<NaverSection>> naverSectionsList = new ArrayList<List<NaverSection>>();
		List<NaverSection> ns1 = NaverSectionsTest.preparedList1();
		List<NaverSection> ns2 = NaverSectionsTest.preparedList2();
		List<NaverSection> ns3 = NaverSectionsTest.preparedList3();
		naverSectionsList.add(ns1);
		naverSectionsList.add(ns2);
		naverSectionsList.add(ns3);
		
		List<Section> s1 = NaverSections.convert(ns1);
		List<Section> s2 = NaverSections.convert(ns2);
		List<Section> s3 = NaverSections.convert(ns3);
		List<List<Section>> ss = new ArrayList<List<Section>>();
		ss.add(s1);
		ss.add(s2);
		ss.add(s3);
		
		naverArticles = NaverArticleTest.preparedList(naverSectionsList);
		articles = ArticleTest.preparedList(OfficeTest.preparedList(), ss);
	}

}