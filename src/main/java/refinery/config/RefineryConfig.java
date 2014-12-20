package refinery.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import core.template.HttpClientTemplate;
import core.template.HttpTemplate;

@Configuration
@ComponentScan(basePackages={"core", "refinery"})
@PropertySource(value="classpath:application-properties.xml")
@EnableScheduling
public class RefineryConfig implements SchedulingConfigurer {
    
	
	@Resource
	private Environment env;
	
//	@Resource(name="host")
//	public String host = env.getRequiredProperty("naver.news.host");
//
	@Bean
	public HttpTemplate httpTemplate() {
		
		String host = env.getRequiredProperty("naver.news.host");
		String context = env.getRequiredProperty("naver.news.api.context");
		
		return new HttpClientTemplate(host, context);
	}
	
	@Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    @Bean(destroyMethod="shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(100);
    }
	
//	@Bean
//	public RestTemplate restTemplate() {
//		
//		return new RestTemplate(Arrays.asList(new HttpMessageConverter[]{jsonConverter()}));
//		
//	}
	
//	@Bean
//	public MappingJackson2HttpMessageConverter jsonConverter() {
//		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
//		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
//		supportedMediaTypes.add(MediaType.TEXT_PLAIN);
//		supportedMediaTypes.add(new MediaType("text", "json"));
//		
//		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
//		jsonConverter.setSupportedMediaTypes(supportedMediaTypes);
//
//		return jsonConverter;
//	}
	
	
}