package pl.krgr.chinczyk.client.presentation;


public abstract class AbstractCamp implements Camp {
		
	protected int calculateCellId(int position, int startCellId) {
		int id = (position + startCellId - 2) % 40 + 1;
		return id;
	}
}
