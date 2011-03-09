package pl.krgr.chinczyk.client.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.services.ISourceProviderService;

import pl.krgr.chinczyk.client.presentation.ClientState;

public class DisconnectCommandHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event); 
		ISourceProviderService service = (ISourceProviderService) window.getService(ISourceProviderService.class); 
		ClientState clientSourceProvider = (ClientState) service.getSourceProvider(ClientState.CONNECTED); 
		// TODO implement disconnect functionality
		clientSourceProvider.setConnected(false);
		return null;
	}


}
