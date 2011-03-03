package pl.krgr.chinczyk.server;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import pl.krgr.chinczyk.network.server.Service;
import pl.krgr.chinczyk.server.network.commands.CommandFactoryImpl;
import pl.krgr.chinczyk.server.nls.Messages;

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
		System.out.println("Trying to register session = " + sessionId); //$NON-NLS-1$
		return sessions.add(sessionId) ? null : Messages.ServerImpl_CannotConnect;
	}
	
	@Override
	public void disconnectPlayer(int sessionId) {
		sessions.remove(new Integer(sessionId));
	}
	
	@Override
	public synchronized Room createNewRoom(int sessionId) throws NotConnectedException {
		assertLogged(sessionId);
		Room room = new Room();
		rooms.add(room);
		return room;
	}

	private void assertLogged(int sessionId) throws NotConnectedException {
		if (!sessions.contains(sessionId)) {
			throw new NotConnectedException();
		}
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
				throw new ServerException(Messages.ServerImpl_ServerAlreadyStarted);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServerException(Messages.ServerImpl_CannotStart + e.getLocalizedMessage());
		}
	}

	@Override
	public void stop() {
		this.service.interrupt();
	}

	@Override
	public List<Integer> getSessions() {
		return sessions;
	}

	@Override
	public Room getRoom(int roomId, int sessionId) throws NotConnectedException {
		assertLogged(sessionId);
		for (Room room : rooms) {
			if (room.getId() == roomId) {
				return room;
			}
		}
		return null;
	}
}
