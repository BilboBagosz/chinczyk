package pl.krgr.chinczyk.control;

public class BoardNotValidException extends Exception {

	private static final long serialVersionUID = 4593404557924401590L;

	public BoardNotValidException() {}

	public BoardNotValidException(String message) {
		super(message);
	}

	public BoardNotValidException(Throwable cause) {
		super(cause);
	}

	public BoardNotValidException(String message, Throwable cause) {
		super(message, cause);
	}
}
