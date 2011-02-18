package pl.krgr.chinczyk.model;

import org.eclipse.swt.graphics.Image;

import pl.krgr.chinczyk.client.presentation.Images;

public class RedCamp extends AbstractCamp {
	
	public static final RedCamp INSTANCE = new RedCamp();
	private static final int PRIORITY = 0; 	
	private RedCamp() {}
	
	@Override
	public int getCellId(int position) {
		if (40 < position && position < 45) {
			return position + 12;
		}
		return calculateCellId(position, RED_START_CELL_ID);			
	}

	@Override
	public Image getPawnImage() {
		return Images.RED_PAWN;
	}

	@Override
	public int getStartCellId() {
		return Camp.RED_START_CELL_ID;
	}

	@Override
	public int[] getCampCells() {
		return Camp.RED_CAMP;
	}

	@Override
	public int getPriority() {
		return PRIORITY;
	}

	@Override
	public int[] getHomeCells() {
		return RED_HOME;
	}

	@Override
	public HighlightType getHighlight() {
		return HighlightType.RED;
	}

}