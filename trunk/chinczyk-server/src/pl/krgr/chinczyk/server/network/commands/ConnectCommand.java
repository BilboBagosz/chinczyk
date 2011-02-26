package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.network.commands.ServerCommand;

public class ConnectCommand implements ServerCommand {

	private String response;
	
	@Override
	public void execute() {
		System.out.println("ConnectCommand:execute()");
		this.response = Responses.CONNECT;
	}

	@Override
	public String getResponse() {
		return response;
	}

}
