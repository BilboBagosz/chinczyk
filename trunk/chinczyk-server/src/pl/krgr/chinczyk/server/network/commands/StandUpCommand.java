package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.control.GameAlreadyStartedException;
import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.server.NotConnectedException;
import pl.krgr.chinczyk.server.Server;

public class StandUpCommand extends AbstractServerCommand {

	private int roomId;
	private String playerName;
	
	public StandUpCommand(Server server, int sessionId, int roomId, String playerName) {
		super(server, sessionId);
		this.roomId = roomId;
		this.playerName = playerName;
	}

	@Override
	public void execute() {
		try {
			String roomInfo = server.standUp(roomId, sessionId, playerName);
			if (roomInfo == null) {
				response = String.format(Responses.ERROR, "Incorect room or player");
				return;
			}
			response = Responses.OK + roomInfo;
		} catch (NotConnectedException e) {
			response = String.format(Responses.ERROR, "PlayerNotConnected");
			e.printStackTrace();
		} catch (GameAlreadyStartedException e) {
			response = String.format(Responses.ERROR, "GameAlreadyStarted");
			e.printStackTrace();
		}
	}

}
