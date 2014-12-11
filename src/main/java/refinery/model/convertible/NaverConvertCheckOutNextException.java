package refinery.model.convertible;

public class NaverConvertCheckOutNextException extends RuntimeException {
	
	public NaverConvertCheckOutNextException (String message) {
		super(message);
	}
	
	public NaverConvertCheckOutNextException (String message, Throwable cause) {
		super(message, cause);
	}
}
