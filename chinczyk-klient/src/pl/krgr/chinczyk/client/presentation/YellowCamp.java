package pl.krgr.chinczyk.client.presentation;

import org.eclipse.swt.graphics.Image;

import pl.krgr.chinczyk.client.model.Camp;

public class YellowCamp extends AbstractCamp {

	public static final YellowCamp INSTANCE = new YellowCamp(); 	
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

}
