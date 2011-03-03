package pl.krgr.chinczyk.test.network.commands;

import pl.krgr.chinczyk.network.commands.ClientCommand;

public abstract class AbstractClientCommand implements ClientCommand {

	protected String response; 
	
	@Override
	public void execute() {}

	@Override
	public void setResponse(String response) {
		this.response = response;
	}

	public String getResponse() {
		return response;
	}	
}
