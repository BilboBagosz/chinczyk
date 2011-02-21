package pl.krgr.chinczyk.model;

public interface Camp {

	int RED_START_CELL_ID = 31;
	int GREEN_START_CELL_ID = 21;
	int YELLOW_START_CELL_ID = 1;
	int BROWN_START_CELL_ID = 11;
	
	int[] RED_CAMP = {57, 58, 59, 60};
	int[] GREEN_CAMP = {69, 70, 71, 72};
	int[] YELLOW_CAMP = {61, 62, 63, 64};
	int[] BROWN_CAMP = {65, 66, 67, 68};
	
	int[] RED_HOME = {53, 54, 55, 56};
	int[] GREEN_HOME = {49, 50, 51, 52};
	int[] YELLOW_HOME = {41, 42, 43, 44};
	int[] BROWN_HOME = {45, 46, 47, 48};
	
	int HOME_START = 40;
	int HOME_END = 45;
	
	int getCellId(int position);
	ImageType getPawnImage();
	int getStartCellId();
	int[] getCampCells();
	int[] getHomeCells();
	int getPlayerPosition();
	ImageType getHighlight();
}
