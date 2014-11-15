package refinery.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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
public class HotissueDaoTest {
	
	@Autowired
	private HotissueDao hotissueDao;
	
	@Autowired
	private ArticleDao articleDao;
	
	private Hotissue hotissue1;
	private Hotissue hotissue2;
	private Hotissue hotissue3;

	
	@Before
	public void setup() {
		hotissue1 = new Hotissue("hotissue1");
		hotissue2 = new Hotissue("hotissue2");
		hotissue3 = new Hotissue("hotissue3");
	}
	
	@Test
	public void deleteAll() {
		hotissueDao.deleteAll();
		
	}
	
	@Test
	public void getCount() {
		hotissueDao.deleteAll();
		assertThat(hotissueDao.getCount(), is(0));
		
	}
	
	@Test
	public void add() {
		hotissueDao.deleteAll();
		assertThat(hotissueDao.getCount(), is(0));
		
		long actualHotissue1Key = hotissueDao.add(hotissue1);
		assertThat(hotissueDao.getCount(), is(1));
		assertThat(actualHotissue1Key, not(is(0)));
		
		long actualHotissue2Key = hotissueDao.add(hotissue2);
		assertThat(hotissueDao.getCount(), is(2));
		assertThat(actualHotissue2Key, not(is(0)));
		
		long actualHotissue3Key = hotissueDao.add(hotissue3);
		assertThat(hotissueDao.getCount(), is(3));
		assertThat(actualHotissue3Key, not(is(0)));
	}
	
	@Test
	public void getWithArticles() {
		Journal journal = new Journal(84);
		Section section = new Section(3);
		Hotissue hotissue = new Hotissue(10);
		Article article1 = new Article(hotissue, journal, section, "title1", "1111-01-01 01:11:11", "content1", 10000, 7000, 10.1);
		article1.setId("id1");
		
		Article article2 = new Article(hotissue, journal, section, "title2", "1222-02-02 02:11:11", "content2", 20000, 8000, 20.1);
		article2.setId("id2");
		
		Article article3 = new Article(hotissue, journal, section, "title3", "1333-03-03 03:11:11", "content3", 30000, 9000, 10.1);
		article3.setId("id3");
		
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));
		
		articleDao.add(article1);
		articleDao.add(article2);
		articleDao.add(article3);
		assertThat(articleDao.getCount(), is(3));
		
		Hotissue actualHotissue = hotissueDao.getWithArticles(hotissue.getId());
		assertThat(actualHotissue.getArticles().size(), is(3));
	}
	
	@Test
	public void addAndGet() {
		hotissueDao.deleteAll();
		assertThat(hotissueDao.getCount(), is(0));
		
		hotissueDao.add(hotissue1);
		int hotissue1Id = hotissueDao.lastInsertId();
		Hotissue actualHotissue1 = hotissueDao.get(hotissue1Id);
		assertSameHotissue(actualHotissue1, hotissue1);
		
		hotissueDao.add(hotissue2);
		int hotissue2Id = hotissueDao.lastInsertId();
		Hotissue actualHotissue2 = hotissueDao.get(hotissue2Id);
		assertSameHotissue(actualHotissue2, hotissue2);
		
		hotissueDao.add(hotissue3);
		int hotissue3Id = hotissueDao.lastInsertId();
		Hotissue actualHotissue3 = hotissueDao.get(hotissue3Id);
		assertSameHotissue(actualHotissue3, hotissue3);
	}
	
	@Test
	public void getByName() {
		hotissueDao.deleteAll();
		assertThat(hotissueDao.getCount(), is(0));
		
		hotissueDao.add(hotissue1);
		hotissueDao.add(hotissue2);
		hotissueDao.add(hotissue3);
		assertThat(hotissueDao.getCount(), is(3));
		
		Hotissue actualHotissue1 = hotissueDao.getByName(hotissue1.getName());
		assertSameHotissue(actualHotissue1, hotissue1);
		
		Hotissue actualHotissue2 = hotissueDao.getByName(hotissue2.getName());
		assertSameHotissue(actualHotissue2, hotissue2);
		
		Hotissue actualHotissue3 = hotissueDao.getByName(hotissue3.getName());
		assertSameHotissue(actualHotissue3, hotissue3);
		
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void notGetByName() {
		hotissueDao.deleteAll();
		assertThat(hotissueDao.getCount(), is(0));
		
		hotissueDao.add(hotissue1);
		hotissueDao.add(hotissue2);
		hotissueDao.add(hotissue3);
		assertThat(hotissueDao.getCount(), is(3));
		
		
		Hotissue fakeHotissue = new Hotissue("face hotissue");
		
		Hotissue actualHotissue = hotissueDao.getByName(fakeHotissue.getName());
		assertSameHotissue(actualHotissue, fakeHotissue);
	}
	
	@Test
	public void delete() {
		hotissueDao.deleteAll();
		assertThat(hotissueDao.getCount(), is(0));
		
		long hotissue1Id = hotissueDao.add(hotissue1);
		long hotissue2Id = hotissueDao.add(hotissue2);
		long hotissue3Id = hotissueDao.add(hotissue3);
		assertThat(hotissueDao.getCount(), is(3));
		
		assertThat(hotissueDao.delete(hotissue1Id), is(1));
		assertThat(hotissueDao.getCount(), is(2));
		
		assertThat(hotissueDao.delete(hotissue2Id), is(1));
		assertThat(hotissueDao.getCount(), is(1));
		
		assertThat(hotissueDao.delete(hotissue3Id), is(1));
		assertThat(hotissueDao.getCount(), is(0));
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void notDelete() {
		hotissueDao.deleteAll();
		assertThat(hotissueDao.getCount(), is(0));
		
		long hotissue1Id = hotissueDao.add(hotissue1);
		
		Journal journal = new Journal(84);
		Section section = new Section(3);
		Hotissue hotissue = new Hotissue(hotissue1Id);
		
		Article article = new Article(hotissue, journal, section, "title1", "1111-01-01 01:11:11", "content1", 10000, 7000, 10.1);
		article.setId("id1");
		articleDao.add(article);
		
		hotissueDao.delete(hotissue1Id);
	}
	

	private void assertSameHotissue(Hotissue actual, Hotissue expected) {
		assertThat(actual.getId(), not(is(0)));
		assertThat(actual.getName(), is(expected.getName()));
	}

}
