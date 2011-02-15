package pl.krgr.chinczyk.model;

import org.eclipse.swt.graphics.Image;

import pl.krgr.chinczyk.client.presentation.Images;

public class BrownCamp extends AbstractCamp {

	private static final int PRIORITY = 2;
	
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

	@Override
	public int getPriority() {		
		return PRIORITY;
	}
}
