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

import refinery.config.RefineryConfig;
import elixir.utility.ElixirUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RefineryConfig.class, loader=AnnotationConfigContextLoader.class)
public class HalfDayJobTest {

	@Autowired
	private Scheduler scheduler;
	
	private Trigger trigger;
	
	@Before
	public void setup() throws SchedulerException {
		trigger = scheduler.getTrigger(new TriggerKey("halfDayTrigger"));
	}
	
	@Test
	public void halfDayJobSchedule() throws SchedulerException {
		String initialTime = "2014-12-07 06:00:00";
        List<String> expectedTimes = Arrays.asList(
        		"2014-12-07 18:00:00",
                "2014-12-08 06:00:00",
                "2014-12-08 18:00:00",
                "2014-12-09 06:00:00");
        
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
