package pl.krgr.chinczyk.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Player {

	private String name;
	private Camp camp;
	private List<Pawn> pawns = new LinkedList<Pawn> ();
	private int lastThrow = -1;
	
	public Player(String name, Camp camp, Map<Integer, Cell> boardMap) {
		this.name = name;
		this.camp = camp;
		for (int i = 0; i < 4; i++) {
			pawns.add(new Pawn(boardMap, camp));
		}
	}
	
	public int rollDice() {
		Random rand = new Random();
		lastThrow = rand.nextInt(6) + 1; 
		return lastThrow;
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

	public void clean() {
		for (Pawn pawn : pawns) {
			pawn.clean();
		}
	}

	public int getLastThrow() {
		return lastThrow;
	}
}
