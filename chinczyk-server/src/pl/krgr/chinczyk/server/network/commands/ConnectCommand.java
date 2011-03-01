package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.server.Server;

public class ConnectCommand extends AbstractCommand {

	public ConnectCommand(Server server, int sessionId) {
		super(server, sessionId);
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
}
