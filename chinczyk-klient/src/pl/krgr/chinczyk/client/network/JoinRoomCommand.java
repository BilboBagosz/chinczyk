package pl.krgr.chinczyk.client.network;

import pl.krgr.chinczyk.client.presentation.Room;
import pl.krgr.chinczyk.model.Camp;
import pl.krgr.chinczyk.network.ProtocolHelper;
import pl.krgr.chinczyk.network.Requests;
import pl.krgr.chinczyk.network.Responses;

public class JoinRoomCommand extends AbstractCommand {
		
	private int roomId;
	private String playerName;
	private Camp camp;

	public JoinRoomCommand(int roomId, String playerName, Camp camp, HandlerCallback callback) {
		super(callback);
		this.roomId = roomId;
		this.playerName = playerName;
		this.camp = camp;
	}

	@Override
	public String getRequest() {		
		return String.format(Requests.JOIN_ROOM, roomId, playerName, camp.toString());
	}

	public void execute() {
		String[] roomInfo = ProtocolHelper.matches(Responses.JOIN_ROOM, response);
		if (roomInfo.length != 10) {
			callback.commandExecuted(new CallBackEvent(false, response));
		} else {
			Room room = Room.fromStrings(roomInfo);
			callback.commandExecuted(new CallBackEvent(true, response, room));
		}		
	}
}
