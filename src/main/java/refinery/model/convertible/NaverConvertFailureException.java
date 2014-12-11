package refinery.model.convertible;

public class NaverConvertFailureException extends RuntimeException {
	
	public NaverConvertFailureException (String message) {
		super(message);
	}
	
	public NaverConvertFailureException (String message, Throwable cause) {
		super(message, cause);
	}
}
