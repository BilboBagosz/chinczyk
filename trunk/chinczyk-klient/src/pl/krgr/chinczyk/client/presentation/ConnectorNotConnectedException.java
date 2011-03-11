package pl.krgr.chinczyk.client.presentation;

public class ConnectorNotConnectedException extends Exception {

	private static final long serialVersionUID = 1310762218411075950L;

	public ConnectorNotConnectedException() {
	}

	public ConnectorNotConnectedException(String message) {
		super(message);
	}

	public ConnectorNotConnectedException(Throwable cause) {
		super(cause);
	}

	public ConnectorNotConnectedException(String message, Throwable cause) {
		super(message, cause);
	}
}
