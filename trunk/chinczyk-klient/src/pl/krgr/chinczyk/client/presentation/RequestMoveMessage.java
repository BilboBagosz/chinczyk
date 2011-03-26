package pl.krgr.chinczyk.client.presentation;

import pl.krgr.chinczyk.client.network.HandlerCallback;
import pl.krgr.chinczyk.model.Camp;

public class RequestMoveMessage {

	private final HandlerCallback handlerCallback;
	private final Camp camp;
	private final int movement;

	public RequestMoveMessage(Camp camp, int movement, HandlerCallback handlerCallback) {
		this.camp = camp;
		this.movement = movement;
		this.handlerCallback = handlerCallback;
	}

	public HandlerCallback getHandlerCallback() {
		return handlerCallback;
	}

	public Camp getCamp() {
		return camp;
	}

	public int getMovement() {
		return movement;
	}

}
