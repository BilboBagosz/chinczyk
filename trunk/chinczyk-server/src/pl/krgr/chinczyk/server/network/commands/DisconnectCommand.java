package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.server.Server;

public class DisconnectCommand extends AbstractServerCommand {
	
	public DisconnectCommand(Server server, int sessionId) {
		super(server, sessionId);
	}
	
	@Override
	public void execute() {
		String result = server.disconnectPlayer(sessionId);		
		if (result == null) {
			response = Responses.DISCONNECT;
		} else {
			response = String.format(Responses.ERROR, result);
		}
	}
}
