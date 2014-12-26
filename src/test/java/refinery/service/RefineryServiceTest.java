package refinery.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
import elixir.model.ArticlesTest;
import elixir.model.Hotissue;
import elixir.model.OfficesTest;
import elixir.model.Section;
import elixir.model.Signature;
import elixir.model.SignaturesTest;
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
	private List<Signature> signatures;
	
	@Before
	public void setup() {
		datehour = ElixirUtils.format("yyyyMMddHHmm", ElixirUtils.now()).substring(0, 11);
		
		naverSections = NaverSectionTest.preparedList();	
		sections = NaverSections.convert(naverSections);
		
		naverHotissues = NaverHotissueTest.preparedList();
		hotissues = NaverHotissues.convert(naverHotissues);
		
		signatures = SignaturesTest.preparedList();
		
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
	
	
//	@Test
//	public void saveArticleList() {
//		// mock
//		when(naverServiceMock.getArticleList(datehour)).thenReturn(articles);
//		
//		// exec
//		refineryService.saveArticleList();
//		
//		// verify
//		verify(articleServiceMock, times(1)).addAll(any());
//		
//	}
//	
//	
//	@Test
//	public void async_updateContentOfArticleList() throws InterruptedException, ExecutionException {
//		
//		
//		assertThat(refineryService.updateContentOfArticleList(signatures).get(), is(-2));
//	}
//	
//	@Test
//	public void async_updateContentOfArticle() throws InterruptedException, ExecutionException {
//		
//		assertThat(refineryService.updateContentOfArticle(signatures.get(0)).get(), is(-2));
//	}
//	
//	@Test
//	public void updateConentOfArticle() {
//		
//		for (int i=0; i<signatures.size(); i++) {
//			Signature signature = signatures.get(i);
//			
//			when(naverServiceMock.getArticle(signature)).thenReturn(articles.get(0));
//						
//			refineryService.updateContentOfArticle(signature);
//			
//		}
//		verify(articleServiceMock, times(3)).updateContent(any());
//		
//	}

	
	
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
		articles = ArticlesTest.preparedList(OfficesTest.preparedList(), ss);
	}
	

}
