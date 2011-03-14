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
import pl.krgr.chinczyk.network.client.Connector;
import pl.krgr.chinczyk.network.client.ConnectorNotConnectedException;

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
					MessageDialog.openError(shell, "B³¹d", "Operacja siê nie powiod³a " + event.getMessage());
					return;
				}
				clientState.setConnected(false);
				clientState.clearRooms();
			}

		});
		
		try {
			Connector connector = clientState.getConnector();
			connector.handleRequest(disconnect);
		} catch (ConnectorNotConnectedException e) {
			MessageDialog.openError(HandlerUtil.getActiveShell(event), "B³¹d", "B³¹d po³¹czenia z serwerem!");
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
