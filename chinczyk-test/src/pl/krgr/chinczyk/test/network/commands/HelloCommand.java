package pl.krgr.chinczyk.test.network.commands;

import pl.krgr.chinczyk.network.Requests;

public class HelloCommand extends AbstractClientCommand {

	@Override
	public String getRequest() {
		return Requests.HELLO;
	}
}
