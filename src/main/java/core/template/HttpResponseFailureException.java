package core.template;

public class HttpResponseFailureException extends RuntimeException {
	
	public HttpResponseFailureException (String message) {
		super(message);
	}
	
	public HttpResponseFailureException (String message, Throwable cause) {
		super(message, cause);
	}
}
