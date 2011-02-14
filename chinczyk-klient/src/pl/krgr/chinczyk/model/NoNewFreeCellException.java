package pl.krgr.chinczyk.model;

public class NoNewFreeCellException extends RuntimeException {

	private static final long serialVersionUID = -1874394018007067823L;

	public NoNewFreeCellException() {
	}

	public NoNewFreeCellException(String message) {
		super(message);
	}

	public NoNewFreeCellException(Throwable cause) {
		super(cause);
	}

	public NoNewFreeCellException(String message, Throwable cause) {
		super(message, cause);
	}

}
