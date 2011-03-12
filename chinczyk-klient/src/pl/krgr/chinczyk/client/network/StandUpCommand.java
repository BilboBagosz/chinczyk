package pl.krgr.chinczyk.client.network;

import pl.krgr.chinczyk.client.presentation.Room;
import pl.krgr.chinczyk.network.ProtocolHelper;
import pl.krgr.chinczyk.network.Requests;
import pl.krgr.chinczyk.network.Responses;

public class StandUpCommand extends AbstractCommand {

	private int roomId;

	public StandUpCommand(int roomId, HandlerCallback callback) {
		super(callback);
		this.roomId = roomId;
	}

	@Override
	public String getRequest() {
		return String.format(Requests.STAND_UP, roomId);
	}
	
	public void execute() {
		String[] match = ProtocolHelper.matches(Responses.STAND_UP, response);
		if (match.length == 10) {
			Room room = Room.fromStrings(match);
			callback.commandExecuted(new CallBackEvent(true, response, room));
		} else {
			callback.commandExecuted(new CallBackEvent(false, response));
		}		
	}
}
