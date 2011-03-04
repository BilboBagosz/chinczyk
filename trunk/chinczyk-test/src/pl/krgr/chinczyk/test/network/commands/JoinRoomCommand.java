package pl.krgr.chinczyk.test.network.commands;

import pl.krgr.chinczyk.network.Requests;

public class JoinRoomCommand extends AbstractClientCommand {

	private int roomId;
	private String playerName;
	private String camp;
	
	public JoinRoomCommand(int roomId, String playerName, String camp) {
		this.roomId = roomId;
		this.playerName = playerName;
		this.camp = camp;
	}
	
	@Override
	public String getRequest() {
		return String.format(Requests.JOIN_ROOM, roomId, playerName, camp);
	}

}
