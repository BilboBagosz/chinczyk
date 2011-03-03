package pl.krgr.chinczyk.test.network.commands;

import pl.krgr.chinczyk.network.Requests;

public class DisconnectCommand extends AbstractClientCommand {
	
	@Override
	public String getRequest() {
		return Requests.DISCONNECT;
	}
}
