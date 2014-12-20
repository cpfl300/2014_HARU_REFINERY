package refinery.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import refinery.model.NaverArticle;
import refinery.model.NaverHotissue;
import refinery.model.NaverHotissueTest;
import refinery.model.NaverHotissues;
import refinery.model.NaverSection;
import refinery.model.NaverSectionTest;
import refinery.model.NaverSections;
import elixir.model.Article;
import elixir.model.Hotissue;
import elixir.model.Section;
import elixir.service.HotissueService;

@RunWith(MockitoJUnitRunner.class)
public class RefineryServiceTest {
	
	@InjectMocks
	private RefineryService refineryService;
	
	@Mock
	private NaverService naverServiceMock;
	
	@Mock
	private HotissueService hotissueServiceMock;
	
	private List<NaverHotissue> naverHotissues;
	private List<NaverArticle> naverArticles;
	private List<NaverSection> naverSections;
	
	private Section section;
	private String datehour;

	private List<Article> articles;
	private List<Hotissue> hotissues;
	private List<List<NaverSection>> naverSectionsList;
	private List<Section> sections;
	
	@Before
	public void setup() {
		naverSections = NaverSectionTest.preparedList();	
		sections = NaverSections.convert(naverSections);
		
		naverHotissues = NaverHotissueTest.preparedList();
		hotissues = NaverHotissues.convert(naverHotissues);
	}
	

	@Test
	public void saveHotissueList(){		
		// mock
		when(naverServiceMock.getHotissueList()).thenReturn(hotissues);
		
		// exec
		refineryService.saveHotissueList();
		
		// vierfy
		verify(hotissueServiceMock, times(1)).addAll(hotissues);
	}

//	private List<Article> prepareArticlesForSaveArticles() {
//		List<Office> offices = OfficeTest.preparedList();
//		List<Section> sections = SectionTest.preparedList(new String[]{"sectionId", "sectionName"});
//		
//		articles = ArticleTest.preparedList(offices, sections,
//				new String[]{"officeId", "officeName", "articleId", "title",
//				"sections", "contributionDate", "contributionTime", "imageUrl"});
//	}

//
//	@Test
//	public void saveArticles() {
//		// articles가 존재시
//		
//		int savedCount = refineryService.saveArticles();
//
//	}
	
	@Test
	public void not_saveArticles() {
		// articles가 미존재시
		
//		refineryService.saveArticles();	
		
	}
	
	private void prepareArticles() {
		// TODO Auto-generated method stub
		
	}


}
