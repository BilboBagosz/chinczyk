package pl.krgr.chinczyk.model;

public abstract class AbstractCamp implements Camp {
		
	protected int calculateCellId(int position, int startCellId) {
		int id = (position + startCellId - 2) % 40 + 1;
		return id;
	}
	
	public static Camp fromString(String campColor) {
		if ("RED".equalsIgnoreCase(campColor)) {
			return RedCamp.INSTANCE;
		} else if ("GREEN".equalsIgnoreCase(campColor)) {
			return GreenCamp.INSTANCE;
		} else if ("YELLOW".equalsIgnoreCase(campColor)) {
			return YellowCamp.INSTANCE;
		} else if ("BROWN".equalsIgnoreCase(campColor)) {
			return BrownCamp.INSTANCE;
		}
		return null;
	}
}
