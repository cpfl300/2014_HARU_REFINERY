package refinery.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import refinery.dao.ArticleDao;
import refinery.model.Article;
import core.naver.model.Response;
import core.naver.model.ResponseArticle;
import core.naver.news.api.API;

@RunWith(MockitoJUnitRunner.class)
public class NaverNewsServiceTest {
	
	@InjectMocks
	private NaverNewsService naverNewsService;
	
	@Mock
	private API naverNewsAPIMock;
	
	@Mock
	private ArticleService articleService;
	
	@Mock
	private ArticleDao articleDaoMock;
	
	@Mock
	private Response responseMock;
	
	private List<ResponseArticle> rArticles;
	
	@Before
	public void setup() {
		makeResponseMock();
	}
	
	@Test
	public void getArticle() {		
		when(articleService.add(any(Article.class))).thenReturn(1);
		when(responseMock.getResponseArticles()).thenReturn(rArticles);
		when(naverNewsAPIMock.get("/article", Response.class)).thenReturn(responseMock);
		when(articleDaoMock.getCount()).thenReturn(rArticles.size());
		
		assertThat(naverNewsService.getArticles(), is(articleDaoMock.getCount()));
		
	}
	
	private void makeResponseMock() {
		rArticles = new ArrayList<ResponseArticle>();
		ResponseArticle rArticle1 = new ResponseArticle("hotissue1", "title1", "서울신문", "북한", "1111-01-01 01:11:11", "content1", 10000, 7000);
		ResponseArticle rArticle2 = new ResponseArticle("hotissue2", "title2", "한국일보", "금융", "1222-02-02 02:11:11", "content2", 20000, 8000);
		ResponseArticle rArticle3 = new ResponseArticle("hotissue3", "title3", "전자신문", "언론", "1333-03-03 03:11:11", "content3", 30000, 8000);
		
		rArticles.add(rArticle1);
		rArticles.add(rArticle2);
		rArticles.add(rArticle3);
	}

}
