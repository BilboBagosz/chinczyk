package pl.krgr.chinczyk.test.network.commands;

import pl.krgr.chinczyk.network.Requests;

public class GetRoomInfoCommand extends AbstractClientCommand {

	private int roomId;

	public GetRoomInfoCommand(int roomId) {
		this.roomId = roomId;
	}
	
	@Override
	public String getRequest() {
		return String.format(Requests.GET_ROOM_INFO, roomId);
	}

}
