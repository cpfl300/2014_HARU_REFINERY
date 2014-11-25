package scheduler.task;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import refinery.model.Article;
import refinery.model.Hotissue;
import refinery.model.Journal;
import refinery.model.Section;
import refinery.service.ArticleService;
import refinery.service.HalfDayService;
import refinery.service.HotissueService;
import refinery.utility.RefineryUtils;

@RunWith(MockitoJUnitRunner.class)
public class HalfDayTaskTest {
	
	private static final int N = 3;
	
	@InjectMocks
	private HalfDayTask halfDayTask;
	
	@Mock
	private HalfDayService halfDayServiceMock;
	
	@Mock
	private ArticleService articleServiceMock;
	
	@Mock
	private HotissueService hotissueServiceMock;
	
	
	private List<Hotissue> hotissues;
	private List<Article> articles;
	
	private Hotissue hotissue1;
	private Hotissue hotissue2;
	private Hotissue hotissue3;
	
	private Article article1;
	private Article article2;
	private Article article3;
	
	private Journal journal1;
	private Journal journal2;
	private Journal journal3;
	
	private Section section1;
	private Section section2;
	private Section section3;
	
	@Before
	public void setup() {
		makeJournalFixtures();
		makeSectionFixtures();
		
		hotissue1 = new Hotissue(1, "hotissue1", "1111-01-01 01:11:11");
		hotissue2 = new Hotissue(2, "hotissue2", "1222-02-02 02:11:11");
		hotissue3 = new Hotissue(3, "hotissue3", "1333-03-03 03:11:11");
		
		hotissues = new ArrayList<Hotissue>();
		hotissues.add(hotissue1);
		hotissues.add(hotissue2);
		hotissues.add(hotissue3);
		
		article1 = new Article(1, hotissue1, journal1, section1, "title1", "1111-01-01 01:11:11", "content1", 10000, 7000);
		article2 = new Article(2, hotissue2, journal2, section2, "title2", "1222-02-02 02:11:11", "content2", 20000, 8000);
		article3 = new Article(3, hotissue3, journal3, section3, "title3", "1333-03-03 03:11:11", "content3", 30000, 9000);
		articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
	}

	@Test
	public void extract() {
		Date today = RefineryUtils.getToday();
		String timestamp = RefineryUtils.formatDate(today);
		String[] dates = RefineryUtils.getServiceDatesByTime(2014, Calendar.DECEMBER , 7, 6);
		hotissue1.addArticle(article1);
		hotissue2.addArticle(article2);
		hotissue3.addArticle(article3);
		
		List<Article> extectedArticles = new ArrayList<Article>();
		extectedArticles.add(new Article(1, timestamp, 1));
		extectedArticles.add(new Article(2, timestamp, 2));
		extectedArticles.add(new Article(3, timestamp, 3));
		
		when(articleServiceMock.calcScore(dates[0], dates[1])).thenReturn(N);
		when(hotissueServiceMock.calcScore(dates[0], dates[1])).thenReturn(N);
		when(hotissueServiceMock.getWithArticlesByOrderedScore(N)).thenReturn(hotissues);
		when(halfDayServiceMock.addArticles(extectedArticles)).thenReturn(N);
		
		int actualCount =  halfDayTask.extract(timestamp, dates, N);
		assertThat(actualCount, is(N));
		
		for (int i=0; i<3; i++) {
			Article actual = extectedArticles.get(i);
			assertThat(actual.getSequence(), is(i+1));
		}
	}
	
	private void makeSectionFixtures() {
		section1 = new Section(3);
		section2 = new Section(10);
		section3 = new Section(23);		
	}

	private void makeJournalFixtures() {
		journal1 = new Journal(84);
		journal2 = new Journal(10);
		journal3 = new Journal(23);		
	}

}
