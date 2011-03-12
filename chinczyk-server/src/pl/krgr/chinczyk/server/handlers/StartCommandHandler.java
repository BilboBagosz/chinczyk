package pl.krgr.chinczyk.server.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.services.ISourceProviderService;

import pl.krgr.chinczyk.server.ServerException;
import pl.krgr.chinczyk.server.presentation.ServerState;

public class StartCommandHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Shell shell = HandlerUtil.getActiveShell(event);
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event); 
		ISourceProviderService service = (ISourceProviderService) window.getService(ISourceProviderService.class); 
		final ServerState serverState = (ServerState) service.getSourceProvider(ServerState.STARTED); 

		try {			
			serverState.startServer();
		} catch (ServerException e) {
			MessageDialog.openError(shell, "B³¹d", e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}
