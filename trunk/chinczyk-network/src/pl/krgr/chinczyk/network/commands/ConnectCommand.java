package pl.krgr.chinczyk.network.commands;

import pl.krgr.chinczyk.network.Requests;
import pl.krgr.chinczyk.network.Responses;

public class ConnectCommand implements Command {

	private String response;
	
	@Override
	public void execute() {
		System.out.println("ConnectCommand:execute()");
		this.response = Responses.CONNECT;
	}

	@Override
	public String request() {
		return Requests.CONNECT;
	}

	@Override
	public String response() {
		return response;
	}

}
