package refinery.model;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import elixir.model.Article;
import elixir.model.ArticleTest;
import elixir.model.Journal;
import elixir.model.Section;

public class NaverArticleTest {

	private List<NaverArticle> naverArticles;
	private List<NaverSection> naverSections;
	private List<Article> articles;
	private Section section;
	
	@Before
	public void setup() {
		prepareNaverSections();
		prepareNaverArticles();
		prepareArticles();
	}
	

	@Test
	public void convert() {
		for (int i=0; i<articles.size(); i++) {
			Article actual = naverArticles.get(i).convert();
			
			ArticleTest.ASSERT(actual, articles.get(i));
		}
	}
	
	@Test
	public void convertAsList() {
		List<Article> actuals = NaverArticle.convert(naverArticles);
		ArticleTest.ASSERTS(actuals, articles);
	}
	

	private void prepareNaverSections() {
		naverSections = Arrays.asList(
				new NaverSection[] {
						new NaverSection("111", "sectionName1"),
						new NaverSection("222", "sectionName2"),
						new NaverSection("333", "sectionName3")
				});
		
		section = NaverSection.convert(naverSections);
	}
	
	private void prepareArticles() {
		articles = Arrays.asList(new Article[] {
				new Article("111", new Journal("001", "officeName1"), "title1", "orgUrl1", section, "20140124", "113202", "imageUrl1"),
				new Article("222", new Journal("002", "officeName2"), "title2", "orgUrl2", section, "20140124", "123202", "imageUrl2"),
				new Article("333", new Journal("003", "officeName3"), "title3", "orgUrl3", section, "20140124", "133202", "imageUrl3")
		});
		
	}

	private void prepareNaverArticles() {
		naverArticles = Arrays.asList(new NaverArticle[] {
				new NaverArticle("001", "officeName1", "111", "title1", "orgUrl1", naverSections, "20140124", "113202", "imageUrl1"),
				new NaverArticle("002", "officeName2", "222", "title2", "orgUrl2", naverSections, "20140124", "123202", "imageUrl2"),
				new NaverArticle("003", "officeName3", "333", "title3", "orgUrl3", naverSections, "20140124", "133202", "imageUrl3")
		});
	}
}
