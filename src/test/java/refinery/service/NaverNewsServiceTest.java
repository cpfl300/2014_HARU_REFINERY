package refinery.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import refinery.apihandler.API;
import elixir.model.Article;
import elixir.service.ArticleService;
import refinery.model.Response;
import refinery.model.ResponseArticle;

@RunWith(MockitoJUnitRunner.class)
public class NaverNewsServiceTest {
	
	@InjectMocks
	private NaverNewsService naverNewsService;
	
	@Mock
	private API naverNewsAPIMock;
	
	@Mock
	private ArticleService articleServiceMock;
	
	private Response responseMock;
	private Response newResponseMock;
	
	private List<Article> articles;
	private List<Article> newArticles;
	
	@Before
	public void setup() {
		makeResponseMock();
		makeNewResponseMock();
	}


	@Test
	public void getArticles() {
		when(naverNewsAPIMock.get("/article", Response.class)).thenReturn(responseMock);
		
		assertThat(naverNewsService.getArticles(), is(articles));
	}
	
	@Test
	/*
	 *  정식 데이터가 존재하지 않으므로 존재여부를 가정
	 *   - 추후 업데이트 날짜가 존재하는 경우 대체할 계획 
	 *   - 지금은 연속 3개의 article이 존재하지 않는 경우 updated 되지 았았다고 가정
	 */
	public void isUpdated() {
		when(naverNewsAPIMock.get("/article", Response.class)).thenReturn(responseMock);
		
		when(articleServiceMock.has(articles.get(0).hashCode())).thenReturn(true);
		when(articleServiceMock.has(articles.get(1).hashCode())).thenReturn(true);
		when(articleServiceMock.has(articles.get(2).hashCode())).thenReturn(true);
		
		assertThat(naverNewsService.isUpdated(), is(true));		
	}
	
	@Test
	public void isNotUpdated() {
		when(naverNewsAPIMock.get("/article", Response.class)).thenReturn(responseMock);
		
		when(articleServiceMock.has(newArticles.get(0).hashCode())).thenReturn(false);	
		assertThat(naverNewsService.isUpdated(), is(false));
		
		when(articleServiceMock.has(newArticles.get(0).hashCode())).thenReturn(true);
		when(articleServiceMock.has(newArticles.get(1).hashCode())).thenReturn(false);
		assertThat(naverNewsService.isUpdated(), is(false));
		
		when(articleServiceMock.has(newArticles.get(0).hashCode())).thenReturn(false);
		when(articleServiceMock.has(newArticles.get(1).hashCode())).thenReturn(false);
		when(articleServiceMock.has(newArticles.get(2).hashCode())).thenReturn(false);
		assertThat(naverNewsService.isUpdated(), is(false));
		
	}
	
	
	private void makeResponseMock() {
		responseMock = new Response();
		
		List<ResponseArticle> responseArticles = new ArrayList<ResponseArticle>();
		articles = new ArrayList<Article>();
		
		ResponseArticle rArticle1 = new ResponseArticle("hotissue1", "title1", "서울신문", "북한", "1111-01-01 01:11:11", "content1", 10000, 7000);
		ResponseArticle rArticle2 = new ResponseArticle("hotissue2", "title2", "한국일보", "금융", "1222-02-02 02:11:11", "content2", 20000, 8000);
		ResponseArticle rArticle3 = new ResponseArticle("hotissue3", "title3", "전자신문", "언론", "1333-03-03 03:11:11", "content3", 30000, 8000);
		
		responseArticles.add(rArticle1);
		responseArticles.add(rArticle2);
		responseArticles.add(rArticle3);
		
		articles.add(rArticle1.toArticle());
		articles.add(rArticle2.toArticle());
		articles.add(rArticle3.toArticle());
		
		responseMock.setResponseArticles(responseArticles);
	}
	
	private void makeNewResponseMock() {
		newResponseMock = new Response();
		
		List<ResponseArticle> responseArticles = new ArrayList<ResponseArticle>();
		newArticles = new ArrayList<Article>();
		
		ResponseArticle rArticle1 = new ResponseArticle("hotissue4", "title4", "서울신문", "북한", "1114-01-01 01:11:11", "content4", 10000, 7000);
		ResponseArticle rArticle2 = new ResponseArticle("hotissue5", "title5", "한국일보", "금융", "1225-02-02 02:11:11", "content5", 20000, 8000);
		ResponseArticle rArticle3 = new ResponseArticle("hotissue6", "title6", "전자신문", "언론", "1335-03-03 03:11:11", "content6", 30000, 8000);
		
		responseArticles.add(rArticle1);
		responseArticles.add(rArticle2);
		responseArticles.add(rArticle3);
		
		newArticles.add(rArticle1.toArticle());
		newArticles.add(rArticle2.toArticle());
		newArticles.add(rArticle3.toArticle());
		
		newResponseMock.setResponseArticles(responseArticles);
		
	}
	

}
