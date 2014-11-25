package scheduler.job;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import refinery.utility.RefineryUtils;
import scheduler.task.HalfDayTask;

@Component
public class HalfDayJob extends QuartzJobBean{
	
	private HalfDayTask halfDayTask;
	private static final int N = 30;
	
	@Autowired
	public void setNaverNewsTask(HalfDayTask halfDayTask) {
		this.halfDayTask = halfDayTask;
	}
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Date today = RefineryUtils.getToday();
		String timestamp = RefineryUtils.formatDate(today);
		
		Date serviceDate = RefineryUtils.nextServiceDate(today);
		String[] dates = RefineryUtils.getServiceFormattedDatesByDate(serviceDate);

		halfDayTask.extract(timestamp, dates, N);
		
	}

}
