package refinery.apihandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import refinery.template.Template;

@Component
public class NaverNewsAPI implements API {
	
	private String host;
	private Template template;
	
	@Autowired
	public void setHost(String host) {
		this.host = host;
	}

	@Autowired
	public void setTemplate(Template template) {
		this.template = template;
	}

	public <T> T get(String uri, Class<T> clazz) {
		
		return clazz.cast(template.get(host, uri, clazz));
	}


}