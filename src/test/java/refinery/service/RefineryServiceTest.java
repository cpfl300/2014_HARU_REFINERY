package refinery.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import elixir.model.Article;

@RunWith(MockitoJUnitRunner.class)
public class RefineryServiceTest {
	
	@InjectMocks
	private RefineryService refineryService;
	
	@Mock
	private NaverService naverSerivce;
	
//	@Mock
//	private ArticleService articleService;
	
	private List<Article> articles;
	
	@Before
	public void setup() {
		prepareArticles();
	}

//	@Test
//	public void saveArticles(){
//		// prepare
//		naverArticleList = new NaverArticleList();
//		naverArticleList.setArticles(naverArticles);
//		
//		Date now = ElixirUtils.getNow();
//		String datehour = ElixirUtils.format("yyyyMMddHHmm", now);
//		datehour = datehour.substring(0, 11);
//		
//		// mock
//		when(naverAaoMock.getArticleListPer10Min(datehour)).thenReturn(naverArticleList);
//		when(articleServiceMock.addArticles(NaverArticle.asArticles(naverArticles))).thenReturn(3);
//		
//		// exec
//		int addCount = naverService.saveArticles();
//		assertThat(addCount, is(3));
//	}
//
//	@Test
//	public void saveArticles() {
//		// articles가 존재시
//		
//		int savedCount = refineryService.saveArticles();
//
//	}
	
	@Test
	public void not_saveArticles() {
		// articles가 미존재시
		
//		refineryService.saveArticles();	
		
	}
	
	private void prepareArticles() {
		// TODO Auto-generated method stub
		
	}


}
