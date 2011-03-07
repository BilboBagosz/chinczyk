package pl.krgr.chinczyk.server;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import pl.krgr.chinczyk.control.BoardNotRegisteredException;
import pl.krgr.chinczyk.control.GameAlreadyStartedException;
import pl.krgr.chinczyk.control.PlayerAlreadyRegisteredException;
import pl.krgr.chinczyk.model.Camp;
import pl.krgr.chinczyk.model.Player;
import pl.krgr.chinczyk.network.server.Service;
import pl.krgr.chinczyk.server.network.commands.CommandFactoryImpl;
import pl.krgr.chinczyk.server.nls.Messages;

public class ServerImpl implements Server {
	
	private Service service;
	private List<Room> rooms = new LinkedList<Room> ();
	private List<Session> sessions = new LinkedList<Session> ();
	private int port;
	
	public ServerImpl(int port) {
		this.port = port;
	}
	
	@Override
	public String connectPlayer(int sessionId) {
		System.out.println("Trying to register session = " + sessionId); //$NON-NLS-1$
		return sessions.add(new Session(sessionId)) ? null : Messages.ServerImpl_CannotConnect;
	}
	
	@Override
	public synchronized void disconnectPlayer(int sessionId) {
		Session toRemove = null;
		for (Session session : sessions) {
			if (session.getSessionId() == sessionId) {
				toRemove = session;
			}
		}
		sessions.remove(toRemove);
	}
	
	@Override
	public synchronized String createNewRoom(int sessionId) throws NotConnectedException {
		assertLogged(sessionId);
		Room room = new Room();
		rooms.add(room);
		return room.info();
	}

	private void assertLogged(int sessionId) throws NotConnectedException {
		for (Session session : sessions) {
			if (session.getSessionId() == sessionId) {
				return;
			}
		}
		throw new NotConnectedException();
	}
	
	@Override
	public void closeRoom(Room room) {		
	}
	
	@Override
	public List<String> getRooms(int sessionId) throws NotConnectedException {
		assertLogged(sessionId);
		List<String> roomsInfo = new LinkedList<String> ();
		for (Room room : rooms) {
			roomsInfo.add(room.info());
		}
		return roomsInfo;
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

	public List<Session> getSessions() {
		return sessions;
	}

	@Override
	public String getRoom(int roomId, int sessionId) throws NotConnectedException {
		assertLogged(sessionId);
		Room room = getRoom(roomId);		
		return room == null ? null : room.info();
	}

	private Room getRoom(int roomId) {
		for (Room room : rooms) {
			if (room.getId() == roomId) {
				return room;
			}
		}
		return null;
	}

	@Override
	public synchronized String joinRoom(int roomId, int sessionId, String playerName, Camp camp) throws NotConnectedException, 
							GameAlreadyStartedException, PlayerAlreadyRegisteredException {
		assertLogged(sessionId);
		String result = null;
		Room room = getRoom(roomId);
		if (room != null) {
			try {
				Player p = room.addPlayer(playerName, camp);
				Session session = getSession(sessionId);
				session.setPlayer(p);  //bind player
				session.setRoom(room); //and room to session
				result = room.info();
			} catch (BoardNotRegisteredException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private Session getSession(int sessionId) {
		for (Session session : sessions) {
			if (session.getSessionId() == sessionId) {
				return session;
			}
		}
		return null;
	}
	
	public List<Room> getRooms() {
		return rooms;
	}

	@Override
	public String standUp(int roomId, int sessionId) throws NotConnectedException, GameAlreadyStartedException {
		assertLogged(sessionId);
		String result = null;
		Room room = getRoom(roomId);
		if (room != null) {
			try {
				Session session = getSession(sessionId);
				room.removePlayer(session.getPlayer().getCamp());
				session.setPlayer(null);  //remove player from session
				session.setRoom(null); //remove room from session
				result = room.info();
			} catch (BoardNotRegisteredException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
