package pl.krgr.chinczyk.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.krgr.chinczyk.control.BoardNotRegisteredException;
import pl.krgr.chinczyk.control.BoardNotValidException;
import pl.krgr.chinczyk.control.GameAlreadyStartedException;
import pl.krgr.chinczyk.control.GameControl;
import pl.krgr.chinczyk.control.GameControlImpl;
import pl.krgr.chinczyk.control.NotEnoughPlayersException;
import pl.krgr.chinczyk.control.PlayerAlreadyRegisteredException;
import pl.krgr.chinczyk.control.PlayerNotReadyException;
import pl.krgr.chinczyk.control.RequestHandler;
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
	private GameControl control = new GameControlImpl(false);
	private final Server server;
	
	private Object lock = new Object();
	public boolean playerRolled = false;
	public boolean playerMoved = false;
	private int pawnPosition;
	
	public Room(Server server) {
		this.server = server;
		this.id = nextId();
		try {
			prepareBoard();
			control.registerBoard(board);
			control.setRequestHandler(new Notificator());
		} catch (BoardNotValidException e) {
			throw new RuntimeException(e);
		} catch (GameAlreadyStartedException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static int nextId() {
		return generatedId++;
	}
	
	public boolean startGame() throws NotEnoughPlayersException, GameAlreadyStartedException, BoardNotRegisteredException, PlayerNotReadyException {
		control.prestart();
		Thread th = new Thread() {
			public void run() {
				control.start();
			}
		};
		th.start();
		control.waitUntilStart();
		return true;
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
	
	public Player removePlayer(Camp camp) throws GameAlreadyStartedException, BoardNotRegisteredException {
		return control.removePlayer(camp);
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
	
	public boolean gameStarted() {
		return control.isStarted();
	}

	public Object getLock() {
		return lock;
	}

	public synchronized void setPawnPosition(int pawnPosition) {
		this.pawnPosition = pawnPosition;
	}

	public synchronized int getPawnPosition() {
		return pawnPosition;
	}

	private class Notificator implements RequestHandler {

		@Override
		public void requestRoll(Player p) {
			playerRolled = false;
			server.notifyRequestRoll(((ServerImpl)server).getSession(p).getSessionId());
			while (!playerRolled) {
				synchronized (lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			playerRolled = false;
		}

		@Override
		public void handleQueryMessage(String message) {
			for (Player pl : getPlayers()) {
				if (pl != null) {
					server.notifyGameQueryMessage(((ServerImpl)server).getSession(pl).getSessionId(), message);
				}
			}
		}

		@Override
		public void handleResultMessage(String message) {
			for (Player pl : getPlayers()) {
				if (pl != null) {
					server.notifyGameResultMessage(((ServerImpl)server).getSession(pl).getSessionId(), message);
				}
			}
		}

		@Override
		public void handleErrorMessage(String message) {
			for (Player pl : getPlayers()) {
				if (pl != null) {
					server.notifyErrorMessage(((ServerImpl)server).getSession(pl).getSessionId(), message);
				}
			}
		}

		@Override
		public synchronized void gameStarted() {
			for (Player pl : getPlayers()) {
				if (pl != null) {
					server.notifyGameStarted(((ServerImpl)server).getSession(pl).getSessionId());
				}
			}
//			try {
//				wait();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}

		@Override
		public void gameEnded(List<Player> places) {
			// TODO Auto-generated method stub
		}

		@Override
		public Pawn requestMove(Player player, int movement) {
			int sessionId = ((ServerImpl)server).getSession(player).getSessionId();
			server.notifyRequestMove(sessionId, player.getCamp(), movement);
			playerMoved = false;
			while (!playerMoved) {
				synchronized (lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			playerMoved = false;
			return player.getPawn(getPawnPosition());
		}
	}
}
