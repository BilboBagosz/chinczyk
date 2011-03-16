package pl.krgr.chinczyk.client.control;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.deferred.SetModel;

import pl.krgr.chinczyk.client.network.CallBackEvent;
import pl.krgr.chinczyk.client.network.HandlerCallback;
import pl.krgr.chinczyk.client.network.JoinRoomCommand;
import pl.krgr.chinczyk.client.network.StandUpCommand;
import pl.krgr.chinczyk.client.network.StartGameCommand;
import pl.krgr.chinczyk.client.presentation.ClientState;
import pl.krgr.chinczyk.client.presentation.GameView;
import pl.krgr.chinczyk.client.presentation.Room;
import pl.krgr.chinczyk.control.BoardNotRegisteredException;
import pl.krgr.chinczyk.control.BoardNotValidException;
import pl.krgr.chinczyk.control.GameAlreadyStartedException;
import pl.krgr.chinczyk.control.GameControl;
import pl.krgr.chinczyk.control.NotEnoughPlayersException;
import pl.krgr.chinczyk.control.PlayerAlreadyRegisteredException;
import pl.krgr.chinczyk.control.RequestHandler;
import pl.krgr.chinczyk.model.AbstractCamp;
import pl.krgr.chinczyk.model.Camp;
import pl.krgr.chinczyk.model.Cell;
import pl.krgr.chinczyk.model.IdMapping;
import pl.krgr.chinczyk.model.Player;
import pl.krgr.chinczyk.network.client.Connector;
import pl.krgr.chinczyk.network.client.ConnectorNotConnectedException;

public class GameControlProxy implements GameControl {

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

	private ClientState state;
	private Room room;
	private GameView gameView;
	private String playerName;
	
