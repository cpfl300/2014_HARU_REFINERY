package core.naver.news.api;

import core.template.Template;


public interface API {
	
	<T> T get(String uri, Class<T> clazz);

	void setHost(String host);

	void setTemplate(Template template);
}
