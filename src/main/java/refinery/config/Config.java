package refinery.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import scheduler.job.NaverNewsJob;
import scheduler.task.NaverNewsTask;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages={"core", "refinery", "scheduler"})
@PropertySource(value="classpath:application-properties.xml")
@EnableTransactionManagement
public class Config {
	
	@Autowired
	private NaverNewsTask naverNewsTask;
	
	@Resource
	private Environment env;
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(env.getRequiredProperty("database.driverClassName"));
		ds.setUrl(env.getRequiredProperty("database.url"));
		ds.setUsername(env.getRequiredProperty("database.username"));
		ds.setPassword(env.getRequiredProperty("database.password"));
		return ds;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		
		return new JdbcTemplate(dataSource());
	}
	
	@Bean
    public PlatformTransactionManager txManager() {
		
        return new DataSourceTransactionManager(dataSource());
    }
	
	
	@Bean
	public String host() {
		
		return env.getRequiredProperty("naver.news.api");
	}
	

	@Bean
	public JobDetailFactoryBean jobBeanFactoryBean() {
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
	public CronTriggerFactoryBean cronTriggerFactoryBean() {
		
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setName("naverNewsTrigger");
		cronTriggerFactoryBean.setJobDetail(jobBeanFactoryBean().getObject());
		cronTriggerFactoryBean.setStartDelay(1000);
		
		String cronExp = env.getRequiredProperty("cron.exp.naverapi.news");
		cronTriggerFactoryBean.setCronExpression(cronExp);
		
		return cronTriggerFactoryBean;
		
	}
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		
		schedulerFactoryBean.setJobDetails(new JobDetail[]{jobBeanFactoryBean().getObject()});
		schedulerFactoryBean.setTriggers(new Trigger[] {cronTriggerFactoryBean().getObject()});
		
		return schedulerFactoryBean;
		
	}
	
	
}