package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.server.Server;

public class DisconnectCommand extends AbstractCommand {
	
	public DisconnectCommand(Server server, int sessionId) {
		super(server, sessionId);
	}
	
	@Override
	public void execute() {
		server.disconnectPlayer(sessionId);		
		response = Responses.DISCONNECT;
	}
}
