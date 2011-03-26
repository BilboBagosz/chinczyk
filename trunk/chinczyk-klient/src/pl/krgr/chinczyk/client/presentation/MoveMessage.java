package pl.krgr.chinczyk.client.presentation;

import pl.krgr.chinczyk.model.Camp;

public class MoveMessage {

	private final Camp camp;
	private final int pawnPosition;
	private final int movement;

	public MoveMessage(Camp camp, int pawnPosition, int movement) {
		this.camp = camp;
		this.pawnPosition = pawnPosition;
		this.movement = movement;
	}

	public Camp getCamp() {
		return camp;
	}

	public int getPawnPosition() {
		return pawnPosition;
	}

	public int getMovement() {
		return movement;
	}

}
