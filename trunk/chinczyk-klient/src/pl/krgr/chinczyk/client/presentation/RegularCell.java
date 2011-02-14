package pl.krgr.chinczyk.client.presentation;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import pl.krgr.chinczyk.model.Pawn;

public class RegularCell extends AbstractCell {

	private Image highlight;
	private Image backlight;
	/**
	 * Parent has to have GridLayout set
	 * @param parent
	 * @param style
	 */
	public RegularCell(Composite parent) {
		this(parent, Images.DEFAULT_IMAGE, Images.DEFAULT_LIGHT);
	}
		
	public RegularCell(Composite parent, Image cellImage, Image highlight) {
		super(parent);
		getGd().widthHint = AbstractCell.CELL_WIDTH;
		setCellImage(cellImage);
		this.highlight = highlight;
		this.backlight = cellImage;
	}

	@Override
	public void highlight(Pawn origin) {
		setCellImage(highlight);
	}

	@Override
	public void backlight() {
		setCellImage(backlight);
	}
}
