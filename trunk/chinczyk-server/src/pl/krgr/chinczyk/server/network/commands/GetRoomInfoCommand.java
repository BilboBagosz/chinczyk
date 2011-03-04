package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.server.NotConnectedException;
import pl.krgr.chinczyk.server.Server;

public class GetRoomInfoCommand extends AbstractServerCommand {

	private int roomId; 
	
	public GetRoomInfoCommand(Server server, int sessionId, int roomId) {
		super(server, sessionId);
		this.roomId = roomId;
	}

	@Override
	public void execute() {
		try {
			String roomInfo = server.getRoom(roomId, sessionId);
			if (roomInfo == null) {
				response = String.format(Responses.ERROR, "Incorect roomId, room = null");
				return;
			}
			response = roomInfo;
		} catch (NotConnectedException e) {
			response = String.format(Responses.ERROR, "Player Not Connected");
			e.printStackTrace();			
		}
	}

}
