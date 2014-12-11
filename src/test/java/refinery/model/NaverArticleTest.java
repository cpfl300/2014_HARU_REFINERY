package refinery.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import elixir.model.Article;
import elixir.model.Hotissue;
import elixir.model.Journal;
import elixir.model.Section;
import elixir.utility.ElixirUtils;

public class NaverArticleTest {

	private List<NaverSection> naverSections;
	private List<NaverArticle> naverArticles;
	private List<Article> articles;
	
	@Before
	public void setup() {
		prepareNaverSections();
		prepareNaverArticles();
		prepareArticles();
	}
	

	@Test
	public void toArticle() {
		
		Article actualArticle = NaverArticle.toArticle(naverArticles.get(0));
		
		assertSameArticle(actualArticle, articles.get(0));
	}
	
	@Test
	public void asArticles() {
		
		List<Article> actuals = NaverArticle.asArticles(naverArticles);
		
		for (int i=0; i<3; i++) {
			assertSameArticle(actuals.get(i), articles.get(i));
		}
		
	}
	
	private void prepareNaverSections() {
		naverSections = Arrays.asList(
				new NaverSection[] {
						new NaverSection("1", "sectionName1"),
						new NaverSection("2", "sectionName2"),
						new NaverSection("3", "sectionName3")
				}); 
	}
	

	private void prepareArticles() {
		articles = Arrays.asList(new Article[] {
				new Article(1, new Hotissue(1), new Journal(1), new Section(11), "title1", "2014-01-24 11:32:02", "content1", 10000, 1000),
				new Article(2, new Hotissue(2), new Journal(2), new Section(22), "title2", "2014-01-24 12:32:02", "content2", 20000, 2000),
				new Article(3, new Hotissue(3), new Journal(3), new Section(33), "title3", "2014-01-24 13:32:02", "content3", 30000, 3000)
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

	private void assertSameArticle(Article actual, Article expected) {
		assertThat(actual.getId(), is(expected.getId()));
		assertThat(actual.getJournal().getId(), is(expected.getJournal().getId()));
		assertThat(actual.getTitle(), is(expected.getTitle()));
		assertThat(actual.getSection().getId(), is(expected.getSection().getId()));
		assertThat(actual.getDate(), is(expected.getDate()));
		assertThat(actual.getContent(), is(expected.getContent()));
		assertThat(actual.getHits(), is(expected.getHits()));
		assertThat(actual.getCompletedReadingCount(), is(expected.getCompletedReadingCount()));
	}
}
