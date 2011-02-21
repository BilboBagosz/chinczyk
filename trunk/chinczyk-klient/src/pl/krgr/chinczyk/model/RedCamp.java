package pl.krgr.chinczyk.model;

public class RedCamp extends AbstractCamp {
	
	public static final RedCamp INSTANCE = new RedCamp();
	private static final int PLAYER_POSITION = 0; 	
	private RedCamp() {}
	
	@Override
	public int getCellId(int position) {
		if (Camp.HOME_START < position && position < Camp.HOME_END) {
			return position + 12;
		}
		return calculateCellId(position, RED_START_CELL_ID);			
	}

	@Override
	public ImageType getPawnImage() {
		return ImageType.RED;
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
	public int getPlayerPosition() {
		return PLAYER_POSITION;
	}

	@Override
	public int[] getHomeCells() {
		return RED_HOME;
	}

	@Override
	public ImageType getHighlight() {
		return ImageType.RED;
	}

}
