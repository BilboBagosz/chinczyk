package pl.krgr.chinczyk.client.presentation;

public class GameResultMessage {

	private String message;

	public GameResultMessage(String message) {
		this.message = message;
	}
	
	public void setMessage(String getMessage) {
		this.message = getMessage;
	}

	public String getMessage() {
		return message;
	}
}
