package pl.krgr.chinczyk.server.network.notifications;

import pl.krgr.chinczyk.network.Notifications;
import pl.krgr.chinczyk.network.notifications.ServerNotification;

public class GameStartedNotification implements ServerNotification {

	@Override
	public String getNotification() {
		return Notifications.GAME_STARTED;
	}

}
