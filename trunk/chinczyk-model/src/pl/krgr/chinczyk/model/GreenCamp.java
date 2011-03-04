package pl.krgr.chinczyk.model;

public class GreenCamp extends AbstractCamp {

	public static final GreenCamp INSTANCE = new GreenCamp();
	private static final int PLAYER_POSITION = 3; 	
	private GreenCamp() {}
	
	@Override
	public int getCellId(int position) {
		if (Camp.HOME_START < position && position < Camp.HOME_END) {
			return position + 8;
		}
		return calculateCellId(position, GREEN_START_CELL_ID);			
	}

	@Override
	public ImageType getPawnImage() {
		return ImageType.GREEN;
	}

	@Override
	public int getStartCellId() {
		return Camp.GREEN_START_CELL_ID;
	}

	@Override
	public int[] getCampCells() {
		return Camp.GREEN_CAMP;
	}

	@Override
	public int getPlayerPosition() {
		return PLAYER_POSITION;
	}

	@Override
	public int[] getHomeCells() {
		return GREEN_HOME;
	}

	@Override
	public ImageType getHighlight() {
		return ImageType.GREEN;
	}

	@Override
	public String toString() {
		return "GREEN";
	}	
}
