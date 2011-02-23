package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.network.Requests;
import pl.krgr.chinczyk.network.commands.ClientCommand;

public class CommandFactory {

	public static ClientCommand createCommand(String message) {
		if (Requests.CONNECT.equals(message)) {
			return new ConnectCommand();
		}
		return new ErrorCommand("Nieznana komenda");
	}
}
