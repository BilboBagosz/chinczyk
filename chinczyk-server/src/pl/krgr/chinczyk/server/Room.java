package pl.krgr.chinczyk.server;

import pl.krgr.chinczyk.control.GameControl;
import pl.krgr.chinczyk.model.Player;
import pl.krgr.chinczyk.network.Responses;

public class Room {
	
	private static int generatedId = 0;
	
	private int id;
	private Player[] players = new Player[4];
	private GameControl control = new GameControl();
	
	public Room() {
		this.id = nextId();
	}
	
	private static int nextId() {
		return generatedId++;
	}
	
	public void startGame() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public Player[] getPlayers() {
		return players;
	}

	public String info() {
		Object[] args = new Object[10];
		int index = 0;
		args[index++] = id;
		
		for (Player player : players) {
			if (player == null) {
				args[index++] = null; //player name
				args[index++] = null; //player camp
			} else {
				args[index++] = player.getName();
				args[index++] = player.getCamp();
			}			
		}
		args[index++] = control.isStarted();
		return String.format(Responses.ROOM_INFO, args);
		//Responses.ROOM_INFO;
	}
}
