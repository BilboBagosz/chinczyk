package pl.krgr.chinczyk.client.network;

import pl.krgr.chinczyk.client.presentation.ClientState;
import pl.krgr.chinczyk.network.notifications.ClientNotification;

public class GameQueryNotification implements ClientNotification {

	private final ClientState state;
	private final String message;

	public GameQueryNotification(ClientState state, String message) {
		this.state = state;
		this.message = message;
	}
	
	@Override
	public void executeNotification() {
		state.handleGameQueryNotification(message);
	}

}
