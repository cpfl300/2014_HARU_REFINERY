package core.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import core.template.Template;
import core.template.fileio.FileIOTemplate;
import core.template.httpclient.HttpClientTemplate;

@Configuration
@ComponentScan(basePackages={"core"})
@PropertySource(value="classpath:application-properties.xml")
@EnableTransactionManagement
public class CoreConfig {
	
	@Resource
	private Environment env;
	
	@Bean
	public Template httpClientTemplate() {
		
		return new HttpClientTemplate();
	}
	
	@Bean
	public Template fileIOTemplate() {
		
		return new FileIOTemplate();
	}
	
//	@Bean
//	public API naverNewsAPI() {
//		API api = new NaverNewsAPI();
//		api.setHost(host());
//		api.setTemplate(httpClientTemplate());
//		
//		return api;
//	}
	
	
	@Bean
	public String host() {
		
		return env.getRequiredProperty("naver.news.api");
	}
	
	
}