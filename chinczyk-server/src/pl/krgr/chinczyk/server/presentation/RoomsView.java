package pl.krgr.chinczyk.server.presentation;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

import pl.krgr.chinczyk.server.Room;
import pl.krgr.chinczyk.server.Server;
import pl.krgr.chinczyk.server.ServerException;
import pl.krgr.chinczyk.server.ServerImpl;

public class RoomsView extends ViewPart {

	public static final String ID = "chinczyk-s.view";
	private Server server;
	private static final int PORT = 5555;
	
	private TableViewer viewer;

	/**
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */
	class ViewContentProvider implements IStructuredContentProvider {
		
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {}

		public void dispose() {}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Object[] getElements(Object parent) {
			return ((List)parent).toArray(new Room[0]);
		}
	}

	class ViewLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		
		public String getColumnText(Object obj, int index) {
			Room room = (Room) obj;
			switch (index) {
			case 0 : return "" + room.getId();
			case 1 : return room.getPlayers()[0] != null ? room.getPlayers()[0].getName() : "";
			case 2 : return room.getPlayers()[1] != null ? room.getPlayers()[1].getName() : "";
			case 3 : return room.getPlayers()[2] != null ? room.getPlayers()[2].getName() : "";
			case 4 : return room.getPlayers()[3] != null ? room.getPlayers()[3].getName() : "";
			}
			return "";
		}

		public Image getColumnImage(Object obj, int index) {
			return null;
		}
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		server = new ServerImpl(PORT);
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		TableColumn column = new TableColumn(viewer.getTable(), SWT.NONE);
		column.setText("ID pokoju");
		column = new TableColumn(viewer.getTable(), SWT.NONE);
		column.setText("Gracz 1");
		column = new TableColumn(viewer.getTable(), SWT.NONE);
		column.setText("Gracz 2");
		column = new TableColumn(viewer.getTable(), SWT.NONE);
		column.setText("Gracz 3");
		column = new TableColumn(viewer.getTable(), SWT.NONE);
		column.setText("Gracz 4");
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		viewer.setInput(((ServerImpl)server).getRooms());
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	public void startListening() throws ServerException {
		server.start();
	}
}