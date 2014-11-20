package refinery.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.Trigger;
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

import refinery.job.detail.NaverNewsJobBean;
import refinery.job.task.NaverNewsTask;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages={"refinery", "core"})
@PropertySource(value="classpath:application-properties.xml")
@EnableTransactionManagement
public class Config {
	
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
	public JobDetailFactoryBean naverNewsJobBean() {
		JobDetailFactoryBean jobBean = new JobDetailFactoryBean();
		jobBean.setJobClass(NaverNewsJobBean.class);
		jobBean.setDurability(true);
		
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("NaverNewsTask", NaverNewsTask.class);
		
		jobBean.setJobDataMap(jobDataMap);
		
		return jobBean;
	}
	
	
	@Bean
	public CronTrigger cronTrigger() {
		
		CronTriggerFactoryBean cronTriggerFactory = new CronTriggerFactoryBean();
		cronTriggerFactory.setStartDelay(1000);
		cronTriggerFactory.setCronExpression("0/3 * * * * ?");
		
		return cronTriggerFactory.getObject();
		
	}
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
		
		Trigger[] triggerArr = new Trigger[1];
		triggerArr[0] = cronTrigger();
		
		scheduler.setTriggers(triggerArr);
		
		return scheduler;
		
	}
	
	
	
	
	
}