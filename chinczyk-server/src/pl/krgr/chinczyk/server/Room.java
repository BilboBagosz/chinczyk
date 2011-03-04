package pl.krgr.chinczyk.server;

import java.util.HashMap;
import java.util.Map;

import pl.krgr.chinczyk.control.BoardNotRegisteredException;
import pl.krgr.chinczyk.control.BoardNotValidException;
import pl.krgr.chinczyk.control.GameAlreadyStartedException;
import pl.krgr.chinczyk.control.GameControl;
import pl.krgr.chinczyk.control.PlayerAlreadyRegisteredException;
import pl.krgr.chinczyk.model.Camp;
import pl.krgr.chinczyk.model.Cell;
import pl.krgr.chinczyk.model.IdMapping;
import pl.krgr.chinczyk.model.Pawn;
import pl.krgr.chinczyk.model.Player;
import pl.krgr.chinczyk.network.Responses;

public class Room {
	
	private static int generatedId = 0;
	private Map<Integer, Cell> board = new HashMap<Integer, Cell>();
	private int id;
	private GameControl control = new GameControl();
	
	public Room() {
		this.id = nextId();
		try {
			prepareBoard();
			control.registerBoard(board);
		} catch (BoardNotValidException e) {
			throw new RuntimeException(e);
		} catch (GameAlreadyStartedException e) {
			throw new RuntimeException(e);
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
	
	public Player addPlayer(String name, Camp camp) throws BoardNotRegisteredException, GameAlreadyStartedException, PlayerAlreadyRegisteredException {
		return control.addPlayer(name, camp);
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
		IdMapping.INSTANCE.reset();
		addCells(72);
	}		
}
