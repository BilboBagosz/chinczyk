package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.network.commands.ServerCommand;
import pl.krgr.chinczyk.server.Server;

public class ConnectCommand implements ServerCommand {

	private String response;
	private Server server;
	private int sessionId;
	
	public ConnectCommand(Server server, int sessionId) {
		this.server = server;
		this.sessionId = sessionId;
	}
	
	@Override
	public void execute() {
		String result = server.connectPlayer(sessionId);
		if (result == null) {
			response = String.format(Responses.CONNECT, sessionId);
		} else {
			response = String.format(Responses.ERROR, result);
		}
	}

	@Override
	public String getResponse() {
		return response;
	}
}
