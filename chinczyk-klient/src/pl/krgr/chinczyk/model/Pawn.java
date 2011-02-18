package pl.krgr.chinczyk.model;

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
		if (owner == baseCell && movement != 6) { //from base can only run if 6 thrown
			return false;
		}
		
		for (int i = actualPosition + 1; i < actualPosition + movement; i++) { //check if road is free
			if (!boardMap.get(camp.getCellId(i)).isFree()) {
				return false;
			}
		}
		Cell targetCell = boardMap.get(camp.getCellId(actualPosition + movement));
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
			if (owner == baseCell) {
				Cell targetCell = boardMap.get(camp.getCellId(START_POSITION));
				targetCell.highlight(HighlightType.DEFAULT);
				targetCell.update();
				return;
			}
			for (int i = actualPosition + 1; i < actualPosition + movement; i++) {
				Cell cell = boardMap.get(camp.getCellId(i));
				cell.highlight(HighlightType.DEFAULT);
				cell.update();
			}
		}
	}

	public void highlightMe(int movement) {
		if (canMove(movement)) {
			owner.highlight(camp.getHighlight());
			owner.update();
		}
	}
	
	public void backlightRoad(int movement) {
		for (int i = actualPosition + 1; i < actualPosition + movement; i++) {
			Cell cell = boardMap.get(camp.getCellId(i));
			cell.backlight();
			cell.update();
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
	
	public void clean() {
		this.baseCell.setPawn(null);
		this.owner.setPawn(null);
		this.baseCell.update();
		this.owner.update();
	}

}
