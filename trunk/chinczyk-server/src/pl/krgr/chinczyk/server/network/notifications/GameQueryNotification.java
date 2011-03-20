package pl.krgr.chinczyk.server.network.notifications;

import pl.krgr.chinczyk.network.Notifications;
import pl.krgr.chinczyk.network.notifications.ServerNotification;

public class GameQueryNotification implements ServerNotification {

	private final String notification;

	public GameQueryNotification(String notification) {
		this.notification = notification;
	}
	
	@Override
	public String getNotification() {
		return String.format(Notifications.GAME_QUERY, notification);
	}

}
