package pl.krgr.chinczyk.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pl.krgr.chinczyk.model.Camp;
import pl.krgr.chinczyk.model.Cell;
import pl.krgr.chinczyk.model.IdMapping;
import pl.krgr.chinczyk.model.Player;

public class GameControl {
	
	private Map<Integer, Cell> board;
	private List<Player> players = new ArrayList<Player> ();
	private String gameQuery;
	private String gameResult;

	private IdMapping ids = IdMapping.INSTANCE;
	private Player actualPlayer;

	private RequestHandler requestHandler;
	
	public boolean addPlayer(String name, Camp camp) throws BoardNotRegisteredException, PlayerAlreadyExistsForCamp {
		if (board == null) {
			throw new BoardNotRegisteredException();
		}
		Player player = new Player(name, camp, board);
		players.set(camp.getPriority(), player);
		return true;
	}
	
	public void start() {
		actualPlayer = selectWhoStartsTheGame(players);
		while (!gameEnd()) {
			move(actualPlayer);
		}
	}
	
	private Player selectWhoStartsTheGame(List<Player> players) {
		
		Player p = null;
		int max = -1;				
		
		for (Player pl : players) {
			setGameQuery(pl.getName() + " rzuæ kostk¹");
			requestHandler.requestRoll(pl);			
			int roll = pl.rollDice();
			setGameResult(pl.getName() + " " + sex(pl.getName(), "wyrzuci³") + ": " + roll);
			if (roll > max) {
				max = roll;
				p = pl;
			} else if (roll == max) {
				
			}
		}		
		return p;
	}

	public static String sex(String name, String string) {
		return name.endsWith("a") ? string + "a" : string;
	}

	private void move(Player actualPlayer2) {
	}

	private boolean gameEnd() {
		return false;
	}

	public void registerBoard(Map<Integer, Cell> board) throws BoardNotValidException {
		if (!validate(board)) {
			throw new BoardNotValidException();
		}
		this.board = board;
	}

	private boolean validate(Map<Integer, Cell> board) {
		for (int id : ids.getMapping()) {
			Cell cell = board.get(id);
			if (cell == null || cell.getId() != id) {
				return false;
			}
		}
		return true;
	}

	public void setGameQuery(String gameQuery) {
		this.gameQuery = gameQuery;
		requestHandler.handleQueryMessage(this.gameQuery);
	}

	public void setGameResult(String gameResult) {
		this.gameResult = gameResult;
		requestHandler.handleResultMessage(this.gameResult);
	}

}
