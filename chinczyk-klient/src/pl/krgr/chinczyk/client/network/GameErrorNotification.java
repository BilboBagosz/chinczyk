package pl.krgr.chinczyk.client.network;

import pl.krgr.chinczyk.client.presentation.ClientState;
import pl.krgr.chinczyk.network.notifications.ClientNotification;

public class GameErrorNotification implements ClientNotification {

	private final ClientState state;
	private final String messge;

	public GameErrorNotification(ClientState state, String messge) {
		this.state = state;
		this.messge = messge;
	}
	
	@Override
	public void executeNotification() {
		state.handleErrorMessageNotification(messge);
	}

}
