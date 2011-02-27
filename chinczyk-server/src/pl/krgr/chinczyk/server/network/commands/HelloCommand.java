package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.network.commands.ServerCommand;

public class HelloCommand implements ServerCommand {

	private String message;
	
	public HelloCommand() {}
	
	@Override
	public void execute() {
		message = Responses.HELLO; 
	}

	@Override
	public String getResponse() {
		return message;
	}

}
