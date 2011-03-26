package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.server.Server;
import pl.krgr.chinczyk.server.WrongPawnException;

public class MoveCommand extends AbstractServerCommand {

	private final int pawnPosition;

	public MoveCommand(Server server, int sessionId, int pawnPosition) {
		super(server, sessionId);
		this.pawnPosition = pawnPosition;
	}

	@Override
	public void execute() {
		try {
			server.move(sessionId, pawnPosition);
			response = Responses.MOVE;
		} catch (WrongPawnException e) {
			response = String.format(Responses.ERROR, e.getMessage());
			e.printStackTrace();
		}
	}
}
