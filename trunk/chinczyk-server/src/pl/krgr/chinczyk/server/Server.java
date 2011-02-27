package pl.krgr.chinczyk.server;

import java.util.List;

public interface Server {
	
	public String connectPlayer(int sessionId);
	
	public void disconnectPlayer(int sessionId);
	
	public Room createNewRoom();
	
	public void closeRoom(Room room);
	
	public Room openRoom(int roomId);

	public List<Room> getRooms();

	public void start() throws ServerException;
	
	public void stop();

	public List<Integer> getSessions();
}
