package refinery.model;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import refinery.utils.TestUtils;
import elixir.model.Article;
import elixir.model.ArticleTest;
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
	
	
	// prepare
	private void prepareNaverSections() {
		naverSections = NaverSectionTest.PREPARED_LIST();
		section = NaverSection.convert(naverSections);
	}
	
	private void prepareArticles() {
		articles = ArticleTest.PREPARED_LIST(section, section, section); 		
	}

	private void prepareNaverArticles() {
		naverArticles = NaverArticleTest.PREPARED_LIST(naverSections, naverSections, naverSections);
	}

	
	// creator
	// "001", "officeName1", "111", "title1", "orgUrl1", naverSections, "20140124", "113202", "imageUrl1"
	public static NaverArticle CREATE(String officeId, String officeName, String articleId, String title, String orgUrl,
			List<NaverSection> naverSections, String serviceDate, String serviceTime, String imageUrl) {
		NaverArticle naverArticle = new NaverArticle();
		
		naverArticle.setOfficeId(officeId);
		naverArticle.setOfficeName(officeName);
		naverArticle.setArticleId(articleId);
		naverArticle.setTitle(title);
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
	
	@SafeVarargs
	public static List<NaverArticle> PREPARED_LIST(List<NaverSection> firstNaverSections, List<NaverSection>... remainNaverSectionsArr) {
		
		List<NaverSection> secondSections = null;
		List<NaverSection> thirdSections = null;
		if (remainNaverSectionsArr.length >= 1) {
			secondSections = remainNaverSectionsArr[0];
		}
		if (remainNaverSectionsArr.length >= 2) {
			thirdSections = remainNaverSectionsArr[1];
		}
		
		return Arrays.asList(new NaverArticle[] {
				NaverArticleTest.CREATE("001", "officeName1", "111", "title1", "orgUrl1", firstNaverSections, "20140124", "113202", "imageUrl1"),
				NaverArticleTest.CREATE("002", "officeName2", "222", "title2", "orgUrl2", secondSections, "20140124", "123202", "imageUrl2"),
				NaverArticleTest.CREATE("003", "officeName3", "333", "title3", "orgUrl3", thirdSections, "20140124", "133202", "imageUrl3")
		});
	}

	
	public static List<NaverArticle> PREPARED_LIST(String[] fields,  List<NaverSection> firstNaverSection, List<NaverSection>... remainNaverSectionsArr){
		List<NaverArticle> naverArticles = NaverArticleTest.PREPARED_LIST(firstNaverSection, remainNaverSectionsArr);
		
		TestUtils.initComplementaryFields(naverArticles, fields);
		
		return naverArticles;
	}

}
