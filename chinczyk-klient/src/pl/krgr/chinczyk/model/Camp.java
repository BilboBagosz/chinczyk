package pl.krgr.chinczyk.model;

import org.eclipse.swt.graphics.Image;

public interface Camp {

	int RED_START_CELL_ID = 31;
	int GREEN_START_CELL_ID = 21;
	int YELLOW_START_CELL_ID = 1;
	int BROWN_START_CELL_ID = 11;
	
	int[] RED_CAMP = {57, 58, 59, 60};
	int[] GREEN_CAMP = {69, 70, 71, 72};
	int[] YELLOW_CAMP = {61, 62, 63, 64};
	int[] BROWN_CAMP = {65, 66, 67, 68};
	
	int getCellId(int position);
	Image getPawnImage();
	int getStartCellId();
	int[] getCampCells();
	int getPriority();
}
