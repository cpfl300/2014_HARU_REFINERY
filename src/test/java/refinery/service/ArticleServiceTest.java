package refinery.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import refinery.config.Config;
import refinery.dao.ArticleDao;
import refinery.dao.HotissueDao;
import refinery.dao.JournalDao;
import refinery.dao.SectionDao;
import refinery.model.Article;
import refinery.model.Hotissue;
import refinery.model.Journal;
import refinery.model.Section;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Config.class, loader=AnnotationConfigContextLoader.class)
@Transactional
public class ArticleServiceTest {
	
	@Autowired
	private ArticleDao articleDao;
	
	@Autowired
	private JournalDao journalDao;
	
	@Autowired
	private SectionDao sectionDao;
	
	@Autowired
	private HotissueDao hotissueDao;
	
	@Autowired
	private HotissueService hotissueService;
	
	@Autowired
	private ArticleService articleService;
	
	private Article article1;
	private Article article2;
	private Article article3;

	@Before
	public void setup() {
		Journal journal1 = new Journal("서울신문");
		Section section1 = new Section("북한"); 
		Hotissue hotissue1 = new Hotissue("hotissue1");
		article1 = new Article(1, hotissue1, journal1, section1, "title1", "1111-01-01 01:11:11", "content1", 10000, 7000, 10.1);
		
		Journal journal2 = new Journal("한국일보");
		Section section2 = new Section("금융");
		Hotissue hotissue2 = new Hotissue("hotissue2");
		article2 = new Article(2, hotissue2, journal2, section2, "title2", "1222-02-02 02:11:11", "content2", 20000, 8000, 20.1);
		
		Journal journal3 = new Journal("전자신문");
		Section section3 = new Section("언론");
		Hotissue hotissue3 = new Hotissue("hotissue3");
		article3 = new Article(3, hotissue3, journal3, section3, "title3", "1333-03-03 03:11:11", "content3", 30000, 9000, 10.1);
	}
	
	@Test
	public void addAndGet() {
		hotissueService.add(new Hotissue(1, "hotissue1"));
		hotissueService.add(new Hotissue(2, "hotissue2"));
		hotissueService.add(new Hotissue(3, "hotissue3"));
		int initialHotissueCount = hotissueDao.getCount();
		
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));	
		
		int actualArticle1Id = articleService.add(article1);
		assertThat(articleDao.getCount(), is(1));
		assertThat(hotissueDao.getCount() - initialHotissueCount, is(0));
		assertSameArticle(articleDao.get(actualArticle1Id), article1);
		
		int actualArticle2Id = articleService.add(article2);
		assertThat(articleDao.getCount(), is(2));
		assertThat(hotissueDao.getCount() - initialHotissueCount, is(0));
		assertSameArticle(articleDao.get(actualArticle2Id), article2);
		
		int actualArticle3Id = articleService.add(article3);
		assertThat(articleDao.getCount(), is(3));
		assertThat(hotissueDao.getCount() - initialHotissueCount, is(0));
		assertSameArticle(articleDao.get(actualArticle3Id), article3);
	}
	
	@Test
	public void addAndGetWhenExistdArticle() {
		hotissueService.add(new Hotissue(1, "hotissue1"));
		hotissueService.add(new Hotissue(2, "hotissue2"));
		hotissueService.add(new Hotissue(3, "hotissue3"));
		
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));
		
		int articleHashCode = article1.hashCode();
		
		int actualArticle1Id1 = articleService.add(article1);
		assertThat(articleDao.getCount(), is(1));
		assertThat(actualArticle1Id1, is(articleHashCode));
		
		int actualArticle1Id2 = articleService.add(article1);
		assertThat(articleDao.getCount(), is(1));
		assertThat(actualArticle1Id2, is(articleHashCode));
		
		int actualArticle1Id3 = articleService.add(article1);
		assertThat(articleDao.getCount(), is(1));
		assertThat(actualArticle1Id3, is(articleHashCode));
	}
	
	
	@Test
	public void addArticleWhenExistedHotissue() {
		hotissueService.add(new Hotissue(1, "hotissue1"));
		hotissueService.add(new Hotissue(2, "hotissue2"));
		hotissueService.add(new Hotissue(3, "hotissue3"));
		int initialHotissueCount = hotissueDao.getCount();
		
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));	
		
		articleService.add(article1);
		assertThat(articleDao.getCount(), is(1));
		assertThat(hotissueDao.getCount() - initialHotissueCount, is(0));
		
		articleService.add(article2);
		assertThat(articleDao.getCount(), is(2));
		assertThat(hotissueDao.getCount() - initialHotissueCount, is(0));
		
		articleService.add(article3);
		assertThat(articleDao.getCount(), is(3));
		assertThat(hotissueDao.getCount() - initialHotissueCount, is(0));
	}
	
	@Test
	public void addArticleWhenNonExistedHotissue() {
		int initialHotissueCount = hotissueDao.getCount();
		
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));	
				
		articleService.add(article1);
		assertThat(articleDao.getCount(), is(1));
		assertThat(hotissueDao.getCount() - initialHotissueCount, is(1));
		
		articleService.add(article2);
		assertThat(articleDao.getCount(), is(2));
		assertThat(hotissueDao.getCount() - initialHotissueCount, is(2));
		
		articleService.add(article3);
		assertThat(articleDao.getCount(), is(3));
		assertThat(hotissueDao.getCount() - initialHotissueCount, is(3));
	}
	
	
	@Test
	public void deleteArticle() {
		int initialHotissueCount = hotissueDao.getCount();
		
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));
		
		Hotissue newHotissue = new Hotissue("new hotissue");
		article1.setHotissue(newHotissue);
		article2.setHotissue(newHotissue);
		article3.setHotissue(newHotissue);
		articleService.add(article1);
		articleService.add(article2);
		articleService.add(article3);
		assertThat(articleDao.getCount(), is(3));
		assertThat(hotissueDao.getCount() - initialHotissueCount, is(1));
		
		articleService.delete(article1.getId());
		assertThat(articleDao.getCount(), is(2));
		assertThat(hotissueDao.getCount() - initialHotissueCount, is(1));
		
		articleService.delete(article2.getId());
		assertThat(articleDao.getCount(), is(1));
		assertThat(hotissueDao.getCount() - initialHotissueCount, is(1));
		
		articleService.delete(article3.getId());
		assertThat(articleDao.getCount(), is(0));
		assertThat(hotissueDao.getCount() - initialHotissueCount, is(0));
		
	}
	
	private void assertSameArticle(Article actual, Article expected) {
		assertThat(actual.getId(), is(expected.getId()));
		assertThat(actual.getHotissue().getId(), is(expected.getHotissue().getId()));
		assertThat(actual.getJournal().getId(), is(expected.getJournal().getId()));
		assertThat(actual.getTitle(), is(expected.getTitle()));
		assertThat(actual.getSection().getId(), is(expected.getSection().getId()));
		assertThat(actual.getDate(), is(expected.getDate()));
		assertThat(actual.getContent(), is(expected.getContent()));
		assertThat(actual.getHits(), is(expected.getHits()));
		assertThat(actual.getCompletedReadingCount(), is(expected.getCompletedReadingCount()));
	}

	
}
