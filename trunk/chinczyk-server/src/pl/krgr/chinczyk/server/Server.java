package pl.krgr.chinczyk.server;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import pl.krgr.chinczyk.model.Player;

public class Server {

	private List<Room> rooms = new LinkedList<Room> ();
	private Set<Player> players = new HashSet<Player> ();
	
	public void connectPlayer(Player player) {
		players.add(player);
	}
	
	public void disconnectPlayer(Player player) {
		players.remove(player);
	}
	
	public synchronized Room createNewRoom() {
		Room room = new Room();
		rooms.add(room);
		return room;
	}
	
	public void closeRoom(Room room) {		
	}
	
	public Room openRoom(int roomId) {
		return null;
	}
	
}
