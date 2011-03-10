package pl.krgr.chinczyk.client.network;

import pl.krgr.chinczyk.network.ProtocolHelper;
import pl.krgr.chinczyk.network.Requests;
import pl.krgr.chinczyk.network.Responses;

public class DisconnectCommand extends AbstractCommand {

	public DisconnectCommand(HandlerCallback callback) {
		super(callback);
	}

	@Override
	public String getRequest() {
		return Requests.DISCONNECT;
	}
	
	@Override
	public void execute() {
		String[] match = ProtocolHelper.matches(Responses.DISCONNECT, response);
		if (match.length > 0) {
			callback.commandExecuted(new CallBackEvent(true, response));
		} else {
			callback.commandExecuted(new CallBackEvent(false, response));
		}
	}
}