package pl.krgr.chinczyk.client.presentation;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import pl.krgr.chinczyk.client.activator.Activator;
import pl.krgr.chinczyk.model.Cell;
import pl.krgr.chinczyk.model.Pawn;

public abstract class AbstractCell implements Cell {

	public static final int CELL_WIDTH = 20;
	public static final int CELL_HEIGHT = 20;
	public static final int DEFAULT_ID = -1;
	private static final ImageRegistry IMAGE_REGISTRY = new ImageRegistry();
	
	private PawnSelectorListener listener;
	private Pawn pawn = null;
	
	private GridData gd;
	private Image cellImage;
	private Image highLightImage;
	private int id = DEFAULT_ID;
	private Canvas cellRepresentation;	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AbstractCell(Composite parent, PawnSelectorListener pawnListener) {
		this.listener = pawnListener;
		this.setCellImage(Images.DEFAULT_IMAGE);
		cellRepresentation = new Canvas(parent, SWT.NONE);
		gd = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		gd.heightHint = CELL_HEIGHT;
		cellRepresentation.setLayoutData(gd);
		cellRepresentation.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				if (pawn != null) {					
					e.gc.drawImage(pawn.getCamp().getPawnImage(), 0, 0);
				} else if (cellImage != null) {
					e.gc.drawImage(cellImage, 0, 0);
				}
				if (highLightImage != null) {
					e.gc.drawImage(highLightImage, 0, 0);
				}				
			}
		});
		cellRepresentation.addMouseListener(new MouseListener() {			
			@Override
			public void mouseUp(MouseEvent e) {
			}			
			@Override
			public void mouseDown(MouseEvent e) {
				if (pawn != null && listener != null) {
					listener.pawnSelected(pawn);
				}
			}			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		cellRepresentation.addMouseTrackListener(new MouseTrackListener() {
			
			@Override
			public void mouseHover(MouseEvent e) {
			}
			
			@Override
			public void mouseExit(MouseEvent e) {
				if (listener != null) {
					listener.pawnOver(null);
				}
			}
			
			@Override
			public void mouseEnter(MouseEvent e) {
				if (pawn != null && listener != null) {
					listener.pawnOver(pawn);
				}
			}
		});
	}
	
	public void update() {
		cellRepresentation.getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				cellRepresentation.redraw();				
			}
		});
	}
	
	public static Image getImage(String path) {
		Image image = IMAGE_REGISTRY.get(path);
		if (image == null) {
			URL url = Platform.getBundle(Activator.PLUGIN_ID).getEntry(path);
			if (url != null) {
				ImageDescriptor desc = ImageDescriptor.createFromURL(url);
				image = desc.createImage();
				if (image != null) {
					IMAGE_REGISTRY.put(path, image);
				}
			}
		}
		return image;
	}

	protected GridData getGd() {
		return gd;
	}

	protected void setGd(GridData gd) {
		this.gd = gd;
	}

	protected Image getCellImage() {
		return cellImage;
	}

	
	protected void setHighlight(Image highlight) {
		this.highLightImage = highlight;
	}

	public void setPawn(Pawn pawn) {
		this.pawn = pawn;
	}

	public Pawn getPawn() {
		return pawn;
	}

	@Override
	public boolean isFree() {
		return this.pawn == null;
	}

	public void setCellImage(Image cellImage) {
		this.cellImage = cellImage;
	}
	
}
