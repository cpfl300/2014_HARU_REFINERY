package scheduler.job;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import refinery.config.Config;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Config.class, loader=AnnotationConfigContextLoader.class)
public class NaverNewsJobTest {
	
	private static final Logger log = LoggerFactory.getLogger(NaverNewsJobTest.class);
	
	@Autowired
	private Scheduler scheduler;
	
	@Test
	public void printJobDetailName() throws SchedulerException {
		List<String> jobDetails = scheduler.getJobGroupNames();
		
		Iterator<String> ir = jobDetails.iterator();
		while (ir.hasNext()) {
			log.debug("jobDetail: " + ir.next());
			
		}
	}

}
