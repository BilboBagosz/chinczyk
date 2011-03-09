package pl.krgr.chinczyk.client.presentation;

import pl.krgr.chinczyk.model.Camp;

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
}

class Player {
	
	private String name;
	private Camp camp;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setCamp(Camp camp) {
		this.camp = camp;
	}
	
	public Camp getCamp() {
		return camp;
	}
}