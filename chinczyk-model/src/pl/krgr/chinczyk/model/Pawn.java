package pl.krgr.chinczyk.model;

import java.util.Map;


public class Pawn {
	
	public static final int PREMIUM_ROLL = 6;
	private static final int START_POSITION = 1;

	private Camp camp;
	private Cell owner;
	private Cell baseCell;
	private Map<Integer, Cell> boardMap;
	private int actualPosition = 0; // [1 - 44] and base positions
	private int highlight = -1; // indicates how many cells from the actual position of this pawn was highlighted 
	private boolean kills = false; //indicates that this soldier killed another in current turn
	
	public Pawn(Map<Integer, Cell> boardMap, Camp camp) {
		this.boardMap = boardMap;
		this.camp = camp;
		Cell base = findFreeBaseCell();
		setBaseCell(base);
		base.setPawn(this);
		base.update();
	}
	
	private Cell findFreeBaseCell() {
		for (int i : camp.getCampCells()) {
			Cell cell = boardMap.get(i);
			if (cell.isFree()) {
				return cell;
			}
		}
		throw new NoNewFreeCellException();
	}

	public boolean canMove(int movement) {
		if (kills) return false; //cannot move if killed someone ;)
		int targetPosition = actualPosition + movement;
		if (owner == baseCell) {
			if (movement != 6) {
				return false;
			} else {
				targetPosition = START_POSITION;
			}
		}
		if (targetPosition > 44) {
			return false;
		}
		for (int i = actualPosition + 1; i < targetPosition; i++) { //check if road is free
			if (!boardMap.get(camp.getCellId(i)).isFree()) {
				return false;
			}
		}
		Cell targetCell = boardMap.get(camp.getCellId(targetPosition));
		if (targetCell.isFree() || targetCell.getPawn().getCamp() != this.camp) { //check if target cell is free or is occupied by enemy
			return true;
		}
		return false;
	}
	
	public boolean move(int movement) {
		if (owner == baseCell) {
			return moveFromBase(movement);
		} else {
			int targetPosition = actualPosition + movement;
//			if (targetPosition > 44) {
//				return false; //cant go
//			} else {
				if (!canMove(movement)) {
					return false;
				}
				return moveTo(targetPosition);
			}
		}
	//}

	private boolean moveFromBase(int movement) {
		if (movement != 6) {
			return false;
		}
		return moveTo(START_POSITION);
//		return true;
	}

	private boolean moveTo(int position) {
		Cell targetCell = boardMap.get(camp.getCellId(position));
		if (targetCell.isFree()) {
			moveTo(targetCell);
			actualPosition = position;
			return true;
		} else {
//			if (targetCell.getPawn().getCamp() == camp) {
//				return false; //cant go
//			} else { //kill enemy ;)
				//kill enemy :)
				kills = true;
				targetCell.getPawn().returnToBase();
				moveTo(targetCell);
				actualPosition = position;
				return true;
			}
		}
	//}
	
	public void clearKill() {
		kills = false;
	}
	
	private void moveTo(Cell cell) {
		owner.setPawn(null);
		owner.update();
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
			if (owner == baseCell) {
				Cell targetCell = boardMap.get(camp.getCellId(START_POSITION));
				targetCell.highlight(ImageType.DEFAULT);
				highlight = 0; // zero means START POSITION CELL
				targetCell.update();
				return;
			}
			for (int i = actualPosition + 1; i <= actualPosition + movement; i++) {
				Cell cell = boardMap.get(camp.getCellId(i));
				cell.highlight(ImageType.DEFAULT);
				cell.update();
			}
			highlight = movement;
		}
	}

	public void highlightMe(int movement) {
		if (canMove(movement)) {
			owner.highlight(camp.getHighlight());
			owner.update();
		}
	}
	
	public void backlightMe() {
		owner.backlight();
		owner.update();
	}
	
	public void backlightRoad() {
		if (highlight < 0) return;
		Cell cell = null;
		if (highlight == 0) {
			cell = boardMap.get(camp.getCellId(START_POSITION));
			cell.backlight();
			highlight = -1;
			cell.update();
			return;
		}
		for (int i = actualPosition + 1; i <= actualPosition + highlight; i++) {
			cell = boardMap.get(camp.getCellId(i));
			cell.backlight();			
			cell.update();			
		}
		highlight = -1;
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
	
	public void clean() {
		this.baseCell.setPawn(null);
		this.owner.setPawn(null);
		this.baseCell.update();
		this.owner.update();
	}

	public int getActualPosition() {
		return actualPosition;
	}
}
