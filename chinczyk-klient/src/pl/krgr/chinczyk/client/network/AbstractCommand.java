package pl.krgr.chinczyk.client.network;

import pl.krgr.chinczyk.network.commands.ClientCommand;

public abstract class AbstractCommand implements ClientCommand {

	protected String response; 
	protected HandlerCallback callback;
	
	public AbstractCommand(HandlerCallback callback) {
		this.callback = callback;
	}
	
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
