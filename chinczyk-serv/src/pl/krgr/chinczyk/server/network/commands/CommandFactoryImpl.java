package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.network.Requests;
import pl.krgr.chinczyk.network.commands.CommandFactory;
import pl.krgr.chinczyk.network.commands.ServerCommand;

public class CommandFactoryImpl implements CommandFactory{

	public ServerCommand createCommand(String message) {
		if (Requests.CONNECT.equals(message)) {
			return new ConnectCommand();
		}
		return new ErrorCommand("Nieznana komenda");
	}
}
