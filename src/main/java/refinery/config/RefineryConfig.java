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
@ComponentScan(basePackages={"refinery.core", "refinery.aao"})
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