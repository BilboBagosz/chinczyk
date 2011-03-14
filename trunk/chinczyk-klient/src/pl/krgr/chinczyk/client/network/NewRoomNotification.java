package pl.krgr.chinczyk.client.network;

import pl.krgr.chinczyk.client.presentation.ClientState;
import pl.krgr.chinczyk.client.presentation.Room;
import pl.krgr.chinczyk.network.notifications.ClientNotification;

public class NewRoomNotification implements ClientNotification {

	private String[] roomInfo;
	private ClientState clientState;
	
	public NewRoomNotification(ClientState state, String[] roomInfo) {
		this.roomInfo = roomInfo;
		this.clientState = state;
	}
	
	@Override
	public void executeNotification() {
		clientState.addRoom(Room.fromStrings(roomInfo));
	}

}
