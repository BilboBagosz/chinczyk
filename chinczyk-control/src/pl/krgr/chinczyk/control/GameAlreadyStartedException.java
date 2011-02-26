package pl.krgr.chinczyk.control;

public class GameAlreadyStartedException extends Exception {

	private static final long serialVersionUID = 9002673666341998487L;

	public GameAlreadyStartedException() {}

	public GameAlreadyStartedException(String message) {
		super(message);
	}

	public GameAlreadyStartedException(Throwable cause) {
		super(cause);
	}

	public GameAlreadyStartedException(String message, Throwable cause) {
		super(message, cause);
	}

}
