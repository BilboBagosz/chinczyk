package pl.krgr.chinczyk.server;

import pl.krgr.chinczyk.control.GameControl;
import pl.krgr.chinczyk.model.Player;

public class Room {
	
	private static int generatedId = 0;
	
	private int id;
	private Player[] players = new Player[4];
	private GameControl control;
	
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

}
