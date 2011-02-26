package pl.krgr.chinczyk.control;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pl.krgr.chinczyk.control.nls.Messages;
import pl.krgr.chinczyk.model.Camp;
import pl.krgr.chinczyk.model.Cell;
import pl.krgr.chinczyk.model.IdMapping;
import pl.krgr.chinczyk.model.Pawn;
import pl.krgr.chinczyk.model.Player;

public class GameControl {
	
	private static final int MINIMUM_NUMBER_OF_PLAYERS = 2;
	private boolean gameStarted = false;
	private Map<Integer, Cell> board;
	private Player[] players = new Player[4];
	private List<Player> places = new LinkedList<Player> ();
	private int numberOfPlayers = 0;
	private String gameQuery;
	private String gameResult;
	private String errorMessage;

	private IdMapping ids = IdMapping.INSTANCE;
	private RequestHandler requestHandler; //interface to be implemented by clients
		
	public boolean addPlayer(String name, Camp camp) throws BoardNotRegisteredException, GameAlreadyStartedException {
		checkPreconditions();
		Player player = new Player(name, camp, board);
		players[camp.getPlayerPosition()] = player;
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

	/**
	 * Starts the game, clients should call this to start a game
	 * 
	 * @throws NotEnoughPlayersException Before game starts there should be at least two players registered
	 * @throws GameAlreadyStartedException
	 * @throws BoardNotRegisteredException Before game starts there should be board registered
	 * @see GameControl#addPlayer(String, Camp)
	 * @see GameControl#registerBoard(Map) 
	 */
	public void start() throws NotEnoughPlayersException, GameAlreadyStartedException, BoardNotRegisteredException {
		checkPreconditions();
		if (numberOfPlayers < MINIMUM_NUMBER_OF_PLAYERS) { 
			throw new NotEnoughPlayersException();
		}
		places.clear();
		setGameResult(Messages.GameControl_PlayersSetSequence);
		gameStarted = true;
		requestHandler.gameStarted();
		Player actualPlayer = selectWhoStartsTheGame(players);
		setGameResult(Messages.GameControl_Starts + actualPlayer.getName());
		
		int playerIndex = indexOf(actualPlayer);
		
		while (!winCondition()) {
			Player player = players[playerIndex];
			if (player != null) {
				move(player);
				if (player.atHome()) {
					if (!places.contains(player)) {
						places.add(player);
						setGameResult(Messages.GameControl_Congratulation + player.getName() + " " + sex(player.getName(), Messages.GameControl_Finished)  //$NON-NLS-2$
								+ Messages.GameControl_GameOn + places.size() + Messages.GameControl_Place);
					}
				}
			player.clearKills();
			}
			playerIndex = ++playerIndex % 4;
		}
		gameEnd();
	}
	
	private boolean winCondition() {
		if (places.size() == numberOfPlayers - 1) { 
			for (Player player : players) { //determine last place
				if (player != null && !player.atHome()) {
					places.add(player); //last place
				}
			}
			return true;
		}
		return false;
	}

	private void move(Player player) {
		if (player == null) return;
		if (player.atHome()) return;
		
		setGameQuery(player.getName() + Messages.GameControl_RollDice);
		requestHandler.requestRoll(player);
		int result = player.rollDice();
		setGameResult(player.getName() + " " + sex(player.getName(), Messages.GameControl_Thrown) + " " + result); //$NON-NLS-1$ //$NON-NLS-3$
		if (player.canMove(result)) {
			player.highlightEnabled(result);
			setGameQuery(player.getName() + Messages.GameControl_YourMove);
			Pawn pawn = requestHandler.requestMove(player, result);
			player.backlightAll();
			player.move(pawn, result);
		} else {
			setErrorMessage(player.getName() + Messages.GameControl_CannotMove);
		}
		if (result == Pawn.PREMIUM_ROLL) {
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
		if (gameStarted) {
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
				setGameQuery(pl.getName() + Messages.GameControl_RollDice);
				requestHandler.requestRoll(pl);			
				int roll = pl.rollDice();
				setGameResult(pl.getName() + " " + sex(pl.getName(), Messages.GameControl_Thrown) + ": " + roll); //$NON-NLS-1$ //$NON-NLS-3$
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
			StringBuilder sb = new StringBuilder(Messages.GameControl_Players);
			for (Player p : ps) {
				if (sb.length() == Messages.GameControl_Players.length()) {
					sb.append(p.getName());					
				} else {
					sb.append(Messages.GameControl_And + p.getName());
				}				
			}
			sb.append(Messages.GameControl_ThrownSameResult + max + Messages.GameControl_ExtraThrow);
			setGameResult(sb.toString());
			return selectWhoStartsTheGame(ps.toArray(new Player[ps.size()]));
		}
	}

	public static String sex(String name, String string) {
		if ("kuba".equalsIgnoreCase(name)) { // ;) //$NON-NLS-1$
			return string;
		}
		return name.endsWith("a") ? string + "a" : string; //$NON-NLS-1$ //$NON-NLS-2$
	}

	private boolean gameEnd() {
		gameStarted = false;
		setGameResult(Messages.GameControl_GameEnd);
		requestHandler.gameEnded(places);
		return true;
	}

	public void registerBoard(Map<Integer, Cell> board) throws BoardNotValidException, GameAlreadyStartedException {
		if (gameStarted) {
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
		return gameStarted;
	}

	private void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		requestHandler.handleErrorMessage(this.errorMessage);
	}

}
