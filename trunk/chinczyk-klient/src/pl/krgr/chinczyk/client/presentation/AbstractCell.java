package pl.krgr.chinczyk.client.presentation;

import java.net.URL;
import java.util.Random;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import pl.krgr.chinczyk.client.activator.Activator;

public abstract class AbstractCell implements Cell {

	public static final int CELL_WIDTH = 20;
	public static final int CELL_HEIGHT = 20;
	public static final int DEFAULT_ID = -1;

	private static final ImageRegistry IMAGE_REGISTRY = new ImageRegistry();
	
	private Pawn pawn = null;
	
	private GridData gd;
	private Image cellImage;
	private int id = DEFAULT_ID;
	private Canvas cellRepresentation;	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AbstractCell(Composite parent) {
		this.cellImage = Images.DEFAULT_IMAGE;
		cellRepresentation = new Canvas(parent, SWT.NONE);
		gd = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		gd.heightHint = CELL_HEIGHT;
		cellRepresentation.setLayoutData(gd);
		cellRepresentation.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				if (AbstractCell.this.pawn != null) { //combine pawn image and cell image
					e.gc.drawImage(AbstractCell.this.pawn.getCamp().getPawnImage(), 0, 0);
				} else if (AbstractCell.this.cellImage != null) {
					e.gc.drawImage(AbstractCell.this.cellImage, 0, 0);
				}
				//if (drawId) 
				//	e.gc.drawString(Integer.toString(id), 5, 3);
			}
		});
		cellRepresentation.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				if (pawn != null) {
					Random rand = new Random();
					pawn.move(rand.nextInt(6) + 1);
				}
				//drawId = !drawId;				
				redraw();
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
	}
	
	public void redraw() {
		cellRepresentation.redraw();
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

	
	protected void setCellImage(Image cellImage) {
		this.cellImage = cellImage;
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
	
}
