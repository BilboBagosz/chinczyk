package pl.krgr.chinczyk.server.network.notifications;

import pl.krgr.chinczyk.model.Camp;
import pl.krgr.chinczyk.network.Notifications;
import pl.krgr.chinczyk.network.notifications.ServerNotification;

public class ClearKillsNotification implements ServerNotification {

	private final Camp camp;

	public ClearKillsNotification(Camp camp) {
		this.camp = camp;
	}

	@Override
	public String getNotification() {
		return String.format(Notifications.CLEAR_KILLS, camp.toString());
	}

}
