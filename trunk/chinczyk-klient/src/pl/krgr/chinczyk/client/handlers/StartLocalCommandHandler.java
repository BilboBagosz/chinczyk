package pl.krgr.chinczyk.client.handlers;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import pl.krgr.chinczyk.client.presentation.GameView;


public class StartLocalCommandHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveShell(event);
		Display disp = shell.getDisplay();
		Shell newShell = new Shell(disp);
		newShell.setSize(new Point(600, 430));
		new GameView(newShell);
		newShell.open();
		return null;
	}

}
