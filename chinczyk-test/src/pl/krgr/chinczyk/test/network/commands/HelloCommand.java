package pl.krgr.chinczyk.test.network.commands;

import pl.krgr.chinczyk.network.Requests;
import pl.krgr.chinczyk.network.commands.ClientCommand;

public class HelloCommand implements ClientCommand {

	private String response;
	
	@Override
	public void execute() {
	}

	@Override
	public String getRequest() {
		return Requests.HELLO;
	}

	@Override
	public void setResponse(String response) {
		this.response = response;
	}

	public String getResponse() {
		return response;
	}

}
