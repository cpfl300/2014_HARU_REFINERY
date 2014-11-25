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

import refinery.dao.HalfDayDao;
import refinery.model.Article;

@RunWith(MockitoJUnitRunner.class)
public class HalfDayServiceTest {
	
	@InjectMocks
	private HalfDayService halfDayService;
	
	@Mock
	private HalfDayDao halfDayDaoMock;
	
	private List<Article> articles;

	private Article article1;
	private Article article2;
	private Article article3;
	
	@Before
	public void setup() {
		
		article1 = new Article(1, "1111-01-01 01:11:11", 1);
		article2 = new Article(2, "1222-02-02 02:11:11", 2);
		article3 = new Article(3, "1333-03-03 03:11:11", 3);
		
		articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
	}


	@Test
	public void addArticles() {
		
		when(halfDayDaoMock.addArticles(articles)).thenReturn(new int[]{1,1,1});
		
		int actualCount = halfDayService.addArticles(articles);
		
		assertThat(actualCount, is(articles.size()));
		
	}

}
