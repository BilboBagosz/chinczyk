package pl.krgr.chinczyk.control;

public class PlayerAlreadyExistsForCamp extends Exception {

	private static final long serialVersionUID = 245986841663901650L;

	public PlayerAlreadyExistsForCamp() {}

	public PlayerAlreadyExistsForCamp(String message) {
		super(message);
	}

	public PlayerAlreadyExistsForCamp(Throwable cause) {
		super(cause);
	}

	public PlayerAlreadyExistsForCamp(String message, Throwable cause) {
		super(message, cause);
	}
}
