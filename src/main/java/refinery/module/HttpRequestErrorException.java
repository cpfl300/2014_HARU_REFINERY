package refinery.module;

public class HttpRequestErrorException extends RuntimeException {
	
	public HttpRequestErrorException (String message) {
		super(message);
	}
	
	public HttpRequestErrorException (String message, Throwable cause) {
		super(message, cause);
	}
}
