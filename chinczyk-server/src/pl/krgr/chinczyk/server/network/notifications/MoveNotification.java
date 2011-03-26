package pl.krgr.chinczyk.server.network.notifications;

import pl.krgr.chinczyk.model.Pawn;
import pl.krgr.chinczyk.model.Player;
import pl.krgr.chinczyk.network.Notifications;
import pl.krgr.chinczyk.network.notifications.ServerNotification;

public class MoveNotification implements ServerNotification {

	private final Player player;
	private final Pawn pawn;
	private final int result;

	public MoveNotification(Player player, Pawn pawn, int result) {
		this.player = player;
		this.pawn = pawn;
		this.result = result;
	}

	@Override
	public String getNotification() {
		return String.format(Notifications.MOVE, player.getCamp().toString(), pawn.getActualPosition(), result);
	}

}
