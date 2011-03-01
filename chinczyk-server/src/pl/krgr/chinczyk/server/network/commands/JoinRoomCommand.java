package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.server.Server;

public class JoinRoomCommand extends AbstractCommand {

	public JoinRoomCommand(Server server, int sessionId) {
		super(server, sessionId);
	}

	@Override
	public void execute() {
	}

}
