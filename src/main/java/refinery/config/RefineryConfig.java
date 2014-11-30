package refinery.config;

import javax.annotation.Resource;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import refinery.scheduler.job.HalfDayJob;
import refinery.scheduler.job.NaverNewsJob;
import refinery.scheduler.task.HalfDayTask;
import refinery.scheduler.task.NaverNewsTask;
import refinery.template.HttpClientTemplate;
import refinery.template.Template;

@Configuration
@ComponentScan(basePackages={"elixir", "refinery"})
@PropertySource(value="classpath:application-properties.xml")
@EnableTransactionManagement
public class RefineryConfig {
	
	private static final Logger log = LoggerFactory.getLogger(RefineryConfig.class);
	
	@Autowired
	private NaverNewsTask naverNewsTask;
	
	@Autowired
	private HalfDayTask halfDayTask;
	
	@Resource
	private Environment env;

	
	@Bean
	public Template httpClientTemplate() {
		
		return new HttpClientTemplate();
	}
	
//	@Bean
//	public Template fileIOTemplate() {
//		
//		return new FileIOTemplate();
//	}

	@Bean
	public String host() {
		
		return env.getRequiredProperty("naver.news.api");
	}
	

	@Bean
	public JobDetailFactoryBean naverNewsJobFactoryBean() {
		JobDetailFactoryBean jobBeanFactoryBean = new JobDetailFactoryBean();
		jobBeanFactoryBean.setJobClass(NaverNewsJob.class);
		jobBeanFactoryBean.setName("naverNewsJob");
		jobBeanFactoryBean.setDurability(true);
		
		JobDataMap jobDataMap = new JobDataMap();		
		jobDataMap.put("naverNewsTask", naverNewsTask);
		
		jobBeanFactoryBean.setJobDataMap(jobDataMap);
		
		return jobBeanFactoryBean;
	}
	
	@Bean
	public JobDetailFactoryBean halfDayJobFactoryBean() {
		JobDetailFactoryBean jobBeanFactoryBean = new JobDetailFactoryBean();
		jobBeanFactoryBean.setJobClass(HalfDayJob.class);
		jobBeanFactoryBean.setName("halfDayJob");
		jobBeanFactoryBean.setDurability(true);
		
		JobDataMap jobDataMap = new JobDataMap();		
		jobDataMap.put("halfDayTask", halfDayTask);
		
		jobBeanFactoryBean.setJobDataMap(jobDataMap);
		
		return jobBeanFactoryBean;
	}
	
	
	@Bean
	public CronTriggerFactoryBean naverNewsTriggerFactoryBean() {
		
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setName("naverNewsTrigger");
		cronTriggerFactoryBean.setJobDetail(naverNewsJobFactoryBean().getObject());
		cronTriggerFactoryBean.setStartDelay(1000);
		
		String cronExp = env.getRequiredProperty("cron.exp.naverapi.news");
		cronTriggerFactoryBean.setCronExpression(cronExp);
		
		return cronTriggerFactoryBean;
		
	}	

	@Bean
	public CronTriggerFactoryBean halfDayTriggerFactoryBean() {
		
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setName("halfDayTrigger");
		cronTriggerFactoryBean.setJobDetail(halfDayJobFactoryBean().getObject());
		cronTriggerFactoryBean.setStartDelay(1000);
		
		String cronExp = env.getRequiredProperty("cron.exp.news.extract");
		cronTriggerFactoryBean.setCronExpression(cronExp);
		
		return cronTriggerFactoryBean;
		
	}
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		
		schedulerFactoryBean.setJobDetails(new JobDetail[]{naverNewsJobFactoryBean().getObject(), halfDayJobFactoryBean().getObject()});
		schedulerFactoryBean.setTriggers(new Trigger[] {naverNewsTriggerFactoryBean().getObject(), halfDayTriggerFactoryBean().getObject()});
		
		return schedulerFactoryBean;
		
	}
	
}