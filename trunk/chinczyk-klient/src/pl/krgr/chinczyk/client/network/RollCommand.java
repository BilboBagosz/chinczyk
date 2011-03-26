package pl.krgr.chinczyk.client.network;

import pl.krgr.chinczyk.network.ProtocolHelper;
import pl.krgr.chinczyk.network.Requests;
import pl.krgr.chinczyk.network.Responses;

public class RollCommand extends AbstractCommand {

	public RollCommand(HandlerCallback callback) {
		super(callback);
	}

	@Override
	public String getRequest() {
		return Requests.ROLL_DICE;
	}
	
	@Override
	public void execute() {
		String[] match = ProtocolHelper.matches(Responses.ROLL_DICE, response);
		if (match.length > 0) {
			callback.commandExecuted(new CallBackEvent(true, response));
		} else {
			callback.commandExecuted(new CallBackEvent(false, response));
		}
	}
}
