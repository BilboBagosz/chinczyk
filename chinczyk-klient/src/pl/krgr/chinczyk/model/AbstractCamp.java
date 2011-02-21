package pl.krgr.chinczyk.model;

public abstract class AbstractCamp implements Camp {
		
	protected int calculateCellId(int position, int startCellId) {
		int id = (position + startCellId - 2) % 40 + 1;
		return id;
	}
}
