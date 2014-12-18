package refinery.service;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import elixir.model.Article;
import elixir.model.ArticleTest;
import elixir.model.Office;
import elixir.model.OfficeTest;
import elixir.model.Section;
import elixir.model.SectionTest;
import elixir.utility.ElixirUtils;

@RunWith(MockitoJUnitRunner.class)
public class RefineryServiceTest {
	
	@InjectMocks
	private RefineryService refineryService;
	
	@Mock
	private NaverService naverServiceMock;
	
	@Mock
	private ArticleService articleServiceMock;
	
	private List<Article> articles;
	
	@Before
	public void setup() {
		
	}

	@Test
	public void saveArticles(){		
		String datehour = ElixirUtils.format("yyyyMMddHHmm", ElixirUtils.getNow()).substring(0, 11);
		articles = prepareArticlesForSaveArticles();
		
		// mock
		when(naverServiceMock.getUpdatedArticles(datehour)).thenReturn(articles);
		when(articleServiceMock.addAll()).thenReturn();
		
		// exec
		refineryService.saveArticles();
		
		// assert
	}

	private List<Article> prepareArticlesForSaveArticles() {
		List<Office> offices = OfficeTest.preparedList();
		List<Section> sections = SectionTest.preparedList(new String[]{"sectionId", "sectionName"});
		
		articles = ArticleTest.preparedList(offices, sections,
				new String[]{"officeId", "officeName", "articleId", "title",
				"sections", "contributionDate", "contributionTime", "imageUrl"});
	}

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
