package core.naver.api;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import refinery.config.Config;
import refinery.model.Article;
import refinery.model.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Config.class, loader=AnnotationConfigContextLoader.class)
public class NaverAPITest {
	
	@Autowired
	private NaverAPI naverApi;
	
	private List<Article> articles;
	
	@Before
	public void setup() {
		// fixtures
		articles = new ArrayList<Article>();
		
		for (int i=0; i<100; i++) {
			Article article = new Article();
			article.setHotissue("hotissue" + i);
			article.setSubject("subject" + i);
			article.setJournal("journal" + i);
			article.setSection("section" + i);
			article.setDate("date" + i);
			article.setContent("content" + i);
			
			articles.add(article);
		}
		
	}
	
	@Test
	public void getArticles() {
		Response actual = naverApi.get("/article", Response.class);
		
		assertThat(actual.getArticles().size(), is(this.articles.size()));
		assertResponseArticles(actual.getArticles(), this.articles);
	}

	private void assertResponseArticles(List<Article> actual, List<Article> expected) {
		for (int i=0; i < actual.size(); i++) {
			Article actualArticle = actual.get(i);
			Article expectedArticle = expected.get(i);
			
			assertThat(actualArticle.getHotissue(), is(expectedArticle.getHotissue()));
			assertThat(actualArticle.getSubject(), is(expectedArticle.getSubject()));
			assertThat(actualArticle.getJournal(), is(expectedArticle.getJournal()));
			assertThat(actualArticle.getSection(), is(expectedArticle.getSection()));
			assertThat(actualArticle.getContent(), is(expectedArticle.getContent()));
		}
		
	}

	

}