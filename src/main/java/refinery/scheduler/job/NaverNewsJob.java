package refinery.scheduler.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import refinery.scheduler.task.NaverNewsTask;

@Component
public class NaverNewsJob extends QuartzJobBean {
	
	private static final Logger log = LoggerFactory.getLogger(NaverNewsJob.class);
	
	private NaverNewsTask naverNewsTask;
	
	@Autowired
	public void setNaverNewsTask(NaverNewsTask naverNewsTask) {
		this.naverNewsTask = naverNewsTask;
	}
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		naverNewsTask.getNews();
		log.debug("get news");
		
	}
}
