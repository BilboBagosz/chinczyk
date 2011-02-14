package pl.krgr.chinczyk.client.presentation;

import static pl.krgr.chinczyk.client.presentation.Images.BROWN_IMAGE;
import static pl.krgr.chinczyk.client.presentation.Images.BROWN_LIGHT;
import static pl.krgr.chinczyk.client.presentation.Images.BROWN_START;
import static pl.krgr.chinczyk.client.presentation.Images.DEFAULT_IMAGE;
import static pl.krgr.chinczyk.client.presentation.Images.DEFAULT_LIGHT;
import static pl.krgr.chinczyk.client.presentation.Images.GREEN_IMAGE;
import static pl.krgr.chinczyk.client.presentation.Images.GREEN_LIGHT;
import static pl.krgr.chinczyk.client.presentation.Images.GREEN_START;
import static pl.krgr.chinczyk.client.presentation.Images.RED_IMAGE;
import static pl.krgr.chinczyk.client.presentation.Images.RED_LIGHT;
import static pl.krgr.chinczyk.client.presentation.Images.RED_START;
import static pl.krgr.chinczyk.client.presentation.Images.YELLOW_IMAGE;
import static pl.krgr.chinczyk.client.presentation.Images.YELLOW_LIGHT;
import static pl.krgr.chinczyk.client.presentation.Images.YELLOW_START;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class MainView extends ViewPart {
	
	public static final String ID = "pl.krgr.chinczyk.client.presentation.MainView.view";
	private static final int NR_OF_COLUMNS = 11;
	
	private Map<Integer, Cell> boardMap = new HashMap<Integer, Cell> ();
	private List<Pawn> plainPawns = new LinkedList<Pawn> ();
	
	private IdMapping ids = new IdMapping();
	
	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
			
		RowLayout rowLayout = new RowLayout();
		rowLayout.marginTop = 40;
		rowLayout.justify = true;
		parent.setLayout(rowLayout);
		Composite grid = new Composite(parent, SWT.BORDER);
		GridLayout layout = new GridLayout(NR_OF_COLUMNS, false); 
		grid.setLayout(layout);
		
		createPawns(16);
		drawBoard(grid);
	}

	private void createPawns(int amount) {
		for (int i = 0; i < amount; i++) {
			plainPawns.add(new Pawn(boardMap));
		}
	}

	private Pawn getPawn(Camp camp) {
		if (plainPawns.size() > 0) {
			Pawn pawn = plainPawns.remove(0);
			pawn.setCamp(camp);
			return pawn;
		}
		return null;
	}
	
	private void drawBoard(Composite grid) {

		addCell(grid, RED_IMAGE, RED_LIGHT, getPawn(RedCamp.INSTANCE));
		addCell(grid, RED_IMAGE, RED_LIGHT, getPawn(RedCamp.INSTANCE));	
		new HorizontalCell(grid);
		addCells(grid, 2);
		addCell(grid, YELLOW_START, DEFAULT_LIGHT);
		new HorizontalCell(grid);		
		addCell(grid, YELLOW_IMAGE, YELLOW_LIGHT, getPawn(YellowCamp.INSTANCE));
		addCell(grid, YELLOW_IMAGE, YELLOW_LIGHT, getPawn(YellowCamp.INSTANCE));
		addCell(grid, RED_IMAGE, RED_LIGHT, getPawn(RedCamp.INSTANCE));
		addCell(grid, RED_IMAGE, RED_LIGHT, getPawn(RedCamp.INSTANCE));	
		addCell(grid);
		addCell(grid, YELLOW_IMAGE, YELLOW_LIGHT);
		addCell(grid);
		addCell(grid, YELLOW_IMAGE, YELLOW_LIGHT, getPawn(YellowCamp.INSTANCE));
		addCell(grid, YELLOW_IMAGE, YELLOW_LIGHT, getPawn(YellowCamp.INSTANCE));

		addHorizontals(grid, YELLOW_IMAGE, YELLOW_LIGHT);
		
		addCell(grid, RED_START, DEFAULT_LIGHT);
		addCells(grid, 4);
		addCell(grid, YELLOW_IMAGE, YELLOW_LIGHT);
		addCells(grid, 6);
		addCells(grid, 4, RED_IMAGE, RED_LIGHT);
		new RegularCell(grid, null, null); // cannot add with addCell becuase it don't have ID
		addCells(grid, 4, BROWN_IMAGE, BROWN_LIGHT);
		addCells(grid, 6);
		addCell(grid, GREEN_IMAGE, GREEN_LIGHT);
		addCells(grid, 4);
		addCell(grid, BROWN_START, DEFAULT_LIGHT);

		addHorizontals(grid, GREEN_IMAGE, GREEN_LIGHT);
	
		addCell(grid, GREEN_IMAGE, GREEN_LIGHT, getPawn(GreenCamp.INSTANCE));
		addCell(grid, GREEN_IMAGE, GREEN_LIGHT, getPawn(GreenCamp.INSTANCE));
		new HorizontalCell(grid);
		addCell(grid);
		addCell(grid, GREEN_IMAGE, GREEN_LIGHT);
		addCell(grid);
		new HorizontalCell(grid);		
		addCell(grid, BROWN_IMAGE, BROWN_LIGHT, getPawn(BrownCamp.INSTANCE));
		addCell(grid, BROWN_IMAGE, BROWN_LIGHT, getPawn(BrownCamp.INSTANCE));
		addCell(grid, GREEN_IMAGE, GREEN_LIGHT, getPawn(GreenCamp.INSTANCE));
		addCell(grid, GREEN_IMAGE, GREEN_LIGHT, getPawn(GreenCamp.INSTANCE));
		addCell(grid, GREEN_START, DEFAULT_LIGHT);
		addCells(grid, 2);
		addCell(grid, BROWN_IMAGE, BROWN_LIGHT, getPawn(BrownCamp.INSTANCE));
		addCell(grid, BROWN_IMAGE, BROWN_LIGHT, getPawn(BrownCamp.INSTANCE));
	}

	private void addHorizontals(Composite grid, Image image, Image highlight) {
		new HorizontalBigCell(grid);
		addCell(grid);
		addCell(grid, image, highlight);
		addCell(grid);
		new HorizontalBigCell(grid);
		addCell(grid);
		addCell(grid, image, highlight);
		addCell(grid);
	}

	private void addCells(Composite grid, int number) {
		addCells(grid, number, DEFAULT_IMAGE, DEFAULT_LIGHT);
	}
	
	private void addCells(Composite grid, int number, Image image, Image highlight) {
		for (int i = 0; i < number; i++) {
			addCell(grid, image, highlight);
		}
	}
	
	private void addCell(Composite grid, Image cellImage, Image highlight, Pawn pawn) {
		RegularCell cell = new RegularCell(grid, cellImage, highlight);
		int id = ids.getActualValue();
		cell.setId(id);
		boardMap.put(id, cell);
		if (pawn != null) {
			cell.setPawn(pawn);
			pawn.setBaseCell(cell);
		}
	}
	
	private void addCell(Composite grid, Image cellImage, Image highlight) {
		addCell(grid, cellImage, highlight, null);
	}
	
	private void addCell(Composite grid) {
		addCell(grid, DEFAULT_IMAGE, DEFAULT_LIGHT, null);
	}
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	}
}