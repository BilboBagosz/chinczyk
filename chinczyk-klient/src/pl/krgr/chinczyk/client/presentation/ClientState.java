package pl.krgr.chinczyk.client.presentation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;

import pl.krgr.chinczyk.client.control.GameControlProxy;
import pl.krgr.chinczyk.client.network.CallBackEvent;
import pl.krgr.chinczyk.client.network.DisconnectNotification;
import pl.krgr.chinczyk.client.network.GameErrorNotification;
import pl.krgr.chinczyk.client.network.GameQueryNotification;
import pl.krgr.chinczyk.client.network.GameResultNotification;
import pl.krgr.chinczyk.client.network.GameStartedNotification;
import pl.krgr.chinczyk.client.network.HandlerCallback;
import pl.krgr.chinczyk.client.network.MoveCommand;
import pl.krgr.chinczyk.client.network.NewRoomNotification;
import pl.krgr.chinczyk.client.network.RequestMoveNotification;
import pl.krgr.chinczyk.client.network.RequestRollNotification;
import pl.krgr.chinczyk.client.network.RollCommand;
import pl.krgr.chinczyk.client.network.RoomChangedNotification;
import pl.krgr.chinczyk.control.GameControl;
import pl.krgr.chinczyk.model.AbstractCamp;
import pl.krgr.chinczyk.model.Camp;
import pl.krgr.chinczyk.model.ChangeListener;
import pl.krgr.chinczyk.network.NetworkException;
import pl.krgr.chinczyk.network.Notifications;
import pl.krgr.chinczyk.network.ProtocolHelper;
import pl.krgr.chinczyk.network.client.Connector;
import pl.krgr.chinczyk.network.client.ConnectorNotConnectedException;
import pl.krgr.chinczyk.network.client.NotificationHandler;
import pl.krgr.chinczyk.network.commands.ClientCommand;
import pl.krgr.chinczyk.network.notifications.ClientNotification;

public class ClientState extends AbstractSourceProvider {

	public static final String CONNECTED = "pl.krgr.chinczyk.client.presentation.ClientState.connected";	
	private boolean connected = false;
	private Connector connector = null;
	private List<Room> rooms = new LinkedList<Room>();
	private List<ChangeListener> listeners = new LinkedList<ChangeListener>();
	
	@Override
	public void dispose() {}

	@SuppressWarnings("rawtypes")
	@Override
	public Map getCurrentState() {
		Map<String, Boolean> currentState = new HashMap<String, Boolean>(1); 
        currentState.put(CONNECTED, connected); 
        return currentState; 			
	}

	@Override
	public String[] getProvidedSourceNames() {
		return new String[] { CONNECTED };
	}
	
	public void propertyChanged(String property) {
		if (CONNECTED.equals(property)) {
			Boolean state = (Boolean) getCurrentState().get(property);
			fireSourceChanged(ISources.WORKBENCH, CONNECTED, state);
		}
	}
	
	public void setConnected(boolean connected) {
		if (this.connected == connected) {
			return; // no change
		} else {
			this.connected = connected;
			propertyChanged(CONNECTED);
		}
		if (!this.connected) {
			this.connector.disconnect();
			this.connector = null;
		}
	}

	public boolean isConnected() {
		return connected;
	}

	public Connector getConnector(String host, int port) throws ConnectorNotConnectedException {
		if (this.connector == null) {
			this.connector = new Connector(host, port, new ClientNotificationHandler());
			try {
				this.connector.connect();
			} catch (NetworkException ne) {
				this.connector = null;
				throw new ConnectorNotConnectedException(ne);
			}
		}
		return connector;
	}
	
	public Connector getConnector() throws ConnectorNotConnectedException {
		if (!connector.isConnected()) {
			throw new ConnectorNotConnectedException();
		}
		return connector;
	}
			
	public void addRoom(Room room) {
		rooms.add(room);
		notifyListeners(room);
	}
	
	public Room getRoom(int roomId) {
		for (Room room : rooms) {
			if (room.getId() == roomId) {
				return room;
			}
		}
		return null;
	}
	
	public void removeRoom(int roomId) {
		Room toremove = getRoom(roomId);
		if (toremove != null) {
			rooms.remove(toremove);
		}
		notifyListeners(toremove);
	}	
	
