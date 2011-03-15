package pl.krgr.chinczyk.client.network;

import pl.krgr.chinczyk.network.ProtocolHelper;
import pl.krgr.chinczyk.network.Requests;
import pl.krgr.chinczyk.network.Responses;

public class OpenRoomCommand extends AbstractCommand {

	private int roomId;
		
	public OpenRoomCommand(int roomId, HandlerCallback callback) {
		super(callback);
		this.roomId = roomId;
	}

	@Override
	public String getRequest() {
		return String.format(Requests.OPEN_ROOM, roomId);
	}

	@Override
	public void execute() {
		String[] match = ProtocolHelper.matches(Responses.OPEN_ROOM, response);
		if (match.length > 0) {
			callback.commandExecuted(new CallBackEvent(true, response, match));
		} else {
			callback.commandExecuted(new CallBackEvent(false, response));
		}
	}
}
