package scheduler.task;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import refinery.model.Article;
import refinery.model.Hotissue;
import refinery.model.Journal;
import refinery.model.Section;
import refinery.service.ArticleService;
import refinery.service.NaverNewsService;


@RunWith(MockitoJUnitRunner.class)
public class NaverNewsTaskTest {
	
	@InjectMocks
	private NaverNewsTask naverNewsTask;
	
	@Mock
	private NaverNewsService naverNewsService;
	
	@Mock
	private ArticleService articleService;
	
	private List<Article> articles;
	private Article article1;
	private Article article2;
	private Article article3;
	
	@Before
	public void setup() {
		
		article1 = new Article(new Hotissue(1, "hotissue1"), new Journal(1), new Section(1), "title1", "1111-01-01 01:11:11", "content1", 10000, 7000, 10.1);
		article2 = new Article(new Hotissue(2, "hotissue2"), new Journal(2), new Section(2), "title2", "1222-02-02 02:11:11", "content2", 20000, 8000, 20.1);
		article3 = new Article(new Hotissue(3, "hotissue3"), new Journal(3), new Section(3), "title3", "1333-03-03 03:11:11", "content3", 30000, 9000, 10.1);

	}
	
	@Test
	public void getNewsWhenNeedToUpdate() {
		articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
		
		when(naverNewsService.isUpdated()).thenReturn(false);
		when(naverNewsService.getArticles()).thenReturn(articles);
		when(articleService.addArticles(articles)).thenReturn(3);
		
		int actualCount = naverNewsTask.getNews();
		
		assertThat(actualCount, is(3));
		
	}
	
	@Test
	public void getNewsWhenNeedNotToUpdate() {
		articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
		
		when(naverNewsService.isUpdated()).thenReturn(true);
		
		int actualCount = naverNewsTask.getNews();
		
		assertThat(actualCount, is(0));
		
	}
	
	@Test
	public void getNewsIncludedDuplicatedArticle() {
		articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article1);
		articles.add(article1);
		
		when(naverNewsService.isUpdated()).thenReturn(false);
		when(naverNewsService.getArticles()).thenReturn(articles);
		when(articleService.addArticles(articles)).thenReturn(1);
		
		int actualCount = naverNewsTask.getNews();
		
		assertThat(actualCount, is(1));
		
	}

}
