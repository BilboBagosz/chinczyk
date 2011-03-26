package pl.krgr.chinczyk.server;

public class WrongPawnException extends Exception {

	private static final long serialVersionUID = -8928132350322338984L;

	public WrongPawnException() {
	}

	public WrongPawnException(String message) {
		super(message);
	}

	public WrongPawnException(Throwable cause) {
		super(cause);
	}

	public WrongPawnException(String message, Throwable cause) {
		super(message, cause);
	}

}
