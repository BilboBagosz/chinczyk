package pl.krgr.chinczyk.server.network.commands;

import java.util.List;

import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.server.NotConnectedException;
import pl.krgr.chinczyk.server.Server;

public class GetRoomsCommand extends AbstractServerCommand {

	public GetRoomsCommand(Server server, int sessionId) {
		super(server, sessionId);
	}

	@Override
	public void execute() {
		try {
			List<String> roomsInfo = server.getRooms(sessionId);
			StringBuilder buildResponse = new StringBuilder();
			for (String roomInfo : roomsInfo) {
				if (buildResponse.length() > 0) {
					buildResponse.append(Responses.LIST_SEPARATOR);
				}
				buildResponse.append(roomInfo);
			}
			response = String.format(Responses.GET_ROOMS, buildResponse.toString());
		} catch (NotConnectedException e) {
			e.printStackTrace();
			response = String.format(Responses.ERROR, "PlayerNotConnected");			
		}
	}

}
