package pl.krgr.chinczyk.client.presentation;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;

public class ClientState extends AbstractSourceProvider {

	public static final String CONNECTED = "pl.krgr.chinczyk.client.presentation.ClientState.connected";
	private boolean connected = false;
		
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
	}

	public boolean isConnected() {
		return connected;
	}
}