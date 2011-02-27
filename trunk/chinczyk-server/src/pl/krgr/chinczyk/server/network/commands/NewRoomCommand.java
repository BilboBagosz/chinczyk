package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.network.commands.ServerCommand;
import pl.krgr.chinczyk.server.NotConnectedException;
import pl.krgr.chinczyk.server.Room;
import pl.krgr.chinczyk.server.Server;

public class NewRoomCommand implements ServerCommand {

	private Server server;
	private String response;
	private int sessionId;

	public NewRoomCommand(Server server, int sessionId) {
		this.server = server;
		this.sessionId = sessionId;
	}
	
	@Override
	public void execute() {
		try {
			Room room = server.createNewRoom(sessionId);
			
			response = Responses.NEW_ROOM;
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String getResponse() {
		return response;
	}

}
