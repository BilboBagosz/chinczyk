package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.network.commands.ServerCommand;
import pl.krgr.chinczyk.server.Server;

public class DisconnectCommand implements ServerCommand {

	private Server server;
	private int sessionId;
	private String response;
	
	public DisconnectCommand(Server server, int sessionId) {
		this.sessionId = sessionId;
		this.server = server;
	}
	
	@Override
	public void execute() {
		server.disconnectPlayer(sessionId);		
		this.response = Responses.DISCONNECT;
	}

	@Override
	public String getResponse() {
		return response;
	}

}
