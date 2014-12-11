package core.template;

import java.util.regex.PatternSyntaxException;

public abstract class HttpTemplateImpl implements HttpTemplate {
	
	private static final String QUESTION = "?";
	private static final String DOUBLE_BACK_SLASH = "\\";
	
	protected String host;
	protected String context;
	
	protected HttpTemplateImpl(String host, String context) {
		this.host = host;
		this.context = context;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	public void setContext(String context) {
		this.context = context;
	}

	protected String createURL(String uri, String query, Object[] objects) {
		String queryString = formatQuery(query, objects);
		
		return this.host + this.context + uri + QUESTION + queryString;
	}
	
	protected String createURL(String uri) {
		
		return this.host + this.context + uri;
	}
	
	protected String formatQuery(String query, Object[] objects) {
		String result = query;
		
		try {
			for(Object o : objects) {
				String str = (String) o;
				result = result.replaceFirst(DOUBLE_BACK_SLASH + QUESTION, str);
			}
		} catch (PatternSyntaxException e) {
			// do nothing
		}
		
		return result;
	}
}
