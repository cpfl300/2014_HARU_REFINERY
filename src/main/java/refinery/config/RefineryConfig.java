package refinery.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import refinery.template.HttpClientTemplate;
import refinery.template.Template;

@Configuration
@ComponentScan(basePackages={"elixir.config", "refinery"}, excludeFilters = @ComponentScan.Filter(value=RefineryPackageFilter.class, type=FilterType.CUSTOM))
@PropertySource(value="classpath:application-properties.xml")
@EnableTransactionManagement
public class RefineryConfig {
	
	@Resource
	private Environment env;

	
	@Bean
	public Template httpClientTemplate() {
		
		return new HttpClientTemplate();
	}

	@Bean
	public String host() {
		
		return env.getRequiredProperty("naver.news.api");
	}
	
}