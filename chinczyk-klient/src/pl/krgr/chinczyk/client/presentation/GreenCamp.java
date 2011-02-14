package pl.krgr.chinczyk.client.presentation;

import org.eclipse.swt.graphics.Image;

import pl.krgr.chinczyk.client.model.Camp;

public class GreenCamp extends AbstractCamp {

	public static final GreenCamp INSTANCE = new GreenCamp(); 	
	private GreenCamp() {}
	
	@Override
	public int getCellId(int position) {
		if (40 < position && position < 45) {
			return position + 8;
		}
		return calculateCellId(position, GREEN_START_CELL_ID);			
	}

	@Override
	public Image getPawnImage() {
		return Images.GREEN_PAWN;
	}

	@Override
	public int getStartCellId() {
		return Camp.GREEN_START_CELL_ID;
	}

	@Override
	public int[] getCampCells() {
		return Camp.GREEN_CAMP;
	}

}
