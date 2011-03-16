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
	private Map<Integer, Cell> board;
	private boolean ready = false;
	
	public Player(String name, Camp camp, Map<Integer, Cell> boardMap) {
		this.name = name;
		this.camp = camp;
		this.board = boardMap;
		addPawns(camp, boardMap);
	}
	
	private void addPawns(Camp camp, Map<Integer, Cell> boardMap) {
		for (int i = 0; i < 4; i++) {
			pawns.add(new Pawn(boardMap, camp));
		}
	}
	
	public boolean atHome() {
		for (int cellIndex : camp.getHomeCells()) {
			if (board.get(cellIndex).isFree()) {
				return false;
			}
		}
		return true;
	}
	
	public boolean canMove(int movement) {
		for (Pawn pawn : pawns) {
			if (pawn.canMove(movement)) {
				return true;
			}
		}
		return false;
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

	public void highlightMove(int movement) {
		for (Pawn pawn : pawns) {
			pawn.highlightRoad(movement);
		}
	}
	
	public void highlightEnabled(int movement) {
		for (Pawn pawn : pawns) {
			pawn.highlightMe(movement);
		}
	}

	public void backlightAll() {
		for (Pawn pawn : pawns) {
			pawn.backlightMe();
		}
	}
	
	@Override
	public String toString() {
		return "Player [name=" + name + ", camp=" + camp + "]";
	}

	public boolean containPawn(Pawn pawn) {
		for (Pawn p : pawns) {
			if (p == pawn) {
				return true;
			}
		}		
		return false;
	}
	
	public void move(Pawn pawn, int result) {
		if (containPawn(pawn)) {
			pawn.move(result);
			return;
		}
		throw new IllegalPawnException();
	}
	
	public void clearKills() {
		for (Pawn pawn : pawns) {
			pawn.clearKill();
		}
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public boolean isReady() {
		return ready;
	}
}
