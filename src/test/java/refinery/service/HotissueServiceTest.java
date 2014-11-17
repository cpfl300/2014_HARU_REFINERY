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
import refinery.model.Hotissue;

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
		hotissue1 = new Hotissue(1, "hotissue1");
		hotissue2 = new Hotissue(2, "hotissue2");
		hotissue3 = new Hotissue(3, "hotissue3");
	}

	@Test
	public void add() {
		int initialCount = hotissueDao.getCount();
		
		int actualHotissue1Key = hotissueService.add(hotissue1);
		assertThat(hotissueDao.getCount()-initialCount, is(1));
		assertThat(hotissueDao.get(actualHotissue1Key).getName(), is(hotissue1.getName()));
		
		int actualHotissue2Key = hotissueService.add(hotissue2);
		assertThat(hotissueDao.getCount()-initialCount, is(2));
		assertThat(hotissueDao.get(actualHotissue2Key).getName(), is(hotissue2.getName()));
		
		int actualHotissue3Key = hotissueService.add(hotissue3);
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
		
		hotissueDao.add(hotissue1);
		hotissueDao.add(hotissue2);
		hotissueDao.add(hotissue3);		
		assertThat(hotissueDao.getCount() - initialCount, is(3));

		
		assertThat(hotissueService.delete(1), is(1));
		assertThat(hotissueDao.getCount() - initialCount, is(2));
		
		assertThat(hotissueService.delete(2), is(1));
		assertThat(hotissueDao.getCount() - initialCount, is(1));
		
		assertThat(hotissueService.delete(3), is(1));
		assertThat(hotissueDao.getCount() - initialCount, is(0));		
	}

}
