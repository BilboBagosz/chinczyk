package pl.krgr.chinczyk.test.network.commands;

import pl.krgr.chinczyk.network.Requests;

public class ConnectCommand extends AbstractClientCommand {
	
	@Override
	public String getRequest() {
		return Requests.CONNECT;
	}
}
