package pl.krgr.chinczyk.server;

import java.util.LinkedList;
import java.util.List;

import pl.krgr.chinczyk.control.BoardNotRegisteredException;
import pl.krgr.chinczyk.control.GameAlreadyStartedException;
import pl.krgr.chinczyk.model.Player;

public class Session {

	private int sessionId;
	private Player player;
	private List<Room> rooms = new LinkedList<Room> ();
	private Server server;
	
	public Session(int sessionId, Server server) {
		this(sessionId, null, null);
		this.server = server;
	}
	
	public Session(int sessionId, Player player) {
		this(sessionId, player, null);
	}
	
	public Session(int sessionId, Room room) {
		this(sessionId, null, room);		
	}
	
	public Session(int sessionId, Player player, Room room) {
		this.sessionId = sessionId;
		this.player = player;
		if (room != null) {
			addRoom(room);
		}
	}

	public void unregister() throws GameAlreadyStartedException {
		for (Room room : rooms) {
			try {
				room.removePlayer(player.getCamp());
				((ServerImpl)server).notifyRoomChanged(sessionId, room);
			} catch (BoardNotRegisteredException e) {
				e.printStackTrace();
			}
		}
		rooms.clear();
		rooms = null;
		player = null;
	}
	
	public int getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public List<Room> getRooms() {
		return rooms;
	}
	
	public Room getRoom(int roomId) {
		for (Room room : rooms) {
			if (room.getId() == roomId) {
				return room;
			}
		}
		return null;
	}
	
	public void addRoom(Room room) {
		rooms.add(room);
	}
	
	public void removeRoom(Room room) {
		rooms.remove(room);
	}
	
	public void removeRoom(int roomId) {
		Room toRemove = getRoom(roomId);
		rooms.remove(toRemove);
	}
}