	public GameControlProxy(ClientState state, Room room) {
		this.state = state;
		this.room = room;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public void setGameView(GameView view) {
		this.gameView = view;
	}
	
	@Override
	public Player[] getPlayers() {
		return players;
	}

	@Override
	public Player addPlayer(String name, Camp camp)	throws BoardNotRegisteredException, GameAlreadyStartedException,
			PlayerAlreadyRegisteredException {
		checkPreconditions();
		JoinHandlerCallback handler = new JoinHandlerCallback(name, camp);		
		JoinRoomCommand joinRoom = new JoinRoomCommand(room.getId(), name, camp, handler); 		
		try {
			Connector connector = state.getConnector();
			connector.handleRequest(joinRoom);
		} catch (ConnectorNotConnectedException e) {
			e.printStackTrace();
			throw new PlayerAlreadyRegisteredException(e);			
		}
		if (handler.player == null) {
			throw new PlayerAlreadyRegisteredException(handler.errorMessage);
		}
		this.playerName = handler.player.getName();
		return handler.player;
	}

	private class JoinHandlerCallback implements HandlerCallback {
		private Player player;
		private String errorMessage;
		private String name;
		private Camp camp;
		
		public JoinHandlerCallback(String name, Camp camp) {
			this.name = name;
			this.camp = camp;
		}
		@Override
		public void commandExecuted(CallBackEvent event) {
			if (!event.getResult()) {
				errorMessage = event.getMessage();
				return;
			}
			player = new Player(name, camp, board);
			players[camp.getPlayerPosition()] = player;
			numberOfPlayers++;
			state.updateRoom((Room) event.getEventStructure());
		}
	}
	
	@Override
	public Player removePlayer(Camp camp) throws GameAlreadyStartedException, BoardNotRegisteredException {
		checkPreconditions();
		
		StandUpHandlerCallback handler = new StandUpHandlerCallback();
		Player toRemove = getPlayer(camp);
		StandUpCommand standUp = new StandUpCommand(room.getId(), toRemove.getName(), handler);
		try {
			Connector connector = state.getConnector();
			connector.handleRequest(standUp);
		} catch (ConnectorNotConnectedException e) {
			e.printStackTrace();
			throw new GameAlreadyStartedException(e);
		}
		
		if (handler.getErrorMessage() != null) {
			throw new GameAlreadyStartedException(handler.getErrorMessage());
		} 
		this.playerName = null;
		Player toRemove2 = null;
		for (int i = 0; i < players.length; i++) {
			toRemove2 = players[i];
			if (toRemove2 != null && toRemove2.getCamp() == camp) {
				players[i] = null;
				toRemove2.clean();
				break;
			}
			toRemove2 = null;
		}
		numberOfPlayers--;
		return toRemove2 == null ? toRemove : toRemove2;
	}

	private Player getPlayer(Camp camp) {
		for (Player player : players) {
			if (player != null && player.getCamp() == camp) {
				return player;
			}
		}
		return null;
	}
	
	private class StandUpHandlerCallback implements HandlerCallback {
		private String errorMessage;
		@Override
		public void commandExecuted(CallBackEvent event) {
			if (!event.getResult()) {
				this.errorMessage = event.getMessage();
				return;
			}
			state.updateRoom((Room) event.getEventStructure());								
		}
		public String getErrorMessage() {
			return errorMessage;
		}
	}
	
	@Override
	public void start() {
//		prestart();
		
		sendStartCommand();
		// TODO Auto-generated method stub

	}

	public void prestart() throws GameAlreadyStartedException,
			BoardNotRegisteredException, NotEnoughPlayersException {
		checkPreconditions();
		if (numberOfPlayers < MINIMUM_NUMBER_OF_PLAYERS) {
			throw new NotEnoughPlayersException();
		}
	}

	private void sendStartCommand() {
		StartGameCommand start = new StartGameCommand(playerName, room.getId(), new HandlerCallback() {
			
			@Override
			public void commandExecuted(CallBackEvent event) {
				if (!event.getResult()) {
					setErrorMessage(event.getMessage());
					return;
				} else {
					setGameResult("Gra rozpoczêta!");
				}
			}
		});
		try {
			state.getConnector().handleRequest(start);
		} catch (ConnectorNotConnectedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void registerBoard(Map<Integer, Cell> board)
			throws BoardNotValidException, GameAlreadyStartedException {
		if (gameStarted) {
			throw new GameAlreadyStartedException();
		}
		if (!validate(board)) {
			throw new BoardNotValidException();
		}
		this.board = board;
		synchronizePlayers(false);
	}

	public void synchronizePlayers(boolean displayMessages) {
		pl.krgr.chinczyk.client.presentation.Player[] ps = room.getPlayers();
		for (int i = 0; i < ps.length; i++) {
			pl.krgr.chinczyk.client.presentation.Player p = ps[i];
			if (p != null) {
				if (players[p.getCamp().getPlayerPosition()] == null) {
					Player player = new Player(p.getName(), p.getCamp(), board);
					players[p.getCamp().getPlayerPosition()] = player;
					numberOfPlayers++;
					if (gameView != null) {
						gameView.setPlayerInfo(player);
						if (displayMessages) {
							setGameResult(player.getName() + " " + sex(player.getName(), "usiad³" + " do sto³u."));
						}
					}
				}
			} else {
				Camp camp = AbstractCamp.fromIndex(i);
				Player player = players[camp.getPlayerPosition()];
				if (player != null) {
					player.clean();
					players[camp.getPlayerPosition()] = null;
					if (displayMessages) {
						setGameResult(player.getName() + " " + sex(player.getName(), "wsta³") + " od sto³u.");
					}
				}
				gameView.clearPlayerInfo(camp);
			}
			
		}
//		for (pl.krgr.chinczyk.client.presentation.Player p : room.getPlayers()) {
//			if (p != null) {
//				if (players[p.getCamp().getPlayerPosition()] == null) {
//					Player player = new Player(p.getName(), p.getCamp(), board);
//					players[p.getCamp().getPlayerPosition()] = player;
//					numberOfPlayers++;
//					if (gameView != null) {
//						gameView.setPlayerInfo(player);
//					}
//				}
//			} else {
//				
//			}
//		}
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

	@Override
	public void setRequestHandler(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	@Override
	public boolean isStarted() {
		return room.isStarted();
	}

	private void checkPreconditions() throws GameAlreadyStartedException, BoardNotRegisteredException {
		if (gameStarted) {
			throw new GameAlreadyStartedException();
		}
		if (board == null) {
			throw new BoardNotRegisteredException();
		}
	}
	private void setGameResult(String gameResult) {
		this.gameResult = gameResult;
		requestHandler.handleResultMessage(this.gameResult);
	}
	private void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		requestHandler.handleErrorMessage(this.errorMessage);
	}
	public static String sex(String name, String string) {
		if ("kuba".equalsIgnoreCase(name)) { // ;) //$NON-NLS-1$
			return string;
		}
		return name.endsWith("a") ? string + "a" : string; //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public void waitUntilStart() {
		// TODO Auto-generated method stub
		
	}

}
