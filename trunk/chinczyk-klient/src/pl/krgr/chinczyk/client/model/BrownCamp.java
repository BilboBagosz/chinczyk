package pl.krgr.chinczyk.client.model;

import org.eclipse.swt.graphics.Image;

import pl.krgr.chinczyk.client.presentation.Images;

public class BrownCamp extends AbstractCamp {

	public static final BrownCamp INSTANCE = new BrownCamp(); 	
	private BrownCamp() {}

	@Override
	public int getCellId(int position) {
		if (40 < position && position < 45) {
			return position + 4;
		}
		return calculateCellId(position, BROWN_START_CELL_ID);			
	}

	@Override
	public Image getPawnImage() {
		return Images.BROWN_PAWN;
	}

	@Override
	public int getStartCellId() {
		return Camp.BROWN_START_CELL_ID;
	}

	@Override
	public int[] getCampCells() {
		return Camp.BROWN_CAMP;
	}
}
