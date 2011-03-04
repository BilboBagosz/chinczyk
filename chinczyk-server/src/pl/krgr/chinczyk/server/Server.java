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
	public void disconnectPlayer(int sessionId);
	
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
	 * @return
	 * @throws NotConnectedException
	 * @throws GameAlreadyStartedException
	 * @throws PlayerAlreadyRegisteredException
	 */
	public String joinRoom(int roomId, int sessionId, String playerName, Camp camp) throws NotConnectedException, GameAlreadyStartedException, PlayerAlreadyRegisteredException;
}
