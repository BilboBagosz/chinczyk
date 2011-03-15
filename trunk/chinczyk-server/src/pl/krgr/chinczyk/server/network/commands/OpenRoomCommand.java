package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.server.Room;
import pl.krgr.chinczyk.server.Server;
import pl.krgr.chinczyk.server.ServerImpl;

public class OpenRoomCommand extends AbstractServerCommand {

	private int roomId;
	
	public OpenRoomCommand(Server server, int sessionId, int roomId) {
		super(server, sessionId);
		this.roomId = roomId;
	}

	@Override
	public void execute() {
		Room room = ((ServerImpl)server).getRoom(roomId);
		if (room == null) {
			response = String.format(Responses.ERROR, "Pokój nieprawid³owy.");
		} else if (room.gameStarted()) {
			response = String.format(Responses.ERROR, "Gra ju¿ siê zaczê³a.");
		} else {
			response = Responses.OK + "OPEN ROOM " + room.info();
		}		
	}

}
