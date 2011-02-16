package pl.krgr.chinczyk.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pl.krgr.chinczyk.model.Camp;
import pl.krgr.chinczyk.model.Cell;
import pl.krgr.chinczyk.model.IdMapping;
import pl.krgr.chinczyk.model.Player;

public class GameControl {
	
	private boolean started = false;
	public boolean isStarted() {
		return started;
	}

	private Map<Integer, Cell> board;
	private Player[] players = new Player[4];
	private int numberOfPlayers = 0;
	private String gameQuery;
	private String gameResult;
	private String errorMessage;

	private IdMapping ids = IdMapping.INSTANCE;
	private Player actualPlayer;

	private RequestHandler requestHandler;
		
	public boolean addPlayer(String name, Camp camp) throws BoardNotRegisteredException {
		if (board == null) {
			throw new BoardNotRegisteredException();
		}
		Player player = new Player(name, camp, board);
		players[camp.getPriority()] = player;
		numberOfPlayers++;
		return true;
	}
	
	public Player removePlayer(Camp camp) {
		Player toRemove = null;
		for (int i = 0; i < players.length; i++) {
			toRemove = players[i];
			if (toRemove != null && toRemove.getCamp() == camp) {
				players[i] = null;
				toRemove.clean();
				break;
			}
			toRemove = null;
		}
		numberOfPlayers--;
		return toRemove;
	}
	
	public void start() throws NotEnoughPlayersException, GameAlreadyStartedException {
		if (started) {
			throw new GameAlreadyStartedException();
		}
		if (numberOfPlayers == 0) { 
			throw new NotEnoughPlayersException();
		}
		setGameResult("Gra rozpoczêta, gracze ustalaj¹ kolejnoœæ rzucaj¹c po kolei kostk¹.");
		started = true;
		actualPlayer = selectWhoStartsTheGame(players);
		setGameResult("Zaczyna " + actualPlayer.getName());
		while (!gameEnd()) {
			move(actualPlayer);
		}
	}
	
	private Player selectWhoStartsTheGame(Player[] players) {
				
		List<Player> ps = new ArrayList<Player>();
		int max = -1;				
		
		for (Player pl : players) {
			if (pl != null) {
				setGameQuery(pl.getName() + " rzuæ kostk¹");
				requestHandler.requestRoll(pl);			
				int roll = pl.rollDice();
				setGameResult(pl.getName() + " " + sex(pl.getName(), "wyrzuci³") + ": " + roll);
				if (roll > max) {
					max = roll;
					ps.clear();
					ps.add(pl);
				} else if (roll == max) {
					ps.add(pl);
				}
			}
		}
		if (ps.size() == 1) {
			return ps.get(0);
		} else {
			StringBuilder sb = new StringBuilder("Gracze:");
			for (Player p : ps) {
				sb.append(" " + p.getName());
			}
			sb.append("rzucili tak¹ sam¹ liczbê oczek = " + max + " potrzebna jest dogrywka!");
			setGameResult(sb.toString());
			return selectWhoStartsTheGame(ps.toArray(new Player[ps.size()]));
		}
	}

	public static String sex(String name, String string) {
		return name.endsWith("a") ? string + "a" : string;
	}

	private void move(Player actualPlayer2) {
	}

	private boolean gameEnd() {
		started = false;
		return true;
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

	public void setRequestHandler(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		requestHandler.handleErrorMessage(this.errorMessage);
	}

}
