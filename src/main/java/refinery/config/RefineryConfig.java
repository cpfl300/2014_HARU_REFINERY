package refinery.config;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import refinery.core.template.HttpClientTemplate;
import refinery.core.template.HttpTemplate;

@Configuration
@ComponentScan(basePackages={"refinery.core", "refinery.aao", "refinery.service"})
@PropertySource(value="classpath:application-properties.xml")
public class RefineryConfig {
	
	private static final Logger log = LoggerFactory.getLogger(RefineryConfig.class);
	
	@Resource
	private Environment env;
	
	@Bean
	public HttpTemplate httpTemplate() {
		
		String host = env.getRequiredProperty("naver.news.host");
		log.debug("host: " + host);
		String context = env.getRequiredProperty("naver.news.api.context");
		
		return new HttpClientTemplate(host, context);
	}
}