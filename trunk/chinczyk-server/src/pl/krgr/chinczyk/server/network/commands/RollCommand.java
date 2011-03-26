package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.server.Server;

public class RollCommand extends AbstractServerCommand {

	public RollCommand(Server server, int sessionId) {
		super(server, sessionId);
	}

	@Override
	public void execute() {
		server.rollDice(sessionId);
		response = String.format(Responses.ROLL_DICE, 0);
	}

}
