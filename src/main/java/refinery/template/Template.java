package refinery.template;


public interface Template {
	
	public <T> T get(String host, String uri, Class<T> clazz);
}
