package refinery.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import refinery.model.NaverArticle;
import refinery.model.NaverArticleTest;
import refinery.model.NaverHotissue;
import refinery.model.NaverHotissueTest;
import refinery.model.NaverHotissues;
import refinery.model.NaverSection;
import refinery.model.NaverSectionTest;
import refinery.model.NaverSections;
import refinery.model.NaverSectionsTest;
import elixir.model.Article;
import elixir.model.ArticleTest;
import elixir.model.Hotissue;
import elixir.model.OfficeTest;
import elixir.model.Section;
import elixir.service.ArticleService;
import elixir.service.HotissueService;
import elixir.utility.ElixirUtils;

@RunWith(MockitoJUnitRunner.class)
public class RefineryServiceTest {
	
	@InjectMocks
	private RefineryService refineryService;
	
	@Mock
	private NaverService naverServiceMock;
	
	@Mock
	private HotissueService hotissueServiceMock;
	
	@Mock
	private ArticleService articleServiceMock;
	
	private List<NaverHotissue> naverHotissues;
	private List<NaverArticle> naverArticles;
	private List<NaverSection> naverSections;
	
	private String datehour;

	private List<Article> articles;
	private List<Hotissue> hotissues;
	private List<Section> sections;
	
	@Before
	public void setup() {
		datehour = ElixirUtils.format("yyyyMMddHHmm", ElixirUtils.getNow()).substring(0, 11);
		
		naverSections = NaverSectionTest.preparedList();	
		sections = NaverSections.convert(naverSections);
		
		naverHotissues = NaverHotissueTest.preparedList();
		hotissues = NaverHotissues.convert(naverHotissues);
		
		prepareArticles();
	}
	

	@Test
	public void saveHotissueList() {		
		// mock
		when(naverServiceMock.getHotissueList()).thenReturn(hotissues);
		
		// exec
		refineryService.saveHotissueList();
		
		// vierfy
		verify(hotissueServiceMock, times(1)).addAll(hotissues);
	}
	
	
	@Test
	public void saveArticleList() {
		// mock
		when(naverServiceMock.getArticleList(datehour)).thenReturn(articles);
		
		// exec
		refineryService.saveArticleList();
		
		// verify
		verify(articleServiceMock, times(1)).addAll(articles);
		
	}
	
	private void prepareArticles() {
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
	

}
