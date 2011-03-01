package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.server.NotConnectedException;
import pl.krgr.chinczyk.server.Room;
import pl.krgr.chinczyk.server.Server;

public class NewRoomCommand extends AbstractCommand {

	public NewRoomCommand(Server server, int sessionId) {
		super(server, sessionId);
	}
	
	@Override
	public void execute() {
		try {
			Room room = server.createNewRoom(sessionId);
			response = "OK ";
			response += room.info();
		} catch (NotConnectedException e) {
			response = String.format(Responses.ERROR, "Nie jesteœ pod³¹czony, nie mo¿esz otworzyæ nowego sto³u.");
			e.printStackTrace();
		}
	}
}
