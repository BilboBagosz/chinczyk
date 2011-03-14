package pl.krgr.chinczyk.server.network.notifications;

import pl.krgr.chinczyk.network.Notifications;
import pl.krgr.chinczyk.network.notifications.ServerNotification;

public class NewRoomNotification implements ServerNotification {

	private String roomInfo;
	
	public NewRoomNotification(String roomInfo) {
		this.roomInfo = roomInfo;
	}
	
	@Override
	public String getNotification() {
		return Notifications.NOTIFICATION_PREFIX + roomInfo; 
	}

}
