package pl.krgr.chinczyk.network.commands;

import pl.krgr.chinczyk.network.Requests;

public class CommandFactory {

	public static Command createCommand(String message) {
		if (Requests.CONNECT.equals(message)) {
			return new ConnectCommand();
		}
		return new ErrorCommand("Nieznana komenda");
	}
}
