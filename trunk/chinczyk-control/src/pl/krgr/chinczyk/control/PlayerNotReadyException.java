package pl.krgr.chinczyk.control;

public class PlayerNotReadyException extends Exception {

	private static final long serialVersionUID = -4757107549466539942L;

	public PlayerNotReadyException() {
	}

	public PlayerNotReadyException(String message) {
		super(message);
	}

	public PlayerNotReadyException(Throwable cause) {
		super(cause);
	}

	public PlayerNotReadyException(String message, Throwable cause) {
		super(message, cause);
	}
}
