package pl.krgr.chinczyk.server;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import pl.krgr.chinczyk.control.BoardNotRegisteredException;
import pl.krgr.chinczyk.control.GameAlreadyStartedException;
import pl.krgr.chinczyk.control.PlayerAlreadyRegisteredException;
import pl.krgr.chinczyk.model.Camp;
import pl.krgr.chinczyk.model.ChangeListener;
import pl.krgr.chinczyk.model.Player;
import pl.krgr.chinczyk.network.server.Service;
import pl.krgr.chinczyk.server.network.commands.CommandFactoryImpl;
import pl.krgr.chinczyk.server.network.notifications.DisconnectNotification;
import pl.krgr.chinczyk.server.network.notifications.NewRoomNotification;
import pl.krgr.chinczyk.server.nls.Messages;

public class ServerImpl implements Server {
	
	private Service service;
	private List<Room> rooms = new LinkedList<Room> ();
	private List<Session> sessions = new LinkedList<Session> ();
	private List<ChangeListener> listeners = new LinkedList<ChangeListener>();
	
	private int port;
	
	public ServerImpl(int port) {
		this.port = port;
	}
	
	public void addListener(ChangeListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(ChangeListener listener) {
		listeners.remove(listener);
	}
	
	@Override
	public String connectPlayer(int sessionId) {
		System.out.println("Trying to register session = " + sessionId); //$NON-NLS-1$
		return sessions.add(new Session(sessionId)) ? null : Messages.ServerImpl_CannotConnect;
	}
	
	@Override
	public synchronized String disconnectPlayer(int sessionId) {
		String result = null;
		Session toRemove = null;
		for (Session session : sessions) {
			if (session.getSessionId() == sessionId) {
				toRemove = session;
				break;
			}
		}
		try {
			toRemove.unregister();
			sessions.remove(toRemove);
		} catch (GameAlreadyStartedException e) {
			result = "Cannot disconnect, GameAlreadyStartedException";
			e.printStackTrace();
		}
		notifyChange(toRemove);
		return result;
	}
	
	@Override
	public synchronized String createNewRoom(int sessionId) throws NotConnectedException {
		assertLogged(sessionId);
		Room room = new Room();
		rooms.add(room);
		notifyChange(room);
		service.broadcastExcept(new NewRoomNotification(room.info()), sessionId);
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
				this.service.waitUntilStarted();
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
		this.service.broadcast(new DisconnectNotification());
		this.service.interrupt();
		this.service.waitUntilStop();
		this.service = null;
		Iterator<Session> sessionsIterator = sessions.iterator();
		while (sessionsIterator.hasNext()) {
			Session session = sessionsIterator.next();
			try {
				session.unregister();
			} catch (GameAlreadyStartedException e) {
				e.printStackTrace();
			}
			sessionsIterator.remove();
		}
		notifyChange(sessions);
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
				session.addRoom(room); //and room to session
				result = room.info();
			} catch (BoardNotRegisteredException e) {
				e.printStackTrace();
			}
		}
		notifyChange(room);
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
		Session session = getSession(sessionId);
		Room room = session.getRoom(roomId);
		if (room != null) {
			try {
				room.removePlayer(session.getPlayer().getCamp());
				session.removeRoom(roomId); //remove room from session
				if (session.getRooms().size() == 0) {
					session.setPlayer(null);  //remove player from session
				}
				result = room.info();
			} catch (BoardNotRegisteredException e) {
				e.printStackTrace();
			}
		}
		notifyChange(room);
		return result;
	}

	@Override
	public boolean isStarted() {
		if (service != null) {
			return service.isRunning();
		} else {
			return false;
		}
	}
	
	public void notifyChange(Object change) {
		for (ChangeListener listener : listeners) {
			listener.notifyChange(change);
		}
	}
}
