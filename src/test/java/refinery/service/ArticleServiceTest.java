package refinery.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import refinery.dao.ArticleDao;
import refinery.dao.JournalDao;
import refinery.dao.SectionDao;
import refinery.model.Article;
import refinery.model.Hotissue;
import refinery.model.Journal;
import refinery.model.Section;

@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceTest {
	
	private static final Logger log = LoggerFactory.getLogger(ArticleServiceTest.class);
	
	private static final String BEFORE_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
	private static final String AFTER_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private SimpleDateFormat beforeFormat;
	private SimpleDateFormat afterFormat;
	
	@InjectMocks
	private ArticleService articleService;
	
	@Mock
	private ArticleDao articleDaoMock;
	
	@Mock
	private JournalDao journalDaoMock;
	
	@Mock
	private SectionDao sectionDaoMock;
	
	@Mock
	private HotissueService hotissueServiceMock;
	
	
	private Article article1;
	private Journal journal1;
	private Section section1;
	private Hotissue hotissue1;
	
	private Article article2;
	private Journal journal2;
	private Section section2;
	private Hotissue hotissue2;
	
	private Article article3;
	private Journal journal3;
	private Section section3;
	private Hotissue hotissue3;
	
	private List<Article> articles;

	@Before
	public void setup() {
		makeJournalDaoMocks();
		makeSectionDaoMocks();
		
		article1 = new Article(hotissue1, journal1, section1, "title1", "1111-01-01 01:11:11", "content1", 10000, 7000, 10.1);		
		article2 = new Article(hotissue2, journal2, section2, "title2", "1222-02-02 02:11:11", "content2", 20000, 8000, 20.1);
		article3 = new Article(hotissue3, journal3, section3, "title3", "1333-03-03 03:11:11", "content3", 30000, 9000, 10.1);
		
		TimeZone zone = TimeZone.getTimeZone("Asia/Seoul");
		beforeFormat = new SimpleDateFormat(BEFORE_DATE_FORMAT);
		
		beforeFormat.setTimeZone(zone);
		afterFormat = new SimpleDateFormat(AFTER_DATE_FORMAT);
		afterFormat.setTimeZone(zone);
	}

	@Test
	public void has() {
		when(articleDaoMock.get(article1.hashCode())).thenReturn(article1);
		assertThat(articleService.has(article1.hashCode()), is(true));
		
		when(articleDaoMock.get(article2.hashCode())).thenReturn(article2);
		assertThat(articleService.has(article2.hashCode()), is(true));
		
		when(articleDaoMock.get(article3.hashCode())).thenReturn(article3);
		assertThat(articleService.has(article3.hashCode()), is(true));
	}
	
	@Test
	public void hasNot() {
		when(articleDaoMock.get(article1.hashCode())).thenThrow(EmptyResultDataAccessException.class);
		assertThat(articleService.has(article1.hashCode()), is(false));
		
		when(articleDaoMock.get(article2.hashCode())).thenThrow(EmptyResultDataAccessException.class);
		assertThat(articleService.has(article2.hashCode()), is(false));
		
		when(articleDaoMock.get(article3.hashCode())).thenThrow(EmptyResultDataAccessException.class);
		assertThat(articleService.has(article3.hashCode()), is(false));
	}
	
	
	@Test
	public void add() {
		int expectedArticle1Id = article1.hashCode();
		assertThat(articleService.add(article1), is(expectedArticle1Id));
		
		int expectedArticle2Id = article2.hashCode();
		assertThat(articleService.add(article2), is(expectedArticle2Id));
		
		int expectedArticle3Id = article3.hashCode();
		assertThat(articleService.add(article3), is(expectedArticle3Id));
	}
	
	@Test
	public void notAdd() {
		doThrow(DuplicateKeyException.class).when(articleDaoMock).add(any(Article.class));
		
		int expectedArticle1Id = article1.hashCode();
		assertThat(articleService.add(article1), is(expectedArticle1Id));
		
		int expectedArticle2Id = article2.hashCode();
		assertThat(articleService.add(article2), is(expectedArticle2Id));
		
		int expectedArticle3Id = article3.hashCode();
		assertThat(articleService.add(article3), is(expectedArticle3Id));
	}
	
	
	@Test
	public void delete() {
		makeHotissueServiceMocks();
		article1.setHotissue(hotissue1);
		article2.setHotissue(hotissue2);
		article3.setHotissue(hotissue3);
		
		when(articleDaoMock.get(article1.hashCode())).thenReturn(article1);		
		when(articleDaoMock.get(article2.hashCode())).thenReturn(article2);
		when(articleDaoMock.get(article3.hashCode())).thenReturn(article3);
		
		when(articleDaoMock.delete(article1.hashCode())).thenReturn(1);
		when(articleDaoMock.delete(article2.hashCode())).thenReturn(1);
		when(articleDaoMock.delete(article3.hashCode())).thenReturn(1);

		when(hotissueServiceMock.delete(hotissue1.getId())).thenReturn(1);
		when(hotissueServiceMock.delete(hotissue2.getId())).thenReturn(1);
		when(hotissueServiceMock.delete(hotissue3.getId())).thenReturn(1);
		
		assertThat(articleService.delete(article1.hashCode()), is(1));
		assertThat(articleService.delete(article1.hashCode()), is(1));
		assertThat(articleService.delete(article1.hashCode()), is(1));
	}
	
	@Test
	public void addArticles() {
		articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
		
		when(articleDaoMock.addArticles(articles)).thenReturn(new int[] {1, 1, 1});
		
		int actualCount = articleService.addArticles(articles);
		
		assertThat(actualCount, is(3));
	}
	
	@Test
	public void addArticlesIncludedDuplicateKey() {
		articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article1);
		articles.add(article1);
		
		when(articleDaoMock.addArticles(articles)).thenReturn(new int[] {1, 0, 0});
		
		int actualCount = articleService.addArticles(articles);
		
		assertThat(actualCount, is(1));
	}
	
	@Test
	public void calcScore() {
		TimeZone zone = TimeZone.getTimeZone("Asia/Seoul");
		Calendar specificCalendar = Calendar.getInstance(zone);
		
		specificCalendar.set(2014, Calendar.DECEMBER, 7, 6, 0, 0);
		String to  = beforeFormat.format(specificCalendar.getTime());
		
		specificCalendar.add(Calendar.HOUR_OF_DAY, -12);
		String from = beforeFormat.format(specificCalendar.getTime());
		
		
		articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
		
		when(articleDaoMock.getArticlesBetweenDates(from, to)).thenReturn(articles);
		when(articleDaoMock.updateScore(articles)).thenReturn(new int[] {1, 1, 1});
		assertThat(articleService.calcScore(from, to), is(3));
	}
	
	@Test
	public void getArticlesOfHalfDayByCalendarTo() {
		TimeZone zone = TimeZone.getTimeZone("Asia/Seoul");
		Calendar morningServiceCalendar = Calendar.getInstance(zone);
		morningServiceCalendar.set(2014, Calendar.DECEMBER , 7, 6, 0, 0); 
		
		Calendar afternoonServiceCalendar = Calendar.getInstance(zone);
		afternoonServiceCalendar.set(2014, Calendar.DECEMBER , 7, 18, 0, 0);
		
		// fixtures
		List<Article> articles = new ArrayList<Article>();
		articles.add(new Article(1, hotissue1, journal1, section1, "title1", "2014-12-06 17:59:59", "content1", 10000, 11000));
		articles.add(new Article(2, hotissue2, journal2, section2, "title2", "2014-12-06 18:00:00", "content2", 20000, 12000));
		articles.add(new Article(3, hotissue3, journal3, section3, "title3", "2014-12-07 05:59:59", "content3", 30000, 13000));
		articles.add(new Article(4, hotissue1, journal1, section1, "title4", "2014-12-07 06:00:00", "content4", 40000, 14000));
		articles.add(new Article(5, hotissue2, journal2, section2, "title5", "2014-12-07 17:59:59", "content5", 50000, 15000));
		articles.add(new Article(6, hotissue3, journal3, section3, "title6", "2014-12-07 18:00:00", "content6", 60000, 16000));
		
		List<Article> morningArticles = Arrays.asList(new Article[] {articles.get(1), articles.get(2)});		
		List<Article> afternoonArticles = Arrays.asList(new Article[] {articles.get(3), articles.get(4)});


		when(articleDaoMock.getArticlesBetweenDates("2014-12-06 18:00:00", "2014-12-07 06:00:00")).thenReturn(morningArticles);		
		when(articleDaoMock.getArticlesBetweenDates("2014-12-07 06:00:00", "2014-12-07 18:00:00")).thenReturn(afternoonArticles);
		
		
		List<Article> actualMorningArticles = articleService.getArticlesOfHalfDayByCalendarTo(morningServiceCalendar);
		assertThat(actualMorningArticles.size(), is(2));
		assertThat(actualMorningArticles.get(0).getId(), is(2));
		assertThat(actualMorningArticles.get(1).getId(), is(3));
		
		List<Article> actualAfternoonArticles = articleService.getArticlesOfHalfDayByCalendarTo(afternoonServiceCalendar);
		assertThat(actualAfternoonArticles.size(), is(2));
		assertThat(actualAfternoonArticles.get(0).getId(), is(4));
		assertThat(actualAfternoonArticles.get(1).getId(), is(5));
	}
	


	private void makeHotissueServiceMocks() {
		hotissue1 = new Hotissue("hotissue1", "1001-01-01 01:11:11");
		hotissue1.setId(hotissue1.hashCode());
		
		hotissue2 = new Hotissue("hotissue2", "1002-02-02 02:11:11");
		hotissue2.setId(hotissue2.hashCode());
		
		hotissue3 = new Hotissue("hotissue3", "1003-03-03 03:11:11");
		hotissue3.setId(hotissue3.hashCode());
		
		when(hotissueServiceMock.add(hotissue1)).thenReturn(hotissue1.getId());
		when(hotissueServiceMock.add(hotissue2)).thenReturn(hotissue2.getId());
		when(hotissueServiceMock.add(hotissue3)).thenReturn(hotissue3.getId());
	}

	private void makeSectionDaoMocks() {
		section1 = new Section(1, "section_major1","section_minor1"); 
		section2 = new Section(2, "section_major2","section_minor2");
		section3 = new Section(3, "section_major3","section_minor3");
		
		when(sectionDaoMock.getByMinor(section1.getMinor())).thenReturn(section1);
		when(sectionDaoMock.getByMinor(section2.getMinor())).thenReturn(section2);
		when(sectionDaoMock.getByMinor(section3.getMinor())).thenReturn(section3);
	}

	private void makeJournalDaoMocks() {
		journal1 = new Journal(1, "journal1", "journal_section1");
		journal2 = new Journal(2, "journal2", "journal_section2");
		journal3 = new Journal(3, "journal3", "journal_section3");
		
		when(journalDaoMock.getByName(journal1.getName())).thenReturn(journal1);
		when(journalDaoMock.getByName(journal2.getName())).thenReturn(journal2);
		when(journalDaoMock.getByName(journal3.getName())).thenReturn(journal3);
	}
	
	private String[] getDateStrArr(Calendar calendar) {		
		String[] dates = new String[2];
		
		SimpleDateFormat format = new SimpleDateFormat(AFTER_DATE_FORMAT);
		dates[1] = format.format(calendar.getTime());
		
		calendar.add(Calendar.HOUR, -12);
		dates[0] = format.format(calendar.getTime());
		
		log.debug("dates- from[" + dates[0] + "] to[" + dates[1] + "]");
		
		return dates;
	}

	
}
