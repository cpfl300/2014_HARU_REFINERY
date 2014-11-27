package core.naver.news.api;

import core.template.Template;

public class NaverNewsAPI implements API {
	
	private String host;
	private Template template;
	
	public void setHost(String host) {
		this.host = host;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public <T> T get(String uri, Class<T> clazz) {
		
		return clazz.cast(template.get(host, uri, clazz));
	}


}