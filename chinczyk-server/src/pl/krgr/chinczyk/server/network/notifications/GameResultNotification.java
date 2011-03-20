package pl.krgr.chinczyk.server.network.notifications;

import pl.krgr.chinczyk.network.Notifications;
import pl.krgr.chinczyk.network.notifications.ServerNotification;

public class GameResultNotification implements ServerNotification {

	private String notification;
	
	public GameResultNotification(String notif) {
		notification = notif;
	}
	
	@Override
	public String getNotification() {
		return String.format(Notifications.GAME_RESULT, notification);
	}

}
