package pl.krgr.chinczyk.test.network.commands;

import pl.krgr.chinczyk.network.Requests;
import pl.krgr.chinczyk.network.commands.ClientCommand;

public class NewRoomCommand implements ClientCommand {

	private String response;
	
	@Override
	public void execute() {
	}

	@Override
	public String getRequest() {
		return Requests.NEW_ROOM;
	}

	@Override
	public void setResponse(String response) {
		this.response = response;
	}

	public String getResponse() {
		return this.response;
	}
	
}
