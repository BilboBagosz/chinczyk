package pl.krgr.chinczyk.client.network;

import java.util.LinkedList;
import java.util.List;

import pl.krgr.chinczyk.client.presentation.Room;
import pl.krgr.chinczyk.network.ProtocolHelper;
import pl.krgr.chinczyk.network.Requests;
import pl.krgr.chinczyk.network.Responses;

public class GetRoomsCommand extends AbstractCommand {

	public GetRoomsCommand(HandlerCallback callback) {
		super(callback);
	}

	@Override
	public String getRequest() {
		return Requests.GET_ROOMS;
	}

	@Override
	public void execute() {
		String match[] = ProtocolHelper.matches(Responses.GET_ROOMS, response);
		List<Room> rooms = new LinkedList<Room>();
		if (match.length == 1) { //ok
			String[] roomsInfo = match[0].split(Responses.LIST_SEPARATOR);
			for (String roomInfo : roomsInfo) {
				Room room = Room.fromString(roomInfo);
				if (room != null) {
					rooms.add(room);
				}
			}
			callback.commandExecuted(new CallBackEvent(true, response, rooms));			
		} else {
			callback.commandExecuted(new CallBackEvent(false, response));
		}
	}
	
}
