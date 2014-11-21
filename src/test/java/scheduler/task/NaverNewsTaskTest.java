package scheduler.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import refinery.config.Config;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Config.class, loader=AnnotationConfigContextLoader.class)
public class NaverNewsTaskTest {
	
	private static final Logger log = LoggerFactory.getLogger(NaverNewsTaskTest.class);

	@Test
	public void getNews() {
		
		log.debug("get");
	}

}
