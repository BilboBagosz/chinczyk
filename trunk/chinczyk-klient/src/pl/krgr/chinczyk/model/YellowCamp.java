package pl.krgr.chinczyk.model;

import org.eclipse.swt.graphics.Image;

import pl.krgr.chinczyk.client.presentation.Images;

public class YellowCamp extends AbstractCamp {

	public static final YellowCamp INSTANCE = new YellowCamp();
	private static final int PRIORITY = 1; 	
	private YellowCamp() {}
	
	@Override
	public int getCellId(int position) {
		return position;
	}

	@Override
	public Image getPawnImage() {
		return Images.YELLOW_PAWN;
	}

	@Override
	public int getStartCellId() {
		return Camp.YELLOW_START_CELL_ID;
	}

	@Override
	public int[] getCampCells() {
		return Camp.YELLOW_CAMP;
	}

	@Override
	public int getPriority() {
		return PRIORITY;
	}

	@Override
	public int[] getHomeCells() {
		return YELLOW_HOME;
	}

}
