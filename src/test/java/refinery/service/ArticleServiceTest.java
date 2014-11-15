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
		Hotissue hotissue1 = new Hotissue("북한 돋보기");
		article1 = new Article(hotissue1, journal1, section1, "title1", "1111-01-01 01:11:11", "content1", 10000, 7000, 10.1);
		article1.setId("id1");
		
		
		Journal journal2 = new Journal("한국일보");
		Section section2 = new Section("금융");
		Hotissue hotissue2 = new Hotissue("최경환 경제팀 경기부양책");
		article2 = new Article(hotissue2, journal2, section2, "title2", "1222-02-02 02:11:11", "content2", 20000, 8000, 20.1);
		article2.setId("id2");
		
		
		Journal journal3 = new Journal("전자신문");
		Section section3 = new Section("언론");
		Hotissue hotissue3 = new Hotissue("생각을 키우는 NIE");
		article3 = new Article(hotissue3, journal3, section3, "title3", "1333-03-03 03:11:11", "content3", 30000, 9000, 10.1);
		article3.setId("id3");
	}
	
	@Test
	public void addArticleExistedHotissue() {
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
	public void addArticleNonExistedHotissue() {
		int initialHotissueCount = hotissueDao.getCount();
		
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));	
		
		
		article1.setHotissue(new Hotissue("new hotissue1"));
		articleService.add(article1);
		assertThat(articleDao.getCount(), is(1));
		assertThat(hotissueDao.getCount() - initialHotissueCount, is(1));
		
		article2.setHotissue(new Hotissue("new hotissue2"));
		articleService.add(article2);
		assertThat(articleDao.getCount(), is(2));
		assertThat(hotissueDao.getCount() - initialHotissueCount, is(2));
		
		article3.setHotissue(new Hotissue("new hotissue3"));
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
}
