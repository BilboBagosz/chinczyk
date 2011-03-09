package pl.krgr.chinczyk.client.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.services.ISourceProviderService;

import pl.krgr.chinczyk.client.presentation.ClientState;
import pl.krgr.chinczyk.network.client.Connector;

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
		Shell shell = HandlerUtil.getActiveShell(event);
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event); 
		ISourceProviderService service = (ISourceProviderService) window.getService(ISourceProviderService.class); 
		ClientState clientSourceProvider = (ClientState) service.getSourceProvider(ClientState.CONNECTED); 
		IWorkbenchPart roomsView = HandlerUtil.getActivePart(event);

		// TODO implement connect functionality
		InputDialog dialog = new InputDialog(shell, "Wybór serwera", "Podaj adres serwera", "", null);
		dialog.open();
		String serwer = dialog.getValue();
		Connector connector = new Connector(serwer, 5555);
		
		clientSourceProvider.setConnected(true);
		return null;
	}
}
