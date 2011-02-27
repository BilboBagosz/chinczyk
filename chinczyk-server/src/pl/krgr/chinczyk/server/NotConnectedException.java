package pl.krgr.chinczyk.server;

public class NotConnectedException extends Exception {

	private static final long serialVersionUID = 3185728332564792680L;

	public NotConnectedException() {}

	public NotConnectedException(String message) {
		super(message);
	}

	public NotConnectedException(Throwable cause) {
		super(cause);
	}

	public NotConnectedException(String message, Throwable cause) {
		super(message, cause);
	}

}
