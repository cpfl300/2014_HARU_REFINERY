package refinery.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import refinery.config.Config;
import refinery.model.Article;
import refinery.model.Hotissue;
import refinery.model.Journal;
import refinery.model.Section;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Config.class, loader=AnnotationConfigContextLoader.class)
@Transactional
public class ArticleDaoTest {
	
	@Autowired
	private ArticleDao articleDao;
	
	@Autowired
	private HotissueDao hotissueDao;
	
	private Article article1;
	private Article article2;
	private Article article3;
	
	@Before
	public void setup() {
		// fixture
		Journal journal1 = new Journal(84);
		Section section1 = new Section(3);
		Hotissue hotissue1 = new Hotissue(10);
		article1 = new Article(hotissue1, journal1, section1, "title1", "1111-01-01 01:11:11", "content1", 10000, 7000, 10.1);
		article1.setId("id1");
		
		
		Journal journal2 = new Journal(10);
		Section section2 = new Section(10);
		Hotissue hotissue2 = new Hotissue(15);
		article2 = new Article(hotissue2, journal2, section2, "title2", "1222-02-02 02:11:11", "content2", 20000, 8000, 20.1);
		article2.setId("id2");
		
		
		Journal journal3 = new Journal(23);
		Section section3 = new Section(23);
		Hotissue hotissue3 = new Hotissue(41);
		article3 = new Article(hotissue3, journal3, section3, "title3", "1333-03-03 03:11:11", "content3", 30000, 9000, 10.1);
		article3.setId("id3");
	}
	

	@Test
	public void getCount() {
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));
	}
	
	@Test
	public void deleteAll() {
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));
		
		assertThat(articleDao.deleteAll(), is(0));
		assertThat(articleDao.getCount(), is(0));
		
		articleDao.add(article1);
		assertThat(articleDao.deleteAll(), is(1));
		assertThat(articleDao.getCount(), is(0));
		
		articleDao.add(article1);
		articleDao.add(article2);
		assertThat(articleDao.deleteAll(), is(2));
		assertThat(articleDao.getCount(), is(0));
		
		articleDao.add(article1);
		articleDao.add(article2);
		articleDao.add(article3);
		assertThat(articleDao.deleteAll(), is(3));
		assertThat(articleDao.getCount(), is(0));
	
	}
	
	@Test
	public void add() {
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));
		
		assertThat(articleDao.add(article1), is(1));
		assertThat(articleDao.getCount(), is(1));
		
		assertThat(articleDao.add(article2), is(1));
		assertThat(articleDao.getCount(), is(2));
		
		assertThat(articleDao.add(article3), is(1));
		assertThat(articleDao.getCount(), is(3));
	}
	
	@Test(expected=DuplicateKeyException.class)
	public void notAdd() {
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));
		
		articleDao.add(article1);
		articleDao.add(article1);
		assertThat(articleDao.getCount(), is(2));
	}
	
	@Test
	public void addAndGet() {
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));
		
		articleDao.add(this.article1);
		Article actualArticle1 = articleDao.get(this.article1.getId());
		assertSameArticle(actualArticle1, this.article1);
		
		articleDao.add(this.article2);
		Article actualArticle2 = articleDao.get(this.article2.getId());
		assertSameArticle(actualArticle2, this.article2);
		
		articleDao.add(this.article3);
		Article actualArticle3 = articleDao.get(this.article3.getId());
		assertSameArticle(actualArticle3, this.article3);
	}
	
	@Test
	public void delete() {
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));
		
		articleDao.add(article1);
		articleDao.add(article2);
		articleDao.add(article3);
		assertThat(articleDao.getCount(), is(3));
		
		assertThat(articleDao.delete(article1.getId()), is(1));
		assertThat(articleDao.getCount(), is(2));
		
		assertThat(articleDao.delete(article2.getId()), is(1));
		assertThat(articleDao.getCount(), is(1));
		
		assertThat(articleDao.delete(article3.getId()), is(1));
		assertThat(articleDao.getCount(), is(0));
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