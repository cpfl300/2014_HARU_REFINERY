package core.naver.news.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import core.httpclient.HttpClientTemplate;

@Component
public class NaverNewsAPI implements API {
	
	@Autowired
	private String host;
	
	@Autowired
	private HttpClientTemplate httpClientTemplate;	
	
	public <T> T get(String uri, Class<T> clazz) {
		
		return clazz.cast(httpClientTemplate.get(host, uri, clazz));
	}


}