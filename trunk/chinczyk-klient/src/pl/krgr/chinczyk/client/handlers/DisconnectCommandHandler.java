package pl.krgr.chinczyk.client.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.services.ISourceProviderService;

import pl.krgr.chinczyk.client.network.CallBackEvent;
import pl.krgr.chinczyk.client.network.DisconnectCommand;
import pl.krgr.chinczyk.client.network.HandlerCallback;
import pl.krgr.chinczyk.client.presentation.ClientState;
import pl.krgr.chinczyk.network.NetworkException;
import pl.krgr.chinczyk.network.client.Connector;

public class DisconnectCommandHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Shell shell = HandlerUtil.getActiveShell(event);
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event); 
		ISourceProviderService service = (ISourceProviderService) window.getService(ISourceProviderService.class); 
		final ClientState clientState = (ClientState) service.getSourceProvider(ClientState.CONNECTED); 
		DisconnectCommand disconnect = new DisconnectCommand(new HandlerCallback() {

			@Override
			public void commandExecuted(CallBackEvent event) {
				if (!event.getResult()) {
					MessageDialog.openError(shell, "B³¹d", event.getMessage());
				}
				clientState.setConnected(false);
			}

		});
		
		try {
			Connector connector = clientState.getConnector("", 0);
			connector.handleRequest(disconnect);
		} catch (NetworkException e) {
			MessageDialog.openError(HandlerUtil.getActiveShell(event), "B³¹d", "Nie mo¿na po³¹czyæ z serwerem");
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
