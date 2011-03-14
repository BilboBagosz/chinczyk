package pl.krgr.chinczyk.client.network;

import pl.krgr.chinczyk.client.presentation.ClientState;
import pl.krgr.chinczyk.network.notifications.ClientNotification;

public class DisconnectNotification implements ClientNotification {

	private ClientState state;
	
	public DisconnectNotification(ClientState state) {
		this.state = state;
	}

	@Override
	public void executeNotification() {
		state.clearRooms();
		state.setConnected(false);
	}

}
