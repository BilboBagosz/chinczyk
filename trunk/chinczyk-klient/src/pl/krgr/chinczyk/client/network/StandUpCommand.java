package pl.krgr.chinczyk.client.network;

import pl.krgr.chinczyk.client.presentation.Room;
import pl.krgr.chinczyk.network.ProtocolHelper;
import pl.krgr.chinczyk.network.Requests;
import pl.krgr.chinczyk.network.Responses;

public class StandUpCommand extends AbstractCommand {

	private int roomId;
	private String playerName;

	public StandUpCommand(int roomId, String playerName, HandlerCallback callback) {
		super(callback);
		this.roomId = roomId;
		this.playerName = playerName;
	}

	@Override
	public String getRequest() {
		return String.format(Requests.STAND_UP, roomId, playerName);
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
