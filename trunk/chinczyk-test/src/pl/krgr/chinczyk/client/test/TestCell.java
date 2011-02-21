package pl.krgr.chinczyk.client.test;

import pl.krgr.chinczyk.model.Cell;
import pl.krgr.chinczyk.model.ImageType;
import pl.krgr.chinczyk.model.Pawn;

public class TestCell implements Cell {

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
	public void highlight(ImageType origin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backlight() {
		// TODO Auto-generated method stub
		
	}

	
}
