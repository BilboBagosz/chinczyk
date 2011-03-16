package pl.krgr.chinczyk.client.network;

import pl.krgr.chinczyk.network.ProtocolHelper;
import pl.krgr.chinczyk.network.Requests;
import pl.krgr.chinczyk.network.Responses;

public class StartGameCommand extends AbstractCommand {

	private final String playerName;
	private final int roomId;

	public StartGameCommand(String playerName, int roomId, HandlerCallback callback) {
		super(callback);
		this.playerName = playerName;
		this.roomId = roomId;
	}

	@Override
	public String getRequest() {
		return String.format(Requests.START_GAME, playerName, roomId);
	}

	public void execute() {
		String[] match = ProtocolHelper.matches(Responses.START_GAME, response);
		if (match.length > 0) {
			callback.commandExecuted(new CallBackEvent(true, response));
		} else {
			callback.commandExecuted(new CallBackEvent(false, response));
		}		
	}

}
