package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.control.GameAlreadyStartedException;
import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.server.NotConnectedException;
import pl.krgr.chinczyk.server.Server;

public class StandUpCommand extends AbstractServerCommand {

	private int roomId;
	
	public StandUpCommand(Server server, int sessionId, int roomId) {
		super(server, sessionId);
		this.roomId = roomId;
	}

	@Override
	public void execute() {
		try {
			String roomInfo = server.standUp(roomId, sessionId);
			if (roomInfo == null) {
				response = String.format(Responses.ERROR, "Incorect roomId, room = null");
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
