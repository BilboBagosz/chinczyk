package pl.krgr.chinczyk.client.network;

import pl.krgr.chinczyk.client.presentation.ClientState;
import pl.krgr.chinczyk.network.notifications.ClientNotification;

public class GameStartedNotification implements ClientNotification {

	private final ClientState state;

	public GameStartedNotification(ClientState state) {
		this.state = state;
	}
	
	@Override
	public void executeNotification() {
		state.handleGameStartedNotification();
	}

}
