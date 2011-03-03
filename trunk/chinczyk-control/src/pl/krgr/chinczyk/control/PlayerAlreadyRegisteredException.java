package pl.krgr.chinczyk.control;

public class PlayerAlreadyRegisteredException extends Exception {

	private static final long serialVersionUID = 6469956081526904663L;

	public PlayerAlreadyRegisteredException() {
	}

	public PlayerAlreadyRegisteredException(String message) {
		super(message);
	}

	public PlayerAlreadyRegisteredException(Throwable cause) {
		super(cause);
	}

	public PlayerAlreadyRegisteredException(String message, Throwable cause) {
		super(message, cause);
	}

}
