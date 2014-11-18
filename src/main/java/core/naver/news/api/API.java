package core.naver.news.api;


public interface API {
	
	<T> T get(String uri, Class<T> clazz);
}
