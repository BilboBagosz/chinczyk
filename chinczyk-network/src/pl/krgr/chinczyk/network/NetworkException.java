package pl.krgr.chinczyk.network;

public class NetworkException extends Exception {

	private static final long serialVersionUID = 1L;

	public NetworkException() {
	}

	public NetworkException(String message) {
		super(message);
	}

	public NetworkException(Throwable cause) {
		super(cause);
	}

	public NetworkException(String message, Throwable cause) {
		super(message, cause);
	}

}
