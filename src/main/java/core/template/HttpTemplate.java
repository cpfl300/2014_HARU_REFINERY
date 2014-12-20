package core.template;

import core.template.GsonMapper;



public interface HttpTemplate {
	
	<T> T request(String uri, String query, Object[] objects, GsonMapper<T> jsonMapper);
	
	<T> T request(String uri, GsonMapper<T> gsonMapper);
	
	<T> T request(String uri, String query, Object[] objects, Class<T> clazz);
	
}
