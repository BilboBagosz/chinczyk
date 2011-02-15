package pl.krgr.chinczyk.control;

import pl.krgr.chinczyk.model.Player;

public interface RequestHandler {

	void requestRoll(Player p);
	void handleQueryMessage(String message);
	void handleResultMessage(String message);

}
