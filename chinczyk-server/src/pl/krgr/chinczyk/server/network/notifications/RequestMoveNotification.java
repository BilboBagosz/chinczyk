package pl.krgr.chinczyk.server.network.notifications;

import pl.krgr.chinczyk.model.Camp;
import pl.krgr.chinczyk.network.Notifications;
import pl.krgr.chinczyk.network.notifications.ServerNotification;

public class RequestMoveNotification implements ServerNotification {

	private final int movement;
	private final Camp camp;

	public RequestMoveNotification(Camp camp, int movement) {
		this.camp = camp;
		this.movement = movement;
	}
	
	@Override
	public String getNotification() {
		return String.format(Notifications.REQUEST_MOVE, camp.toString(), movement);
	}
}
