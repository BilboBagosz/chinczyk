package pl.krgr.chinczyk.client.network;

import pl.krgr.chinczyk.client.presentation.ClientState;
import pl.krgr.chinczyk.model.Camp;
import pl.krgr.chinczyk.network.notifications.ClientNotification;

public class RequestMoveNotification implements ClientNotification {

	private final ClientState clientState;
	private final Camp camp;
	private final int movement;

	public RequestMoveNotification(ClientState clientState, Camp camp, int movement) {
		this.clientState = clientState;
		this.camp = camp;
		this.movement = movement;
	}

	@Override
	public void executeNotification() {
		clientState.handleRequestMove(camp, movement);
	}

}
