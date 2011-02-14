package pl.krgr.chinczyk.client.presentation;

import org.eclipse.swt.graphics.Image;
import static pl.krgr.chinczyk.client.presentation.Images.*;

public enum Camp {
	RED,
	YELLOW,
	BROWN,
	GREEN;
	
	private static final int RED_START_CELL_ID = 31;
	private static final int GREEN_START_CELL_ID = 21;
	private static final int YELLOW_START_CELL_ID = 1;
	private static final int BROWN_START_CELL_ID = 11;
	
//	public int getPosition(int cellId) {
//		switch (this) {
//		case BROWN : {
//			if (44 < cellId && cellId < 49) {
//				return cellId - 4;
//			}
//			return calculatePosition(cellId, BROWN_START_CELL_ID);
//		}
//		case GREEN : { 
//			if (48 < cellId && cellId < 53) {
//				return cellId - 8;
//			}
//			return calculatePosition(cellId, GREEN_START_CELL_ID);
//		}
//		case RED   : {
//			if (52 < cellId && cellId < 57) {
//				return cellId - 12;
//			}
//			return calculatePosition(cellId, RED_START_CELL_ID);
//		}
//		case YELLOW : return cellId;
//		}
//		return -1;
//	}

	public int getCellId(int position) {
		switch (this) {
		case BROWN : {
			if (40 < position && position < 45) {
				return position + 4;
			}
			return calculateCellId(position, BROWN_START_CELL_ID);			
		}
		case GREEN : { 
			if (40 < position && position < 45) {
				return position + 8;
			}
			return calculateCellId(position, GREEN_START_CELL_ID);			
		}
		case RED : {
			if (40 < position && position < 45) {
				return position + 12;
			}
			return calculateCellId(position, RED_START_CELL_ID);			
		}
		case YELLOW : return position;
		}
		return -1;
	}
	
	private int calculateCellId(int position, int startCellId) {
		int id = (position + startCellId - 2) % 40 + 1;
		return id;
	}

//	private int calculatePosition(int cellId, int startCellId) {
//		int position = (cellId - startCellId) % 40 + 1;
//		return position;
//	}
	
	public Image getImage() {
		switch (this) {
		case BROWN : return BROWN_PAWN;
		case GREEN : return GREEN_PAWN;
		case RED : return RED_PAWN;
		case YELLOW : return YELLOW_PAWN;
		}
		return null;
	}
	
	public int getStartCellId() {
		switch (this) {
		case BROWN : return BROWN_START_CELL_ID;
		case GREEN : return GREEN_START_CELL_ID;
		case RED : return RED_START_CELL_ID;
		case YELLOW : return YELLOW_START_CELL_ID;
		}
		return -1;
	}
}
