package pl.krgr.chinczyk.test.network.commands;

import pl.krgr.chinczyk.network.Requests;

public class StandUpCommand extends AbstractClientCommand {

	private int roomId;

	public StandUpCommand(int roomId) {
		this.roomId = roomId;
	}
	
	@Override
	public String getRequest() {
		return String.format(Requests.STAND_UP, roomId);
	}

}
