package pl.krgr.chinczyk.client.presentation;

import java.util.LinkedList;
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

import pl.krgr.chinczyk.network.client.Connector;

public class PlayRoomsView extends ViewPart {
	
	private List<Room> rooms = new LinkedList<Room>();
 	private TableViewer viewer;
 	private Connector connector;
	
	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
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
	}

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

	public void setConnector(Connector connector) {
		this.connector = connector;
	}
	
	@Override
	public void setFocus() {
	}
	
	public void addRoom(Room room) {
		rooms.add(room);
		viewer.refresh();
	}
	
	public void removeRoom(int roomId) {
		Room toremove = null;
		for (Room room : rooms) {
			if (room.getId() == roomId) {
				toremove = room;
				break;
			}
		}
		if (toremove != null) {
			rooms.remove(toremove);
		}
		viewer.refresh();
	}
}
