package pl.krgr.chinczyk.client.presentation;

public interface Cell {

	public int getId();
	public void setId(int id);
	public void redraw();
	public void setPawn(Pawn pawn);
	public Pawn getPawn();
	boolean isFree();
	void highlight(Pawn origin);
	void backlight();
}
