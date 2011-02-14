package pl.krgr.chinczyk.model;

import java.util.Random;

public class Player {

	private String name;
	private Camp camp;
	
	public Player(String name, Camp camp) {
		this.name = name;
		this.camp = camp;
	}
	
	public int rollDice() {
		Random rand = new Random();
		return rand.nextInt(6) + 1;
	}

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
