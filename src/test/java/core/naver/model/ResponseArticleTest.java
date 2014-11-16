package core.naver.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import refinery.model.Article;

public class ResponseArticleTest {
	
	private ResponseArticle responseArticle1;
	private ResponseArticle responseArticle2;
	private ResponseArticle responseArticle3;
	
	@Before
	public void setup() {		
		responseArticle1 = new ResponseArticle("hotissue1", "title1", "journal1", "section1", "1111-01-01 01:01:01", "content1", 10000, 1000);
		responseArticle2 = new ResponseArticle("hotissue2", "title2", "journal2", "section2", "1222-02-02 02:02:01", "content2", 20000, 2000);
		responseArticle3 = new ResponseArticle("hotissue3", "title3", "journal3", "section3", "1333-03-03 03:03:01", "content3", 30000, 3000);
	}
	
	@Test
	public void toArticle() {
		Article convertedArticle1 = responseArticle1.toArticle();
		assertSameData(convertedArticle1, responseArticle1);
		
		Article convertedArticle2 = responseArticle2.toArticle();
		assertSameData(convertedArticle2, responseArticle2);
		
		Article convertedArticle3 = responseArticle3.toArticle();
		assertSameData(convertedArticle3, responseArticle3);
	}

	private void assertSameData(Article article, ResponseArticle responseArticle) {
		assertThat(article.getHotissue().getName(), is(responseArticle.getHotissue()));
		assertThat(article.getTitle(), is(responseArticle.getTitle()));
		assertThat(article.getJournal().getName(), is(responseArticle.getJournal()));
		assertThat(article.getSection().getMinor(), is(responseArticle.getSection()));
		assertThat(article.getDate(), is(responseArticle.getDate()));
		assertThat(article.getContent(), is(responseArticle.getContent()));
		assertThat(article.getHits(), is(responseArticle.getHits()));
		assertThat(article.getCompletedReadingCount(), is(responseArticle.getCompletedReadingCount()));		
	}

}
