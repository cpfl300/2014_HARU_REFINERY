package refinery.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@ComponentScan(basePackages={"refinery.scheduler"})
@EnableScheduling
@EnableAsync
public class RefineryServiceConfig implements SchedulingConfigurer, AsyncConfigurer {
	
	private static final Logger log = LoggerFactory.getLogger(RefineryServiceConfig.class);
	
	@Bean(destroyMethod="shutdown")
	public Executor taskExecutor() {
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(100);
		log.debug("taskExecutor");
		
		return executor;
	}
	
	@Bean
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(10);
        executor.setKeepAliveSeconds(30);
        executor.setThreadNamePrefix("RefineryExecutor-");
        executor.initialize();
        
        log.debug("active count: " + executor.getActiveCount());
        log.debug("corepoolsize: " + executor.getCorePoolSize());
        log.debug("getAsyncExecutor");
        
        return executor;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		 taskRegistrar.setScheduler(taskExecutor());
	}


	@Override
	public Executor getAsyncExecutor() {
		
        return asyncExecutor();
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		
		return new RefineryAsyncExceptionHandler();
	}

}
