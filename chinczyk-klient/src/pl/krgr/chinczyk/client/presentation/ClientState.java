package pl.krgr.chinczyk.client.presentation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;

import pl.krgr.chinczyk.network.NetworkException;
import pl.krgr.chinczyk.network.client.Connector;

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
		Boolean state = (Boolean) getCurrentState().get(property);
		fireSourceChanged(ISources.WORKBENCH, CONNECTED, state);
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

	public Connector getConnector(String host, int port) throws NetworkException {
		if (this.connector == null) {
			this.connector = new Connector(host, port);
			try {
				this.connector.connect();
			} catch (NetworkException ne) {
				this.connector = null;
				throw ne;
			}
		}
		return connector;
	}
	
	public void addRoom(Room room) {
		rooms.add(room);
		for (ChangeListener listener: listeners) {
			listener.notifyChange(room);
		}
	}
	
	public void removeRoom(int roomId) {
		Room toremove = null;
		for (Room room : rooms) {
			if (room.getId() == roomId) {
				toremove = room;
				break;
			}
		}
		if (toremove != null) {
			rooms.remove(toremove);
		}
		for (ChangeListener listener: listeners) {
			listener.notifyChange(toremove);
		}
	}
	
	public void addListener(ChangeListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(ChangeListener listener) {
		this.listeners.remove(listener);
	}
}