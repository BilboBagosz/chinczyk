package pl.krgr.chinczyk.client.presentation;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class HorizontalBigCell extends HorizontalCell {

	private static final int VERTICAL_SPAN = 2;
	private static final int HORIZONTAL_SPAN = 4;
	
	public HorizontalBigCell(Composite parent) {
		super(parent);
		getGd().verticalSpan = VERTICAL_SPAN;
		getGd().horizontalSpan = HORIZONTAL_SPAN;
		getGd().widthHint = AbstractCell.CELL_WIDTH * HORIZONTAL_SPAN;
	}

	public HorizontalBigCell(Composite parent, Image cellImage) {
		this(parent);
		setCellImage(cellImage);
	}
}
