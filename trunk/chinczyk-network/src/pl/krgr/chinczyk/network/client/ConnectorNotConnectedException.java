package pl.krgr.chinczyk.network.client;

public class ConnectorNotConnectedException extends Exception {

	private static final long serialVersionUID = 529646196809346343L;

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
