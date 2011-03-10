package pl.krgr.chinczyk.client.network;

import pl.krgr.chinczyk.network.ProtocolHelper;
import pl.krgr.chinczyk.network.Requests;
import pl.krgr.chinczyk.network.Responses;

public class ConnectCommand extends AbstractCommand {

	public ConnectCommand(HandlerCallback callback) {
		super(callback);
	}
	
	@Override
	public String getRequest() {
		return Requests.CONNECT;
	}
	
	@Override
	public void execute() {
		String[] match = ProtocolHelper.matches(Responses.CONNECT, response);
		if (match.length > 0) {
			callback.commandExecuted(new CallBackEvent(true, response));
		} else {
			callback.commandExecuted(new CallBackEvent(false, response));
		}
	}

}
