package core.httpclient;

public class HttpRequestFailureException extends RuntimeException {
	
	public HttpRequestFailureException (String message) {
		super(message);
	}
	
	public HttpRequestFailureException (String message, Throwable cause) {
		super(message, cause);
	}
}
