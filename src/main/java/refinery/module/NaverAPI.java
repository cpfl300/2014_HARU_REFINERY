package refinery.module;

import org.springframework.beans.factory.annotation.Autowired;

import core.httpclient.HttpClientTemplate;

public class NaverAPI {
	
	private String host;

	@Autowired
	private HttpClientTemplate httpClientTemplate;	

	@Autowired
	public NaverAPI(String host) {
		this.host = host;
	}
	
	public <T> T get(String uri, Class<T> clazz) {
		
		return clazz.cast(httpClientTemplate.get(host, uri, clazz));
	}
}