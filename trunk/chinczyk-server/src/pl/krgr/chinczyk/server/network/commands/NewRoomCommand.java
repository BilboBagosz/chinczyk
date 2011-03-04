package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.server.NotConnectedException;
import pl.krgr.chinczyk.server.Server;

public class NewRoomCommand extends AbstractServerCommand {

	public NewRoomCommand(Server server, int sessionId) {
		super(server, sessionId);
	}
	
	@Override
	public void execute() {
		try {
			response = "OK ";
			response += server.createNewRoom(sessionId);
		} catch (NotConnectedException e) {
			response = String.format(Responses.ERROR, "Nie jesteœ pod³¹czony, nie mo¿esz otworzyæ nowego sto³u.");
			e.printStackTrace();
		}
	}
}
