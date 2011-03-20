package pl.krgr.chinczyk.client.network;

import pl.krgr.chinczyk.client.presentation.ClientState;
import pl.krgr.chinczyk.network.notifications.ClientNotification;

public class GameResultNotification implements ClientNotification {

	private final ClientState state;
	private final String notification;

	public GameResultNotification(ClientState state, String notification) {
		this.state = state;
		this.notification = notification;
	}

	@Override
	public void executeNotification() {
		state.handleGameResultNotification(notification);
	}

}
