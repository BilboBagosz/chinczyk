package pl.krgr.chinczyk.server.presentation;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;

import pl.krgr.chinczyk.server.Server;
import pl.krgr.chinczyk.server.ServerException;
import pl.krgr.chinczyk.server.ServerImpl;

public class ServerState extends AbstractSourceProvider {
	
	public static final String STARTED = "pl.krgr.chinczyk.server.presentation.ServerState.started";
	private Server server;
	private static final int PORT = 5555;
	
	public ServerState() {
		server = new ServerImpl(PORT);		
	}

	@Override
	public void dispose() {}

	@SuppressWarnings("rawtypes")
	@Override
	public Map getCurrentState() {
		Map<String, Boolean> currentState = new HashMap<String, Boolean>(1); 
        currentState.put(STARTED, server.isStarted()); 
        return currentState; 			
	}

	@Override
	public String[] getProvidedSourceNames() {
		return new String[] { STARTED };
	}

	public void propertyChanged(String property) {
		if (STARTED.equals(property)) {
			Boolean state = (Boolean) getCurrentState().get(property);
			fireSourceChanged(ISources.WORKBENCH, STARTED, state);
		}
	}
	
	public void startServer() throws ServerException {
		server.start();
		propertyChanged(STARTED);
	}
	
	public void stopServer() {
		server.stop();
		propertyChanged(STARTED);
	}
}
