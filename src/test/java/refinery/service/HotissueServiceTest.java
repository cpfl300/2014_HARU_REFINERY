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
import refinery.dao.HotissueDao;
import refinery.model.Article;
import refinery.model.Hotissue;
import refinery.model.Journal;
import refinery.model.Section;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Config.class, loader=AnnotationConfigContextLoader.class)
@Transactional
public class HotissueServiceTest {
	
	@Autowired
	private HotissueDao hotissueDao;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private HotissueService hotissueService;
	
	private Hotissue hotissue1;
	private Hotissue hotissue2;
	private Hotissue hotissue3;
	
	@Before
	public void setup() {
		// fixtures
		hotissue1 = new Hotissue("hotissue1");
		hotissue2 = new Hotissue("hotissue2");
		hotissue3 = new Hotissue("hotissue3");
	}

	@Test
	public void add() {
		int initialCount = hotissueDao.getCount();
		
		long actualHotissue1Key = hotissueService.add(hotissue1);
		assertThat(hotissueDao.getCount()-initialCount, is(1));
		assertThat(hotissueDao.get(actualHotissue1Key).getName(), is(hotissue1.getName()));
		
		long actualHotissue2Key = hotissueService.add(hotissue2);
		assertThat(hotissueDao.getCount()-initialCount, is(2));
		assertThat(hotissueDao.get(actualHotissue2Key).getName(), is(hotissue2.getName()));
		
		long actualHotissue3Key = hotissueService.add(hotissue3);
		assertThat(hotissueDao.getCount()-initialCount, is(3));
		assertThat(hotissueDao.get(actualHotissue3Key).getName(), is(hotissue3.getName()));
	}
	
	@Test
	public void addExistedHotissue() {
		int initialCount = hotissueDao.getCount();
		
		hotissueService.add(hotissue1);
		assertThat(hotissueDao.getCount()-initialCount, is(1));
		
		hotissueService.add(hotissue1);
		assertThat(hotissueDao.getCount()-initialCount, is(1));
		
		hotissueService.add(hotissue1);
		assertThat(hotissueDao.getCount()-initialCount, is(1));
	}
	
	@Test
	public void delete() {
		int initialCount = hotissueDao.getCount();
		
		long hotissue1Id = hotissueDao.add(hotissue1);
		long hotissue2Id = hotissueDao.add(hotissue2);
		long hotissue3Id = hotissueDao.add(hotissue3);		
		assertThat(hotissueDao.getCount() - initialCount, is(3));

		
		assertThat(hotissueService.delete(hotissue1Id), is(1));
		assertThat(hotissueDao.getCount() - initialCount, is(2));
		
		assertThat(hotissueService.delete(hotissue2Id), is(1));
		assertThat(hotissueDao.getCount() - initialCount, is(1));
		
		assertThat(hotissueService.delete(hotissue3Id), is(1));
		assertThat(hotissueDao.getCount() - initialCount, is(0));		
	}

}
