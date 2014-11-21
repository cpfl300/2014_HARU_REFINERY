package refinery.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

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
		hotissue1 = new Hotissue(1, "hotissue1", "1111-01-01 01:11:11");
		hotissue2 = new Hotissue(2, "hotissue2", "1222-02-02 02:11:11");
		hotissue3 = new Hotissue(3, "hotissue3", "1333-03-03 03:11:11");
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
		
		hotissueDao.add(hotissue1);
		assertThat(hotissueDao.getCount(), is(1));
		
		hotissueDao.add(hotissue2);
		assertThat(hotissueDao.getCount(), is(2));
		
		hotissueDao.add(hotissue3);
		assertThat(hotissueDao.getCount(), is(3));
	}
	
	@Test
	public void getWithArticles() {
		Journal journal = new Journal(84);
		Section section = new Section(3);
		Hotissue hotissue = new Hotissue(1, "hotissue1");
		hotissueDao.add(hotissue);
		
		Article article1 = new Article(1, hotissue, journal, section, "title1", "1111-01-01 01:11:11", "content1", 10000, 7000, 10.1);
		Article article2 = new Article(2, hotissue, journal, section, "title2", "1222-02-02 02:11:11", "content2", 20000, 8000, 20.1);
		Article article3 = new Article(3, hotissue, journal, section, "title3", "1333-03-03 03:11:11", "content3", 30000, 9000, 10.1);
		
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
		Hotissue actualHotissue1 = hotissueDao.get(1);
		assertSameHotissue(actualHotissue1, hotissue1);
		
		hotissueDao.add(hotissue2);
		Hotissue actualHotissue2 = hotissueDao.get(2);
		assertSameHotissue(actualHotissue2, hotissue2);
		
		hotissueDao.add(hotissue3);
		Hotissue actualHotissue3 = hotissueDao.get(3);
		assertSameHotissue(actualHotissue3, hotissue3);
	}
	
	@Test
	public void get() {
		hotissueDao.deleteAll();
		assertThat(hotissueDao.getCount(), is(0));
		
		hotissueDao.add(hotissue1);
		hotissueDao.add(hotissue2);
		hotissueDao.add(hotissue3);
		assertThat(hotissueDao.getCount(), is(3));
		
		Hotissue actualHotissue1 = hotissueDao.get(hotissue1.getId());
		assertSameHotissue(actualHotissue1, hotissue1);
		
		Hotissue actualHotissue2 = hotissueDao.get(hotissue2.getId());
		assertSameHotissue(actualHotissue2, hotissue2);
		
		Hotissue actualHotissue3 = hotissueDao.get(hotissue3.getId());
		assertSameHotissue(actualHotissue3, hotissue3);
	}
	
	@Test
	public void addWithTimestamp() {
		hotissueDao.deleteAll();
		assertThat(hotissueDao.getCount(), is(0));
		
		hotissueDao.addWithTimestamp(hotissue1);
		hotissueDao.addWithTimestamp(hotissue2);
		hotissueDao.addWithTimestamp(hotissue3);
		assertThat(hotissueDao.getCount(), is(3));
		
		Hotissue actualHotissue1 = hotissueDao.get(hotissue1.getId());
		assertSameHotissue(actualHotissue1, hotissue1);
		
		Hotissue actualHotissue2 = hotissueDao.get(hotissue2.getId());
		assertSameHotissue(actualHotissue2, hotissue2);
		
		Hotissue actualHotissue3 = hotissueDao.get(hotissue3.getId());
		assertSameHotissue(actualHotissue3, hotissue3);
		
	}
	
	@Test
	public void getLatestHotissues() {
		hotissueDao.deleteAll();
		assertThat(hotissueDao.getCount(), is(0));
		
		hotissueDao.addWithTimestamp(hotissue1);
		hotissueDao.addWithTimestamp(hotissue2);
		hotissueDao.addWithTimestamp(hotissue3);
		assertThat(hotissueDao.getCount(), is(3));
		
		List<Hotissue> actualHotissues = hotissueDao.getLatestHotissues(3);
		assertSameHotissue(actualHotissues.get(0), hotissue3);
		assertSameHotissue(actualHotissues.get(1), hotissue2);
		assertSameHotissue(actualHotissues.get(2), hotissue1);
		
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
		
		hotissueDao.add(hotissue1);
		hotissueDao.add(hotissue2);
		hotissueDao.add(hotissue3);
		assertThat(hotissueDao.getCount(), is(3));
		
		assertThat(hotissueDao.delete(1), is(1));
		assertThat(hotissueDao.getCount(), is(2));
		
		assertThat(hotissueDao.delete(2), is(1));
		assertThat(hotissueDao.getCount(), is(1));
		
		assertThat(hotissueDao.delete(3), is(1));
		assertThat(hotissueDao.getCount(), is(0));
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void notDelete() {
		hotissueDao.deleteAll();
		assertThat(hotissueDao.getCount(), is(0));
		
		hotissueDao.add(hotissue1);
		
		Journal journal = new Journal(84);
		Section section = new Section(3);
		Hotissue hotissue = new Hotissue(1);
		
		Article article = new Article(1, hotissue, journal, section, "title1", "1111-01-01 01:11:11", "content1", 10000, 7000, 10.1);
		articleDao.add(article);
		
		hotissueDao.delete(1);
	}
	

	private void assertSameHotissue(Hotissue actual, Hotissue expected) {
		assertThat(actual.getName(), is(expected.getName()));
		
		if(expected.getTimestamp().charAt(0) != '1') {
			assertThat(actual.getTimestamp(), is(expected.getTimestamp()));
		}
	}

}
