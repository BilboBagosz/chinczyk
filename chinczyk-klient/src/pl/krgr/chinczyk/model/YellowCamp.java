package pl.krgr.chinczyk.model;

public class YellowCamp extends AbstractCamp {

	public static final YellowCamp INSTANCE = new YellowCamp();
	private static final int PLAYER_POSITION = 1; 	
	private YellowCamp() {}
	
	@Override
	public int getCellId(int position) {
		return position;
	}

	@Override
	public ImageType getPawnImage() {
		return ImageType.YELLOW;
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
	public int getPlayerPosition() {
		return PLAYER_POSITION;
	}

	@Override
	public int[] getHomeCells() {
		return YELLOW_HOME;
	}

	@Override
	public ImageType getHighlight() {
		return ImageType.YELLOW;
	}

}
