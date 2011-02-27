package pl.krgr.chinczyk.server;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import pl.krgr.chinczyk.network.server.Service;
import pl.krgr.chinczyk.server.network.commands.CommandFactoryImpl;

public class ServerImpl implements Server {
	
	private Service service;
	private List<Room> rooms = new LinkedList<Room> ();
	private List<Integer> sessions = new LinkedList<Integer> ();
	private int port;
	
	public ServerImpl(int port) {
		this.port = port;
	}
	
	@Override
	public String connectPlayer(int sessionId) {
		System.out.println("Trying to register session = " + sessionId);
		return sessions.add(sessionId) ? null : "Nie mo¿na po³±czyæ z serwerem";
	}
	
	@Override
	public void disconnectPlayer(int sessionId) {
		sessions.remove(sessionId);
	}
	
	@Override
	public synchronized Room createNewRoom() {
		Room room = new Room();
		rooms.add(room);
		return room;
	}
	
	@Override
	public void closeRoom(Room room) {		
	}
	
	@Override
	public Room openRoom(int roomId) {
		return null;
	}

	@Override
	public List<Room> getRooms() {
		return rooms;
	}

	@Override
	public void start() throws ServerException {
		try {
			if (service == null) {
				this.service = new Service(port, new CommandFactoryImpl(this));
				this.service.start();
			} else {
				throw new ServerException("Serwer zosta³ ju¿ wystartowany.");
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServerException("Problem z wystartowaniem serwera: " + e.getLocalizedMessage());
		}
	}

	@Override
	public void stop() {
		this.service.interrupt();
	}

	public List<Integer> getSessions() {
		return sessions;
	}
}
