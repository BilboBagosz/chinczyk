package pl.krgr.chinczyk.client.presentation;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import pl.krgr.chinczyk.model.HighlightType;

public class RegularCell extends AbstractCell {

	//private Image highlight;
	
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
		//this.highlight = highlight;
	}

	@Override
	public void highlight(HighlightType origin) {
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
