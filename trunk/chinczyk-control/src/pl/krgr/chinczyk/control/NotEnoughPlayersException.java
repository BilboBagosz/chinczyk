package pl.krgr.chinczyk.control;

public class NotEnoughPlayersException extends Exception {

	private static final long serialVersionUID = 6356712081734182748L;

	public NotEnoughPlayersException() {}

	public NotEnoughPlayersException(String message) {
		super(message);
	}

	public NotEnoughPlayersException(Throwable cause) {
		super(cause);
	}

	public NotEnoughPlayersException(String message, Throwable cause) {
		super(message, cause);
	}
}
