package pl.krgr.chinczyk.client.presentation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class HorizontalCell extends AbstractCell {

	private static final int HORIZONTAL_SPAN = 2;
	private static final int VERTICAL_SPAN = 2;
	
	public HorizontalCell(Composite parent) {
		super(parent);
		setCellImage(null);
		getGd().horizontalSpan = HORIZONTAL_SPAN;
		getGd().verticalSpan = VERTICAL_SPAN;
		getGd().widthHint = AbstractCell.CELL_WIDTH * HORIZONTAL_SPAN;
		getGd().horizontalAlignment = SWT.FILL;
		getGd().verticalAlignment = SWT.FILL;
	}

	public HorizontalCell(Composite parent, Image cellImage) {
		this(parent);
		setCellImage(cellImage);
	}

	@Override
	public void highlight(Pawn origin) {
		//no implementation
	}

	@Override
	public void backlight() {
		// no implementation
	}
}
