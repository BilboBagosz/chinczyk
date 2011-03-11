package pl.krgr.chinczyk.client.presentation;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.services.ISourceProviderService;

public class PlayRoomsView extends ViewPart implements ChangeListener {
	
 	private TableViewer viewer;
	
	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		createTableViewerColumn("ID", 50);
		createTableViewerColumn("Gracz 1", 80);
		createTableViewerColumn("Gracz 2", 80);
		createTableViewerColumn("Gracz 3", 80);
		createTableViewerColumn("Gracz 4", 80);

		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		ISourceProviderService service = (ISourceProviderService) window.getService(ISourceProviderService.class); 
		final ClientState clientState = (ClientState) service.getSourceProvider(ClientState.CONNECTED); 
		clientState.addListener(this);
		viewer.setInput(clientState.getRooms());
		//viewer.setInput(rooms);
		//TODO set input
	}
	
	private TableViewerColumn createTableViewerColumn(String title, int bound) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
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
			case 1 : return room.getPlayers()[0] != null ? room.getPlayers()[0].getName() : "wolne";
			case 2 : return room.getPlayers()[1] != null ? room.getPlayers()[1].getName() : "wolne";
			case 3 : return room.getPlayers()[2] != null ? room.getPlayers()[2].getName() : "wolne";
			case 4 : return room.getPlayers()[3] != null ? room.getPlayers()[3].getName() : "wolne";
			}
			return "";
		}

		public Image getColumnImage(Object obj, int index) {
			return null;
		}
	}
	
	@Override
	public void setFocus() {
	}

	@Override
	public void notifyChange(Object o) {
		this.viewer.refresh();
	}
	
}
