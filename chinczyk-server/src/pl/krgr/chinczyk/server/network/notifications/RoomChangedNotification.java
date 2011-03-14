package pl.krgr.chinczyk.server.network.notifications;

import pl.krgr.chinczyk.network.Notifications;
import pl.krgr.chinczyk.network.notifications.ServerNotification;

public class RoomChangedNotification implements ServerNotification {

	private String roomInfo;

	public RoomChangedNotification(String roomInfo) {
		this.roomInfo = roomInfo;
	}
	
	@Override
	public String getNotification() {
		return Notifications.NOTIFICATION_PREFIX + "CHANGED " + roomInfo;
	}

}
