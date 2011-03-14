package pl.krgr.chinczyk.client.network;

import pl.krgr.chinczyk.client.presentation.ClientState;
import pl.krgr.chinczyk.client.presentation.Room;
import pl.krgr.chinczyk.network.notifications.ClientNotification;

public class RoomChangedNotification implements ClientNotification {

	private String[] roomInfo;
	private ClientState clientState;
	
	public RoomChangedNotification(ClientState state, String[] roomInfo) {
		this.roomInfo = roomInfo;
		this.clientState = state;
	}
		
	@Override
	public void executeNotification() {
		clientState.updateRoom(Room.fromStrings(roomInfo));
	}
}
