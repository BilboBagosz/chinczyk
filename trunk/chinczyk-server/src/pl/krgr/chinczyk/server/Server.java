package pl.krgr.chinczyk.server;

import java.util.List;

import pl.krgr.chinczyk.control.GameAlreadyStartedException;
import pl.krgr.chinczyk.control.PlayerAlreadyRegisteredException;
import pl.krgr.chinczyk.model.Camp;

public interface Server {

	/**
	 * 
	 * @param sessionId
	 * @return
	 */
	public String connectPlayer(int sessionId);
	
	/**
	 * 
	 * @param sessionId
	 */
	public String disconnectPlayer(int sessionId);
	
	/**
	 * 
	 * @param sessionId
	 * @return Room info
	 * @throws NotConnectedException
	 */
	public String createNewRoom(int sessionId) throws NotConnectedException;
	
	/**
	 * 
	 * @param room
	 */
	public void closeRoom(Room room);
	
	/**
	 * 
	 * @param sessionId
	 * @return Rooms info
	 * @throws NotConnectedException
	 */
	public List<String> getRooms(int sessionId) throws NotConnectedException;

	/**
	 * 
	 * @throws ServerException
	 */
	public void start() throws ServerException;
	
	/**
	 * 
	 */
	public void stop();
	
	/**
	 * 
	 * @param roomId
	 * @param sessionId
	 * @return RoomInfo as String
	 * @throws NotConnectedException
	 */
	public String getRoom(int roomId, int sessionId) throws NotConnectedException;
	
	/**
	 * 
	 * @param roomId
	 * @param sessionId
	 * @param playerName
	 * @param camp
	 * @return roomInfo as string
	 * @throws NotConnectedException
	 * @throws GameAlreadyStartedException
	 * @throws PlayerAlreadyRegisteredException
	 */
	public String joinRoom(int roomId, int sessionId, String playerName, Camp camp) throws NotConnectedException, GameAlreadyStartedException, PlayerAlreadyRegisteredException;
	
	/**
	 * 
	 * @param roomId
	 * @param sessionId
	 * @param playerName 
	 * @return RoomInfo as String
	 * @throws NotConnectedException
	 * @throws GameAlreadyStartedException
	 */
	public String standUp(int roomId, int sessionId, String playerName) throws NotConnectedException, GameAlreadyStartedException;
	
	public boolean isStarted();
	
	public String startGame(int sessionId, String playerName, int roomId);

	public void notifyGameResultMessage(int sessionId, String string);
	
	public void notifyGameStarted(int sessionId);

	public void notifyGameQueryMessage(int sessionId, String message);

	public void notifyErrorMessage(int sessionId, String message);
	
	public void notifyRequestRoll(int sessionId);

	public void rollDice(int sessionId);

	public void notifyRequestMove(int sessionId, Camp camp, int movement);
	
	public void move(int sessionId, int pawnPosition) throws WrongPawnException;

}
