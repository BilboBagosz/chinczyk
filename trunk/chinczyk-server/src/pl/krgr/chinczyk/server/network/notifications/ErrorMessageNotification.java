package pl.krgr.chinczyk.server.network.notifications;

import pl.krgr.chinczyk.network.Notifications;
import pl.krgr.chinczyk.network.notifications.ServerNotification;

public class ErrorMessageNotification implements ServerNotification {

	private final String message;
	
	public ErrorMessageNotification(String message) {
		this.message = message;
	}
	
	@Override
	public String getNotification() {
		return String.format(Notifications.GAME_ERROR, message);
	}

}
