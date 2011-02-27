package pl.krgr.chinczyk.server.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

import pl.krgr.chinczyk.server.ServerException;
import pl.krgr.chinczyk.server.presentation.RoomsView;

public class StartCommandHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPart part = HandlerUtil.getActivePart(event);
		if (part instanceof RoomsView) {
			RoomsView view = (RoomsView) part;
			try {
				view.startListening();
				MessageDialog.openInformation(part.getSite().getShell(), "Informacja", "Server wystartowany");
			} catch (ServerException e) {
				MessageDialog.openError(part.getSite().getShell(), "B³±d", e.getLocalizedMessage());
				//e.printStackTrace();
			}
		}
		return null;
	}

}
