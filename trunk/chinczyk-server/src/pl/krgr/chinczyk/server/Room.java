package pl.krgr.chinczyk.server;

import java.util.LinkedList;
import java.util.List;

import pl.krgr.chinczyk.control.GameControl;
import pl.krgr.chinczyk.model.Player;

public class Room {
	
	private static int generatedId = 0;
	
	private int id;
	private List<Player> players = new LinkedList<Player> ();
	private GameControl control;	
	
	public Room() {
		this.id = nextId();
	}
	
	private static int nextId() {
		return generatedId++;
	}
	
	public void startGame() {
		
	}
}
