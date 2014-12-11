package refinery.service;

import static org.hamcrest.CoreMatchers.is;
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
import elixir.model.Hotissue;
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
	private String datehour;

	private List<Article> articles;
	
	@Before
	public void setup() {
		datehour = ElixirUtils.format("yyyyMMddHHmm", ElixirUtils.getNow()).substring(0, 11);
		prepareNaverSections();
		prepareNaverArticles();
		prepareArticlesC();
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
	public void udpateArticleContent() {
		
		
		//List<String> actualIds = naverService.getArticles(datehour);
		
		
		
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
	}
	

	private void prepareArticlesOfNaverArticleList() {
		articles = Arrays.asList(new Article[] {
				new Article("001", new Journal("011", "officeName1"), NaverSection.asSections(naverSections), "title1", "imageUrl1", "20140124", "113202", 10000, 1000),
				new Article("002", new Journal("022", "officeName2"), NaverSection.asSections(naverSections), "title2", "imageUrl2", "20140124" ,"123202", 20000, 2000),
				new Article("003", new Journal("033", "officeName3"), NaverSection.asSections(naverSections), "title3", "imageUrl3", "20140124" ,"133202", 30000, 3000)
		});
		
	}

	private void prepareNaverArticles() {
		naverArticles = Arrays.asList(new NaverArticle[] {
				new NaverArticle("1", "officeName1", "1", "11", "1", "title1",
						"subcontent1", "content1", "orgUrl1", naverSections, "20140124", "113202", "imageUrl1", "reporter1", "copyright1",
						"10000", "1000", "1"),
				new NaverArticle("2", "officeName2", "2", "22", "2", "title2",
						"subcontent2", "content2", "orgUrl2", naverSections, "20140124", "123202", "imageUrl2", "reporter2", "copyright2",
						"20000", "2000", "2"),
				new NaverArticle("3", "officeName3", "3", "33", "3", "title3",
						"subcontent3", "content3", "orgUrl3", naverSections, "20140124", "133202", "imageUrl3", "reporter3", "copyright3",
						"30000", "3000", "3")
		});
	}



	


	
//	@Test
//	public void isUpdated() {
//		String datehour = ElixirUtils.format(NaverService.DATE_FORMAT, ElixirUtils.getNow()).substring(0,NaverService.DATE_FORMAT.length()-1);
//		// articles is null
//		when(naverAaoMock.getArticleListPer10Min(datehour)).thenReturn(naverArticleList);
//		assertThat(naverService.isUpdated(), is(false));
//		
//		// articles is not null
//		naverArticleList.setArticles(Arrays.asList(new NaverArticle[]{ new NaverArticle() }));
//		when(naverAaoMock.getArticleListPer10Min(datehour)).thenReturn(naverArticleList);
//		assertThat(naverService.isUpdated(), is(true));
//	}
	
	
//	@Test
//	public void getArticles() {
//		when(naverAaoMock.get("/article", Response.class)).thenReturn(responseMock);
//		
//		assertThat(naverService.getArticles(), is(articles));
//	}
//	
//
//	
//	@Test
//	public void isNotUpdated() {
//		when(naverAaoMock.get("/article", Response.class)).thenReturn(responseMock);
//		
//		when(articleServiceMock.has(newArticles.get(0).hashCode())).thenReturn(false);	
//		assertThat(naverService.isUpdated(), is(false));
//		
//		when(articleServiceMock.has(newArticles.get(0).hashCode())).thenReturn(true);
//		when(articleServiceMock.has(newArticles.get(1).hashCode())).thenReturn(false);
//		assertThat(naverService.isUpdated(), is(false));
//		
//		when(articleServiceMock.has(newArticles.get(0).hashCode())).thenReturn(false);
//		when(articleServiceMock.has(newArticles.get(1).hashCode())).thenReturn(false);
//		when(articleServiceMock.has(newArticles.get(2).hashCode())).thenReturn(false);
//		assertThat(naverService.isUpdated(), is(false));
//		
//	}
//	
//	
//	private void makeResponseMock() {
//		responseMock = new Response();
//		
//		List<ResponseArticle> responseArticles = new ArrayList<ResponseArticle>();
//		articles = new ArrayList<Article>();
//		
//		ResponseArticle rArticle1 = new ResponseArticle("hotissue1", "title1", "서울신문", "북한", "1111-01-01 01:11:11", "content1", 10000, 7000);
//		ResponseArticle rArticle2 = new ResponseArticle("hotissue2", "title2", "한국일보", "금융", "1222-02-02 02:11:11", "content2", 20000, 8000);
//		ResponseArticle rArticle3 = new ResponseArticle("hotissue3", "title3", "전자신문", "언론", "1333-03-03 03:11:11", "content3", 30000, 8000);
//		
//		responseArticles.add(rArticle1);
//		responseArticles.add(rArticle2);
//		responseArticles.add(rArticle3);
//		
//		articles.add(rArticle1.toArticle());
//		articles.add(rArticle2.toArticle());
//		articles.add(rArticle3.toArticle());
//		
//		responseMock.setResponseArticles(responseArticles);
//	}
//	
//	private void makeNewResponseMock() {
//		newResponseMock = new Response();
//		
//		List<ResponseArticle> responseArticles = new ArrayList<ResponseArticle>();
//		newArticles = new ArrayList<Article>();
//		
//		ResponseArticle rArticle1 = new ResponseArticle("hotissue4", "title4", "서울신문", "북한", "1114-01-01 01:11:11", "content4", 10000, 7000);
//		ResponseArticle rArticle2 = new ResponseArticle("hotissue5", "title5", "한국일보", "금융", "1225-02-02 02:11:11", "content5", 20000, 8000);
//		ResponseArticle rArticle3 = new ResponseArticle("hotissue6", "title6", "전자신문", "언론", "1335-03-03 03:11:11", "content6", 30000, 8000);
//		
//		responseArticles.add(rArticle1);
//		responseArticles.add(rArticle2);
//		responseArticles.add(rArticle3);
//		
//		newArticles.add(rArticle1.toArticle());
//		newArticles.add(rArticle2.toArticle());
//		newArticles.add(rArticle3.toArticle());
//		
//		newResponseMock.setResponseArticles(responseArticles);
//		
//	}

	

}