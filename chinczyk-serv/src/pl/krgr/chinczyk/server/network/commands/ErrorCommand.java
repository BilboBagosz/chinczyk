package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.network.commands.ServerCommand;

public class ErrorCommand implements ServerCommand {

	private String message;
	
	public ErrorCommand(String message) {
		this.message = message;
	}
	
	@Override
	public void execute() {
		message = String.format(Responses.ERROR, message); 
	}

	@Override
	public String getResponse() {
		return message;
	}

}
