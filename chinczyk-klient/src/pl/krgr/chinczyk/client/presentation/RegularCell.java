package pl.krgr.chinczyk.client.presentation;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import pl.krgr.chinczyk.model.ImageType;

public class RegularCell extends AbstractCell {
	
	/**
	 * Parent has to have GridLayout set
	 * @param parent
	 * @param style
	 */
	public RegularCell(Composite parent, PawnSelectorListener listener) {
		this(parent, Images.DEFAULT_IMAGE, listener);
	}
		
	public RegularCell(Composite parent, Image cellImage, PawnSelectorListener listener) {
		super(parent, listener);
		getGd().widthHint = AbstractCell.CELL_WIDTH;
		setCellImage(cellImage);
	}

	@Override
	public void highlight(ImageType origin) {
		switch(origin) {
		case RED : 
			setHighlight(Images.RED_PAWN_LIGHT);
			break;
		case BROWN :
			setHighlight(Images.BROWN_PAWN_LIGHT);
			break;
		case GREEN : 
			setHighlight(Images.GREEN_PAWN_LIGHT);
			break;
		case YELLOW :
			setHighlight(Images.YELLOW_PAWN_LIGHT);
			break;			
		case DEFAULT :
			setHighlight(Images.DEFAULT_LIGHT);
			break;
		}
	}

	@Override
	public void backlight() {
		setHighlight(null);
	}
}
