package pl.krgr.chinczyk.control;

import java.util.List;

import pl.krgr.chinczyk.model.Pawn;
import pl.krgr.chinczyk.model.Player;

public interface RequestHandler {

	void requestRoll(Player p);
	void handleQueryMessage(String message);
	void handleResultMessage(String message);
	void handleErrorMessage(String message);
	void gameStarted();
	void gameEnded(List<Player> places);
	Pawn requestMove(Player player, int movement);
	void move(Player player, Pawn pawn, int result);
	void clearKills(Player player);
}
