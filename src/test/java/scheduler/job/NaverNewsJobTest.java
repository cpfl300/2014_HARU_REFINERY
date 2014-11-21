	package scheduler.job;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
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
	
	private Trigger trigger;
	private final String datePattern = "yyyy/MM/dd hh:mm:ss";
	
	@Before
	public void setup() throws SchedulerException {
		trigger = scheduler.getTrigger(new TriggerKey("naverNewsTrigger"));
	}
	
	@Test
	public void naverNewsJobSchedule() throws SchedulerException {
		String initialTime = "2014/12/07 12:00:00";
        List<String> expectedTimes = Arrays.asList(
        		"2014/12/07 12:10:00",
                "2014/12/07 12:20:00",
                "2014/12/07 12:30:00",
                "2014/12/07 12:40:00");
        
        assertSchedule(initialTime, expectedTimes);
	}
		 
	private void assertSchedule(String initialTime, List<String> expectedTimes) {
		Date startTime = null;
		try {
			startTime = DateUtils.parseDate(initialTime, new String[] { datePattern });
		} catch (ParseException e) {
			log.debug("wrong date format: " + e.getMessage());
		}

		for (String exptectedTime : expectedTimes) {
			Date nextExecutionTime = trigger.getFireTimeAfter(startTime);
			String actualTime = DateFormatUtils.format(nextExecutionTime, datePattern);
			
			assertThat(actualTime, is(exptectedTime));
			
			startTime = nextExecutionTime;
		}
	}
}
