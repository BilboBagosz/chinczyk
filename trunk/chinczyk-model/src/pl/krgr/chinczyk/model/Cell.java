package pl.krgr.chinczyk.model;


public interface Cell {

	public int getId();
	public void setId(int id);
	public void update();
	public void setPawn(Pawn pawn);
	public Pawn getPawn();
	boolean isFree();
	void highlight(ImageType type);
	void backlight();
}
