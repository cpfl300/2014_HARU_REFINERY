package refinery.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import core.template.HttpClientTemplate;
import core.template.HttpTemplate;

@Configuration
@ComponentScan(basePackages={"core", "refinery"})
@PropertySource(value="classpath:application-properties.xml")
public class RefineryConfig {
	
	@Resource
	private Environment env;

	@Bean
	public HttpTemplate httpTemplate() {
		
		String host = env.getRequiredProperty("naver.news.host");
		String context = env.getRequiredProperty("naver.news.api.context");
		
		return new HttpClientTemplate(host, context);
	}
	
}