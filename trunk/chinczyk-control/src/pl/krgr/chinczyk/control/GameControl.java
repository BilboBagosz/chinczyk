package pl.krgr.chinczyk.control;

import java.util.Map;

import pl.krgr.chinczyk.model.Camp;
import pl.krgr.chinczyk.model.Cell;
import pl.krgr.chinczyk.model.Player;

public interface GameControl {

	public abstract Player[] getPlayers();

	public abstract Player addPlayer(String name, Camp camp)
			throws BoardNotRegisteredException, GameAlreadyStartedException,
			PlayerAlreadyRegisteredException;

	public abstract Player removePlayer(Camp camp)
			throws GameAlreadyStartedException, BoardNotRegisteredException;

	/**
	 * Starts the game, clients should call this to start a game
	 * 
	 * @throws NotEnoughPlayersException Before game starts there should be at least two players registered
	 * @throws GameAlreadyStartedException
	 * @throws BoardNotRegisteredException Before game starts there should be board registered
	 * @see GameControlImpl#addPlayer(String, Camp)
	 * @see GameControlImpl#registerBoard(Map) 
	 */
	public abstract void start() throws NotEnoughPlayersException,
			GameAlreadyStartedException, BoardNotRegisteredException;

	public abstract void registerBoard(Map<Integer, Cell> board)
			throws BoardNotValidException, GameAlreadyStartedException;

	public abstract void setRequestHandler(RequestHandler requestHandler);

	public abstract boolean isStarted();
	
}