package pl.krgr.chinczyk.control;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pl.krgr.chinczyk.model.Camp;
import pl.krgr.chinczyk.model.Cell;
import pl.krgr.chinczyk.model.IdMapping;
import pl.krgr.chinczyk.model.Pawn;
import pl.krgr.chinczyk.model.Player;

public class GameControl {
	
	private boolean started = false;
	private Map<Integer, Cell> board;
	private Player[] players = new Player[4];
	private List<Player> places = new LinkedList<Player> ();
	private int numberOfPlayers = 0;
	private String gameQuery;
	private String gameResult;
	private String errorMessage;

	private IdMapping ids = IdMapping.INSTANCE;

	private RequestHandler requestHandler;
		
	public boolean addPlayer(String name, Camp camp) throws BoardNotRegisteredException, GameAlreadyStartedException {
		checkPreconditions();
		Player player = new Player(name, camp, board);
		players[camp.getPriority()] = player;
		numberOfPlayers++;
		return true;
	}
	
	public Player removePlayer(Camp camp) throws GameAlreadyStartedException, BoardNotRegisteredException {
		checkPreconditions();
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
	
	public void start() throws NotEnoughPlayersException, GameAlreadyStartedException, BoardNotRegisteredException {
		checkPreconditions();
		if (numberOfPlayers < 2) { 
			throw new NotEnoughPlayersException();
		}
		places.clear();
		setGameResult("Gra rozpoczêta, gracze ustalaj¹ kolejnoœæ rzucaj¹c po kolei kostk¹.");
		started = true;
		requestHandler.gameStarted();
		Player actualPlayer = selectWhoStartsTheGame(players);
		setGameResult("Zaczyna " + actualPlayer.getName());
		
		int playerIndex = indexOf(actualPlayer);
		
		while (!winCondition()) {
			Player player = players[playerIndex]; 
			move(player);
			if (!player.notAtHome()) {
				if (!places.contains(player)) {
					places.add(players[playerIndex]);
					setGameResult("Gratulacje! " + player.getName() + " " + sex(player.getName(), "ukoñczy³") 
							+ " grê na" + places.size() + " miejscu!");
				}
			}
			player.clearKills();
			playerIndex = ++playerIndex % 4;
		}
		gameEnd();
	}
	
	private boolean winCondition() {
		if (places.size() == numberOfPlayers - 1) { 
			for (Player player : players) { //determine last place
				if (player.notAtHome()) {
					places.add(player); //last place
				}
			}
			return true;
		}
		return false;
	}

	private void move(Player player) {
		if (player == null) return;
		if (!player.notAtHome()) return;
		
		setGameQuery(player.getName() + ", rzuæ kostk¹.");
		requestHandler.requestRoll(player);
		int result = player.rollDice();
		setGameResult(player.getName() + " " + sex(player.getName(), "wyrzuci³") + " " + result);
		if (player.canMove(result)) {
			player.highlightEnabled(result);
			setGameQuery(player.getName() + ", Twój ruch.");
			Pawn pawn = requestHandler.requestMove(player, result);
			player.backlightAll();
			player.move(pawn, result);
		} else {
			setErrorMessage(player.getName() + ", Brak mo¿liwoœci ruchu.");
		}
		if (result == 6) {
			move(player); //move again :)
		}
	}
	
	private int indexOf(Player player) {
		for (int i = 0; i < players.length; i++) {
			if (player != null && player == players[i]) {
				return i;
			}
		}
		return -1;
	}

	private void checkPreconditions() throws GameAlreadyStartedException, BoardNotRegisteredException {
		if (started) {
			throw new GameAlreadyStartedException();
		}
		if (board == null) {
			throw new BoardNotRegisteredException();
		}
	}
	
	private Player selectWhoStartsTheGame(Player[] players) {

		List<Player> ps = new ArrayList<Player>();
		int max = -1;				
		
		for (Player pl : players) {
			if (pl != null) {
				setGameQuery(pl.getName() + ", rzuæ kostk¹.");
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
			StringBuilder sb = new StringBuilder("Gracze: ");
			for (Player p : ps) {
				if (sb.length() == "Gracze: ".length()) {
					sb.append(p.getName());					
				} else {
					sb.append(" i " + p.getName());
				}				
			}
			sb.append(" rzucili tak¹ sam¹ liczbê oczek = " + max + ", potrzebna jest dogrywka!");
			setGameResult(sb.toString());
			return selectWhoStartsTheGame(ps.toArray(new Player[ps.size()]));
		}
	}

	public static String sex(String name, String string) {
		if ("kuba".equalsIgnoreCase(name)) { // ;)
			return string;
		}
		return name.endsWith("a") ? string + "a" : string;
	}

	private boolean gameEnd() {
		started = false;
		setGameResult("Gra zakoñczona!");
		requestHandler.gameEnded(places);
		return true;
	}

	public void registerBoard(Map<Integer, Cell> board) throws BoardNotValidException, GameAlreadyStartedException {
		if (started) {
			throw new GameAlreadyStartedException();
		}
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

	private void setGameQuery(String gameQuery) {
		this.gameQuery = gameQuery;
		requestHandler.handleQueryMessage(this.gameQuery);
	}

	private void setGameResult(String gameResult) {
		this.gameResult = gameResult;
		requestHandler.handleResultMessage(this.gameResult);
	}

	public void setRequestHandler(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	public boolean isStarted() {
		return started;
	}

	private void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		requestHandler.handleErrorMessage(this.errorMessage);
	}

}
