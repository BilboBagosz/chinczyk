package pl.krgr.chinczyk.server;

import pl.krgr.chinczyk.model.Cell;
import pl.krgr.chinczyk.model.ImageType;
import pl.krgr.chinczyk.model.Pawn;

public class BoardCell implements Cell {

	private int id = -1;
	private Pawn pawn;
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void update() {
	}

	@Override
	public void setPawn(Pawn pawn) {
		this.pawn = pawn;
	}

	@Override
	public Pawn getPawn() {
		return this.pawn;
	}

	@Override
	public boolean isFree() {
		return pawn == null;
	}

	@Override
	public void highlight(ImageType type) {
	}

	@Override
	public void backlight() {
	}

}
