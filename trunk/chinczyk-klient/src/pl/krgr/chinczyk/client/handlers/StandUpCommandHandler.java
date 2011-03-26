package pl.krgr.chinczyk.client.handlers;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;


public class StandUpCommandHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
//		final Shell shell = HandlerUtil.getActiveShell(event);
//		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event); 
//		ISourceProviderService service = (ISourceProviderService) window.getService(ISourceProviderService.class); 
//		final ClientState clientState = (ClientState) service.getSourceProvider(ClientState.CONNECTED); 
//
//		StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);
//		final Room room = (Room) selection.getFirstElement();
		
//		StandUpCommand standUp = new StandUpCommand(room.getId(), new HandlerCallback() {
//			@Override
//			public void commandExecuted(CallBackEvent event) {
//				if (!event.getResult()) {
//					MessageDialog.openError(shell, "B³¹d", "Operacja siê nie powiod³a " + event.getMessage());
//					return;
//				}
//				clientState.updateRoom((Room) event.getEventStructure());								
//			}
//		});
//		
//		try {
//			Connector connector = clientState.getConnector();
//			connector.handleRequest(standUp);
//		} catch (ConnectorNotConnectedException e) {
//			MessageDialog.openError(shell, "B³±d", e.getMessage());
//			e.printStackTrace();
//		}
//		
		return null;
	}

}
