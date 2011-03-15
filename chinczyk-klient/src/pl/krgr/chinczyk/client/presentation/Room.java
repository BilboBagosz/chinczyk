package pl.krgr.chinczyk.client.presentation;

import pl.krgr.chinczyk.model.AbstractCamp;
import pl.krgr.chinczyk.network.ProtocolHelper;
import pl.krgr.chinczyk.network.Responses;

public class Room {

	private int id;
	private Player[] players = new Player[4];
	private boolean started = false;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Player[] getPlayers() {
		return players;
	}
	
	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public boolean isStarted() {
		return started;
	}
	
	public static Room fromString(String roomInfo) {
		String[] infos = ProtocolHelper.matches(Responses.ROOM_INFO, roomInfo);
		return fromStrings(infos);
	}
	
	public static Room fromStrings(String[] infos) {
		Room room = null;
		if (infos.length == 10) {
			room = new Room();
			room.setId(Integer.parseInt(infos[0]));
			for (int i = 0; i < 4; i++) {
				if (!infos[1+i*2].equals("null") && !infos[2+i*2].equals("null")) {
					room.players[i] = new Player();
					room.players[i].setName(infos[1+i*2]);
					room.players[i].setCamp(AbstractCamp.fromString(infos[2+i*2]));
				}
			}
			room.setStarted(Boolean.parseBoolean(infos[9]));
		}		
		return room;
	}
	
}