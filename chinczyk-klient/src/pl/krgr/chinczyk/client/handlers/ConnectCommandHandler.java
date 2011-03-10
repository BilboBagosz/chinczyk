package pl.krgr.chinczyk.client.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.services.ISourceProviderService;

import pl.krgr.chinczyk.client.network.CallBackEvent;
import pl.krgr.chinczyk.client.network.ConnectCommand;
import pl.krgr.chinczyk.client.network.HandlerCallback;
import pl.krgr.chinczyk.client.presentation.ClientState;
import pl.krgr.chinczyk.network.NetworkException;
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
		final Shell shell = HandlerUtil.getActiveShell(event);
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event); 
		ISourceProviderService service = (ISourceProviderService) window.getService(ISourceProviderService.class); 
		final ClientState clientState = (ClientState) service.getSourceProvider(ClientState.CONNECTED); 

		InputDialog dialog = new InputDialog(shell, "Wybór serwera", "Podaj adres serwera", "localhost", null);
		dialog.open();
		String serwer = dialog.getValue();
		
		Connector connector = null;		
		try {
			connector = clientState.getConnector(serwer, 5555);
		} catch (NetworkException e) {
			MessageDialog.openError(shell, "B³¹d", "Nie mo¿na po³¹czyæ z serwerem");
			e.printStackTrace();
			return null;
		}
		
		ConnectCommand connect = new ConnectCommand(new HandlerCallback() {
			@Override
			public void commandExecuted(CallBackEvent event) {
				if (!event.getResult()) {
					MessageDialog.openError(shell, "B³¹d", event.getMessage());
				}
				clientState.setConnected(event.getResult());
			}
		});
		connector.handleRequest(connect);
		return null;
	}
}
