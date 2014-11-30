package refinery.apihandler;

import refinery.template.Template;


public interface API {
	
	<T> T get(String uri, Class<T> clazz);

	void setHost(String host);

	void setTemplate(Template template);
}
