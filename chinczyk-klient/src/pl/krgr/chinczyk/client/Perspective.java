package pl.krgr.chinczyk.client;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
//		layout.addStandaloneView(MainView.ID, false, IPageLayout.LEFT, 1f, "");
		layout.setFixed(true);
	}
}
