package pl.krgr.chinczyk.client.presentation;

import pl.krgr.chinczyk.model.Camp;

public class ClearKillsMessage {

	private final Camp camp;

	public ClearKillsMessage(Camp camp) {
		this.camp = camp;
	}

	public Camp getCamp() {
		return camp;
	}

}
