package pl.krgr.chinczyk.model;

public class BrownCamp extends AbstractCamp {

	private static final int PLAYER_POSITION = 2;
	
	public static final BrownCamp INSTANCE = new BrownCamp(); 	
	private BrownCamp() {}

	@Override
	public int getCellId(int position) {
		if (Camp.HOME_START < position && position < Camp.HOME_END) {
			return position + 4;
		}
		return calculateCellId(position, BROWN_START_CELL_ID);			
	}

	@Override
	public ImageType getPawnImage() {
		return ImageType.BROWN;
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
	public int getPlayerPosition() {		
		return PLAYER_POSITION;
	}

	@Override
	public int[] getHomeCells() {
		return BROWN_HOME;
	}

	@Override
	public ImageType getHighlight() {
		return ImageType.BROWN;
	}
}
