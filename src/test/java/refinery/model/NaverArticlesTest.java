package refinery.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import elixir.model.Article;
import elixir.model.ArticleTest;
import elixir.model.ArticlesTest;
import elixir.model.OfficeTest;
import elixir.model.Section;

public class NaverArticlesTest {
	
	private List<NaverArticle> naverArticles;
	private List<Article> articles;
	
	@Before
	public void setup() {
		List<List<NaverSection>> naverSectionsList = new ArrayList<List<NaverSection>>();
		List<NaverSection> ns1 = NaverSectionsTest.preparedList1();
		List<NaverSection> ns2 = NaverSectionsTest.preparedList2();
		List<NaverSection> ns3 = NaverSectionsTest.preparedList3();
		naverSectionsList.add(ns1);
		naverSectionsList.add(ns2);
		naverSectionsList.add(ns3);
		
		List<Section> s1 = NaverSections.convert(ns1);
		List<Section> s2 = NaverSections.convert(ns2);
		List<Section> s3 = NaverSections.convert(ns3);
		List<List<Section>> ss = new ArrayList<List<Section>>();
		ss.add(s1);
		ss.add(s2);
		ss.add(s3);
		
		naverArticles = NaverArticleTest.preparedList(naverSectionsList);
		articles = ArticleTest.preparedList(OfficeTest.preparedList(), ss);
	}
	
	@Test
	public void convert() {
		List<Article> actuals = NaverArticles.convert(naverArticles); 
		
		ArticlesTest.ASSERTS(actuals, articles,
				new String[]{"artilceId", "title", "content", "orgUrl", "sections", "serviceDate", "serviceTime", "imageUrl"});
	}

}
