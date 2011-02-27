package pl.krgr.chinczyk.server;

public class ServerException extends Exception {

	private static final long serialVersionUID = -4417256741514954440L;

	public ServerException() {
	}

	public ServerException(String message) {
		super(message);
	}

	public ServerException(Throwable cause) {
		super(cause);
	}

	public ServerException(String message, Throwable cause) {
		super(message, cause);
	}

}
