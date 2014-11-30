	package refinery.scheduler.job;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import refinery.scheduler.config.RefinerySchedulerConfig;
import elixir.utility.ElixirUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={RefinerySchedulerConfig.class}, loader=AnnotationConfigContextLoader.class)
public class NaverNewsJobTest {
	
	@Autowired
	private Scheduler scheduler;
	
	private Trigger trigger;

	@Before
	public void setup() throws SchedulerException {
		trigger = scheduler.getTrigger(new TriggerKey("naverNewsTrigger"));
	}
	
	@Test
	public void naverNewsJobSchedule() throws SchedulerException {
		String initialTime = "2014-12-07 12:00:00";
        List<String> expectedTimes = Arrays.asList(
        		"2014-12-07 12:10:00",
                "2014-12-07 12:20:00",
                "2014-12-07 12:30:00",
                "2014-12-07 12:40:00");
        
        assertSchedule(initialTime, expectedTimes);
	}
		 
	private void assertSchedule(String initialTime, List<String> expectedTimes) {
		Date startTime = null;
		startTime = ElixirUtils.parseFormattedDate(initialTime);
		
		for (String exptectedTime : expectedTimes) {
			Date nextExecutionTime = trigger.getFireTimeAfter(startTime);
			String actualTime = ElixirUtils.formatDate(nextExecutionTime);
			
			assertThat(actualTime, is(exptectedTime));
			
			startTime = nextExecutionTime;
		}
	}
}