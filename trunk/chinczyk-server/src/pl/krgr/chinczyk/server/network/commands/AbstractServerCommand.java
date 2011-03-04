package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.network.commands.ServerCommand;
import pl.krgr.chinczyk.server.Server;

public abstract class AbstractServerCommand implements ServerCommand {

	protected String response;
	protected Server server;
	protected int sessionId;

	public AbstractServerCommand(Server server, int sessionId) {
		this.server = server;
		this.sessionId = sessionId;
	}
	
	@Override
	public String getResponse() {
		return this.response;
	}
}
