package pl.krgr.chinczyk.control;

public class BoardNotRegisteredException extends Exception {

	private static final long serialVersionUID = -3154975856331535845L;

	public BoardNotRegisteredException() {}

	public BoardNotRegisteredException(String message) {
		super(message);
	}

	public BoardNotRegisteredException(Throwable cause) {
		super(cause);
	}

	public BoardNotRegisteredException(String message, Throwable cause) {
		super(message, cause);
	}

}
