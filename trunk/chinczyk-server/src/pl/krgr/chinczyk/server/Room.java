package pl.krgr.chinczyk.server;

import java.util.Map;

import pl.krgr.chinczyk.control.BoardNotValidException;
import pl.krgr.chinczyk.control.GameAlreadyStartedException;
import pl.krgr.chinczyk.control.GameControl;
import pl.krgr.chinczyk.model.Cell;
import pl.krgr.chinczyk.model.IdMapping;
import pl.krgr.chinczyk.model.Pawn;
import pl.krgr.chinczyk.model.Player;
import pl.krgr.chinczyk.network.Responses;

public class Room {
	
	private static int generatedId = 0;
	private Map<Integer, Cell> board;
	private int id;
	private GameControl control = new GameControl();
	
	public Room() {
		this.id = nextId();
		try {
			prepareBoard();
			control.registerBoard(board);
		} catch (BoardNotValidException e) {
			e.printStackTrace();
		} catch (GameAlreadyStartedException e) {
			e.printStackTrace();
		}
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
		return control.getPlayers();
	}

	public String info() {
		Object[] args = new Object[10];
		int index = 0;
		args[index++] = id;
		
		for (Player player : getPlayers()) {
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
	
	public boolean addPlayer(String playerInfo) {
		return false;
	}
	
	private void addCell(Pawn pawn) {
		Cell cell = new BoardCell();		
		int id = IdMapping.INSTANCE.getActualValue();
		cell.setId(id);
		board.put(id, cell);
		if (pawn != null) {
			cell.setPawn(pawn);
			pawn.setBaseCell(cell);
		}
	}
	
	private void addCells(int number) {
		for (int i = 0; i < number; i++) {
			addCell(null);
		}
	}		
	
	private void prepareBoard() {
		addCells(71);
	}		
}
