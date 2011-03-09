package pl.krgr.chinczyk.client.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.services.ISourceProviderService;

import pl.krgr.chinczyk.client.presentation.ClientState;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ConnectCommandHandler extends AbstractHandler {	
	
	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event); 
		ISourceProviderService service = (ISourceProviderService) window.getService(ISourceProviderService.class); 
		ClientState clientSourceProvider = (ClientState) service.getSourceProvider(ClientState.CONNECTED); 
		// TODO implement connect functionality
		clientSourceProvider.setConnected(true);
		return null;
	}
}
