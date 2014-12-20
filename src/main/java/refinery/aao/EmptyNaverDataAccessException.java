package refinery.aao;

public class EmptyNaverDataAccessException extends RuntimeException {
	
	public EmptyNaverDataAccessException (String message) {
		super(message);
	}
	
	public EmptyNaverDataAccessException (String message, Throwable cause) {
		super(message, cause);
	}
	
}
