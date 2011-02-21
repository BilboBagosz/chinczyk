package pl.krgr.chinczyk.model;

public class IllegalPawnException extends RuntimeException {

	private static final long serialVersionUID = -6272022979422303084L;

	public IllegalPawnException() {
	}

	public IllegalPawnException(String message) {
		super(message);
	}

	public IllegalPawnException(Throwable cause) {
		super(cause);
	}

	public IllegalPawnException(String message, Throwable cause) {
		super(message, cause);
	}

}
