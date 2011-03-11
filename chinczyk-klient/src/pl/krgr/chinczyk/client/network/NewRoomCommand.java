package pl.krgr.chinczyk.client.network;

import pl.krgr.chinczyk.client.presentation.Room;
import pl.krgr.chinczyk.network.ProtocolHelper;
import pl.krgr.chinczyk.network.Requests;
import pl.krgr.chinczyk.network.Responses;

public class NewRoomCommand extends AbstractCommand {

	public NewRoomCommand(HandlerCallback callback) {
		super(callback);
	}

	@Override
	public String getRequest() {
		return Requests.NEW_ROOM;
	}

	@Override
	public void execute() {
		String[] match = ProtocolHelper.matches(Responses.NEW_ROOM, response);
		if (match.length != 10) {
			callback.commandExecuted(new CallBackEvent(false, response));
		} else {
			Room room = Room.fromStrings(match);
			callback.commandExecuted(new CallBackEvent(true, response, room));
		}		
	}
}
