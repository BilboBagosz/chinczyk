package pl.krgr.chinczyk.client.presentation;

import org.eclipse.swt.graphics.Image;

public interface Camp {

	int RED_START_CELL_ID = 31;
	int GREEN_START_CELL_ID = 21;
	int YELLOW_START_CELL_ID = 1;
	int BROWN_START_CELL_ID = 11;
	
	int[] RED_CAMP = {};
	int[] GREEN_CAMP = {};
	int[] YELLOW_CAMP = {};
	int[] BROWN_CAMP = {};
	
	int getCellId(int position);
	Image getPawnImage();
	int getStartCellId();
	
//	public int getCellId(int position) {
//		switch (this) {
//		case BROWN : {
//			if (40 < position && position < 45) {
//				return position + 4;
//			}
//			return calculateCellId(position, BROWN_START_CELL_ID);			
//		}
//		case GREEN : { 
//			if (40 < position && position < 45) {
//				return position + 8;
//			}
//			return calculateCellId(position, GREEN_START_CELL_ID);			
//		}
//		case RED : {
//			if (40 < position && position < 45) {
//				return position + 12;
//			}
//			return calculateCellId(position, RED_START_CELL_ID);			
//		}
//		case YELLOW : return position;
//		}
//		return -1;
//	}
	
//	private int calculateCellId(int position, int startCellId) {
//		int id = (position + startCellId - 2) % 40 + 1;
//		return id;
//	}
	
//	public Image getImage() {
//		switch (this) {
//		case BROWN : return BROWN_PAWN;
//		case GREEN : return GREEN_PAWN;
//		case RED : return RED_PAWN;
//		case YELLOW : return YELLOW_PAWN;
//		}
//		return null;
//	}
	
//	public int getStartCellId() {
//		switch (this) {
//		case BROWN : return BROWN_START_CELL_ID;
//		case GREEN : return GREEN_START_CELL_ID;
//		case RED : return RED_START_CELL_ID;
//		case YELLOW : return YELLOW_START_CELL_ID;
//		}
//		return -1;
//	}
}
