package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.network.commands.ClientCommand;

public class ErrorCommand implements ClientCommand {

	private String message;
	
	public ErrorCommand(String message) {
		this.message = message;
	}
	
	@Override
	public void execute() {
		message = String.format(Responses.ERROR, message); 
	}

	@Override
	public String request() {
		return null;
	}

	@Override
	public String response() {
		return message;
	}

}
