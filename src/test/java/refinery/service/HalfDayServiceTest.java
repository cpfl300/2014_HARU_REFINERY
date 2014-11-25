package refinery.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import refinery.model.Article;
import refinery.model.Hotissue;

@RunWith(MockitoJUnitRunner.class)
public class HalfDayServiceTest {
	
	private static final int N = 100;
	
	@InjectMocks
	private HalfDayService halfDayService;
	
	@Mock
	private ArticleService articleService;
	

	@Test
	public void save() {
		
		List<Article> actualArticles = halfDayService.save(N);
		assertThat(actualArticles.size(), is(N));
		
		List<Hotissue> hotissues = Hotissue.orderByHotissue(actualArticles);
		assertThat(hotissues.size(), is(N));
	}

}
