package pl.krgr.chinczyk.client.model;

import java.util.Map;


public class Pawn {
	
	private static final int START_POSITION = 1;
	
	private Camp camp;
	private Cell owner;
	private Cell baseCell;
	private Map<Integer, Cell> boardMap;
	private int actualPosition; // [1 - 44] and base positions
	
	public Pawn(Map<Integer, Cell> boardMap, Camp camp) {
		this.boardMap = boardMap;
		this.camp = camp;
	}
	
//	private Cell findFreeBaseCell() {
//		for (int i : camp.getCampCells()) {
//			Cell cell = boardMap.get(i);
//			if (cell.isFree()) {
//				return cell;
//			}
//		}
//		return null;
//	}

	public boolean canMove(int movement) {		
		for (int i = actualPosition + 1; i < actualPosition + movement; i++) {
			if (!boardMap.get(camp.getCellId(i)).isFree()) {
				return false;
			}
		}	
		return true;
	}
	
	public boolean move(int movement) {
		if (owner == baseCell) {
			return moveFromBase(movement);
		} else {
			int targetPosition = actualPosition + movement;
			if (targetPosition > 44) {
				return false; //cant go
			} else {
				if (!canMove(movement)) {
					return false;
				}
				return moveTo(targetPosition);
			}
		}
	}

	private boolean moveFromBase(int movement) {
		if (movement != 6) {
			return false;
		}
		moveTo(START_POSITION);
		return true;
	}

	private boolean moveTo(int position) {
		Cell targetCell = boardMap.get(camp.getCellId(position));
		if (targetCell.isFree()) {
			moveTo(targetCell);
			actualPosition = position;
			return true;
		} else {
			if (targetCell.getPawn().getCamp() == camp) {
				return false; //cant go
			} else { //kill enemy ;)
				targetCell.getPawn().returnToBase();
				moveTo(targetCell);
				actualPosition = position;
				return true;
			}
		}
	}
	
	private void moveTo(Cell cell) {
		owner.setPawn(null);
		owner = cell;
		owner.setPawn(this);
		owner.update();
	}
	
	public void returnToBase() {
		owner.setPawn(null);		
		owner = baseCell;
		owner.setPawn(this);
		owner.update();
	}
	
	public void highlightRoad(int movement) {
		if (canMove(movement)) {
			for (int i = actualPosition + 1; i < actualPosition + movement; i++) {
				Cell cell = boardMap.get(camp.getCellId(i));
				cell.highlight(this);
			}
		}
	}

	public void backlightRoad(int movement) {
		for (int i = actualPosition + 1; i < actualPosition + movement; i++) {
			Cell cell = boardMap.get(camp.getCellId(i));
			cell.backlight();
		}		
	}
	
	public void setBaseCell(Cell baseCell) {
		this.baseCell = baseCell;
		this.owner = baseCell;
	}

	public Cell getOwner() {
		return owner;
	}

	public void setCamp(Camp camp) {
		this.camp = camp;		
	}

	public Camp getCamp() {
		return camp;
	}
}
