package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.network.Requests;
import pl.krgr.chinczyk.network.commands.CommandFactory;
import pl.krgr.chinczyk.network.commands.ServerCommand;
import pl.krgr.chinczyk.server.Server;

public class CommandFactoryImpl implements CommandFactory {

	private Server serverInstance;
	
	public CommandFactoryImpl(Server server) {
		this.serverInstance = server;
	}
	
	@Override
	public ServerCommand createCommand(String message, int sessionId) {
		if (Requests.HELLO.equals(message)) {
			return new HelloCommand();
		}
		if (Requests.CONNECT.equals(message)) {
			return new ConnectCommand(serverInstance, sessionId);
		}
		return new ErrorCommand("Nieznana komenda");
	}
}
