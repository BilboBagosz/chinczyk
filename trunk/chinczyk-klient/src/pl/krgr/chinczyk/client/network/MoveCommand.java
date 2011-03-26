package pl.krgr.chinczyk.client.network;

import pl.krgr.chinczyk.network.Requests;

public class MoveCommand extends AbstractCommand {

	private final int pawnPosition;

	public MoveCommand(int pawnPosition, HandlerCallback callback) {
		super(callback);
		this.pawnPosition = pawnPosition;
	}

	@Override
	public String getRequest() {
		return String.format(Requests.MOVE, pawnPosition);
	}

}
