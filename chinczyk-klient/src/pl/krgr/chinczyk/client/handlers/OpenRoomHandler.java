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
import pl.krgr.chinczyk.client.network.HandlerCallback;
import pl.krgr.chinczyk.client.network.NewRoomCommand;
import pl.krgr.chinczyk.client.presentation.ClientState;
import pl.krgr.chinczyk.client.presentation.Room;
import pl.krgr.chinczyk.network.client.Connector;
import pl.krgr.chinczyk.network.client.ConnectorNotConnectedException;

public class OpenRoomHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Shell shell = HandlerUtil.getActiveShell(event);
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event); 
		ISourceProviderService service = (ISourceProviderService) window.getService(ISourceProviderService.class); 
		final ClientState clientState = (ClientState) service.getSourceProvider(ClientState.CONNECTED); 
		
		NewRoomCommand newRoomCommand = new NewRoomCommand(new HandlerCallback() {
			
			@Override
			public void commandExecuted(CallBackEvent event) {
				if (!event.getResult()) {
					MessageDialog.openError(shell, "B³¹d", "Operacja siê nie powiod³a " + event.getMessage());
					return;
				}
				clientState.addRoom((Room) event.getEventStructure());
			}
		});
		
		try {
			Connector conn = clientState.getConnector();
			conn.handleRequest(newRoomCommand);
		} catch (ConnectorNotConnectedException e) {
			MessageDialog.openError(shell, "B³¹d", "Brak po³¹czenia z serwerem");
			e.printStackTrace();
		}
		return null;
	}

}