	public void addListener(ChangeListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(ChangeListener listener) {
		this.listeners.remove(listener);
	}
	
	public void clearRooms() {
		rooms.clear();
		notifyListeners(null);
	}

	private void notifyListeners(Object o) {
		for (ChangeListener listener: listeners) {
			listener.notifyChange(o);
		}
	}
	
	public List<Room> getRooms() {
		return rooms;
	}

	public void updateRoom(Room room) {
		Room toUpdate = getRoom(room.getId());
		if (toUpdate == null) {
			return;
		}
		toUpdate.setPlayers(room.getPlayers());
		toUpdate.setStarted(room.isStarted());		
		notifyListeners(toUpdate);
	}
	
	private class ClientNotificationHandler implements NotificationHandler {

		@Override
		public void handleNotification(String notification) {
			System.out.println("ClientNotificationHandler::handleNotification, notification = " + notification);			
			
			String[] match = null;
			if ((match = ProtocolHelper.matches(Notifications.SERVER_STOP, notification)).length > 0) {
				ClientNotification notif = new DisconnectNotification(ClientState.this);
				notif.executeNotification();
				return;
			}
			if ((match = ProtocolHelper.matches(Notifications.NEW_ROOM_INFO, notification)).length > 0) {
				ClientNotification notif = new NewRoomNotification(ClientState.this, match);
				notif.executeNotification();
				return;
			}			
			if ((match = ProtocolHelper.matches(Notifications.ROOM_CHANGED, notification)).length > 0) {
				ClientNotification notif = new RoomChangedNotification(ClientState.this, match);
				notif.executeNotification();
				return;
			}						
			if ((match = ProtocolHelper.matches(Notifications.GAME_RESULT, notification)).length > 0) {
				ClientNotification notif = new GameResultNotification(ClientState.this, match[0]);
				notif.executeNotification();
				return;
			}						
			if ((match = ProtocolHelper.matches(Notifications.GAME_STARTED, notification)).length > 0) {
				ClientNotification notif = new GameStartedNotification(ClientState.this);
				notif.executeNotification();
				return;
			}						
			if ((match = ProtocolHelper.matches(Notifications.GAME_QUERY, notification)).length > 0) {
				ClientNotification notif = new GameQueryNotification(ClientState.this, match[0]);
				notif.executeNotification();
				return;
			}	
			if ((match = ProtocolHelper.matches(Notifications.GAME_ERROR, notification)).length > 0) {
				ClientNotification notif = new GameErrorNotification(ClientState.this, match[0]);
				notif.executeNotification();
				return;
			}					
			if ((match = ProtocolHelper.matches(Notifications.REQUEST_ROLL, notification)).length > 0) {
				ClientNotification notif = new RequestRollNotification(ClientState.this);
				notif.executeNotification();
				return;
			}			
			if ((match = ProtocolHelper.matches(Notifications.REQUEST_MOVE, notification)).length > 0) {
				Camp camp = AbstractCamp.fromString(match[0]);
				int movement = Integer.parseInt(match[1]);
				ClientNotification notif = new RequestMoveNotification(ClientState.this, camp, movement);
				notif.executeNotification();
				return;
			}
			if ((match = ProtocolHelper.matches(Notifications.MOVE, notification)).length > 0) {
				Camp camp = AbstractCamp.fromString(match[0]);
				int pawnPosition = Integer.parseInt(match[1]);
				int movement = Integer.parseInt(match[2]);
//				ClientNotification notif = new MoveNotification(ClientState.this, camp, pawnPosition, movement);
//				notif.executeNotification();
				handleMove(camp, pawnPosition, movement);
				return;
			}
			if ((match = ProtocolHelper.matches(Notifications.CLEAR_KILLS, notification)).length > 0) {
				Camp camp = AbstractCamp.fromString(match[0]);
				handleClearKills(camp);
				return;
			}			
		}		
	}

	public void openGameView(Room room) {
		GameControl control = new GameControlProxy(this, room);
		Display disp = Display.getCurrent();
		Shell newShell = new Shell(disp);
		newShell.setSize(new Point(600, 430));
		GameView view = new GameView(newShell, control);
		addListener(view);
		newShell.open();						
	}
	
	public void handleClearKills(Camp camp) {
		notifyListeners(new ClearKillsMessage(camp));
	}

	public void handleGameResultNotification(String gameResult) {
		notifyListeners(new GameResultMessage(gameResult));
	}

	public void handleGameStartedNotification() {
		notifyListeners(new GameStartedMessage());
	}

	public void handleGameQueryNotification(String message) {
		notifyListeners(new GameQueryMessage(message));
	}

	public void handleErrorMessageNotification(String message) {
		notifyListeners(new GameErrorMessage(message));
	}

	private void sendCommand(final ClientCommand command) {
		Thread sendingCommand = new Thread() {
			public void run() {
				try {
					getConnector().handleRequest(command);
				} catch (ConnectorNotConnectedException e) {
					e.printStackTrace();
				}
			}
		};
		sendingCommand.start();
	}
	
	public void handleMove(Camp camp, int pawnPosition, int movement) {
		notifyListeners(new MoveMessage(camp, pawnPosition, movement));
	}
	
	public void handleRequestMove(Camp camp, int movement) {
		notifyListeners(new RequestMoveMessage(camp, movement, new HandlerCallback() {
			@Override
			public void commandExecuted(CallBackEvent event) {
				int pawnPosition = Integer.parseInt(event.getMessage());
				ClientCommand moveCommand = new MoveCommand(pawnPosition, new IgnoreHandlerCallback());
				sendCommand(moveCommand);
			}
		}));
		//blocking till this moment in game view
	}
	
	public void handleRequestRoll() {
		notifyListeners(new RequestRollMessage());
		//blocking till this moment in game view
		ClientCommand rollCommand = new RollCommand(new IgnoreHandlerCallback());
		sendCommand(rollCommand);
	}
}