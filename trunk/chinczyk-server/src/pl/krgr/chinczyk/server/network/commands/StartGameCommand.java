package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.server.Server;

public class StartGameCommand extends AbstractServerCommand {

	private final String playerName;
	private final int roomId;

	public StartGameCommand(Server server, int sessionId, String playerName, int roomId) {
		super(server, sessionId);
		this.playerName = playerName;
		this.roomId = roomId;
	}

	@Override
	public void execute() {
		response = server.startGame(sessionId, playerName, roomId);
	}

}
