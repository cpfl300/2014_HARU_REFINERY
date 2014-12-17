package refinery.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import elixir.model.Article;
import elixir.model.ArticleTest;
import elixir.model.OfficeTest;
import elixir.model.Section;
import elixir.test.ElixirTestUtils;

public class NaverArticleTest {

	private List<NaverArticle> naverArticles;
	private List<NaverSection> naverSections;
	private List<Article> articles;
	private Section section;
	
	@Before
	public void setup() {
		naverSections = NaverSectionTest.preparedList();
		List<List<NaverSection>> naverSectionsList = new ArrayList<List<NaverSection>>();
		naverSectionsList.add(naverSections);
		naverSectionsList.add(naverSections);
		naverSectionsList.add(naverSections);
		
		section = NaverSection.convert(naverSections);
		
		naverArticles = NaverArticleTest.preparedList(naverSectionsList);
		articles = ArticleTest.preparedList(
				OfficeTest.preparedList(),
				Arrays.asList(new Section[]{section, section, section}));
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
	
	

	// creator
	// "001", "officeName1", "111", "title1", "orgUrl1", naverSections, "20140124", "113202", "imageUrl1"
	public static NaverArticle create(String officeId, String officeName, String articleId, String title,
			String content, String orgUrl, List<NaverSection> naverSections,
			String serviceDate, String serviceTime, String imageUrl) {
		NaverArticle naverArticle = new NaverArticle();
		
		naverArticle.setOfficeId(officeId);
		naverArticle.setOfficeName(officeName);
		naverArticle.setArticleId(articleId);
		naverArticle.setTitle(title);
		naverArticle.setContent(content);
		naverArticle.setOrgUrl(orgUrl);
		naverArticle.setSections(naverSections);
		naverArticle.setServiceDate(serviceDate);
		naverArticle.setServiceTime(serviceTime);
		naverArticle.setImageUrl(imageUrl);
		
		return naverArticle;
	}
	
	// ("001", "officeName1", "111", "title1", "20140124", "113202")
	public static NaverArticle CREATE(String officeId, String officeName, String articleId,
			String title, String serviceDate, String serviceTime) {
		NaverArticle naverArticle = new NaverArticle();
		
		naverArticle.setOfficeId(officeId);
		naverArticle.setOfficeName(officeName);
		naverArticle.setArticleId(articleId);
		naverArticle.setTitle(title);
		naverArticle.setServiceDate(serviceDate);
		naverArticle.setServiceTime(serviceTime);
		
		return naverArticle;
	}
	
	public static List<NaverArticle> preparedList(List<List<NaverSection>> naverSectionsList) {
				
		return Arrays.asList(new NaverArticle[] {
				NaverArticleTest.create("101", "경향신문", "111", "title1", "content1", "orgUrl1", naverSectionsList.get(0), "20140101", "010101", "imageUrl1"),
				NaverArticleTest.create("209", "한국경제", "222", "title2", "content2", "orgUrl2", naverSectionsList.get(1), "20140102", "010102", "imageUrl2"),
				NaverArticleTest.create("711", "인벤", "333", "title3", "content3", "orgUrl3", naverSectionsList.get(2), "20140103", "010103", "imageUrl3")
		});
	}

	
	public static List<NaverArticle> preparedList(List<List<NaverSection>> naverSectionsList, String[] fields){
		List<NaverArticle> naverArticles = NaverArticleTest.preparedList(naverSectionsList);
		
		ElixirTestUtils.initComplementaryFields(naverArticles, fields);
		
		return naverArticles;
	}

}
