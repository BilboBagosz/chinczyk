package pl.krgr.chinczyk.server;

import pl.krgr.chinczyk.model.Player;

public class Session {

	private int sessionId;
	private Player player;
	private Room room;
	
	public Session(int sessionId) {
		this(sessionId, null, null);
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
		this.room = room;
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
	
	public Room getRoom() {
		return room;
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
}
