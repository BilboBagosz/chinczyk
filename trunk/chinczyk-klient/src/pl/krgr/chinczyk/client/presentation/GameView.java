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
import java.util.Map;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;

import pl.krgr.chinczyk.control.BoardNotRegisteredException;
import pl.krgr.chinczyk.control.BoardNotValidException;
import pl.krgr.chinczyk.control.GameAlreadyStartedException;
import pl.krgr.chinczyk.control.GameControl;
import pl.krgr.chinczyk.control.NotEnoughPlayersException;
import pl.krgr.chinczyk.control.RequestHandler;
import pl.krgr.chinczyk.model.BrownCamp;
import pl.krgr.chinczyk.model.Camp;
import pl.krgr.chinczyk.model.Cell;
import pl.krgr.chinczyk.model.GreenCamp;
import pl.krgr.chinczyk.model.IdMapping;
import pl.krgr.chinczyk.model.Pawn;
import pl.krgr.chinczyk.model.Player;
import pl.krgr.chinczyk.model.RedCamp;
import pl.krgr.chinczyk.model.YellowCamp;

public class GameView extends ViewPart {
	
	public static final String ID = "pl.krgr.chinczyk.client.presentation.MainView.view";
	private static final int NR_OF_COLUMNS = 11;
	
	private Map<Integer, Cell> boardMap = new HashMap<Integer, Cell> ();	
	
	private Label gamePlayLabel;
	private Label gameResultLabel;	
	
	private GameControl control;
	protected volatile boolean rolled = false;
	
	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
			
		RowLayout rowLayout = new RowLayout();
		rowLayout.marginTop = 40;
		rowLayout.justify = true;
		rowLayout.wrap = false;
		parent.setLayout(rowLayout);

		//Board
		Composite board = new Composite(parent, SWT.BORDER);
		GridLayout layout = new GridLayout(NR_OF_COLUMNS, false); 
		board.setLayout(layout);
		drawBoard(board);
		
		//Controls
		Composite gameControl = new Composite(parent, SWT.BORDER);
		layout = new GridLayout(4, false);
		gameControl.setLayout(layout);
		toolkit.adapt(gameControl);		
		drawControls(gameControl, toolkit);
		
		//game control
		control = new GameControl();		
		control.setRequestHandler(new RequestHandler() {
			
			@Override
			public void requestRoll(Player p) {
				rolled = false;
				while (!rolled) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				rolled = false;
			}
			
			@Override
			public void handleResultMessage(String message) {
				setResultTextAsync(message);
			}
			
			@Override
			public void handleQueryMessage(String message) {
				setGamePlayTextAsync(message);
			}

			@Override
			public void handleErrorMessage(String message) {
				setErrorTextAsync(message);
			}
		});
		try {
			control.registerBoard(boardMap);
		} catch (BoardNotValidException e) {
			setErrorTextAsync("B³¹d wewnêtrzny");
			e.printStackTrace();
		} catch (GameAlreadyStartedException e) {
			setErrorTextAsync("Gra ju¿ siê zaczê³a!");
			e.printStackTrace();
		}
	}
	
	private void setResultTextAsync(final String message) {
		gameResultLabel.getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				gameResultLabel.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN));
				gameResultLabel.setText(message);
			}
		});
	}
	
	private void setGamePlayTextAsync(final String message) {
		gamePlayLabel.getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				gamePlayLabel.setText(message);
			}
		});
	}
	
	private void setErrorTextAsync(final String message) {
		gameResultLabel.getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				gameResultLabel.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
				gameResultLabel.setText(message);
			}
		});
	}

	private void drawControls(Composite gameControl, FormToolkit toolkit) {
		
		addImage(gameControl, toolkit, RedCamp.INSTANCE);
		Label redLabel = addLabel(gameControl, toolkit, "Wolne");
		addSeatButton(gameControl, toolkit, RedCamp.INSTANCE, redLabel);
		addStartButton(gameControl, toolkit);
		
		addImage(gameControl, toolkit, YellowCamp.INSTANCE);
		Label yellowLabel = addLabel(gameControl, toolkit, "Wolne");
		addSeatButton(gameControl, toolkit, YellowCamp.INSTANCE, yellowLabel);
		
		addImage(gameControl, toolkit, BrownCamp.INSTANCE);
		Label brownLabel = addLabel(gameControl, toolkit, "Wolne");
		addSeatButton(gameControl, toolkit, BrownCamp.INSTANCE, brownLabel);

		addImage(gameControl, toolkit, GreenCamp.INSTANCE);
		Label greenLabel = addLabel(gameControl, toolkit, "Wolne");
		addSeatButton(gameControl, toolkit, GreenCamp.INSTANCE, greenLabel);

		Label separator = toolkit.createSeparator(gameControl, SWT.HORIZONTAL);		
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd.horizontalSpan = 4;
		separator.setLayoutData(gd);

		this.gamePlayLabel = toolkit.createLabel(gameControl, "", SWT.WRAP);
		this.gamePlayLabel.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.horizontalSpan = 3;
		this.gamePlayLabel.setLayoutData(gd);
		
		Button dice = toolkit.createButton(gameControl, "", SWT.PUSH);
		dice.setImage(Images.DICE);
		dice.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				rolled = true;
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		this.gameResultLabel = toolkit.createLabel(gameControl, "¯eby zacz¹æ naciœnij zielony przycisk PLAY", SWT.WRAP);
		this.gameResultLabel.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
		gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd.verticalIndent = 5;
		gd.horizontalSpan = 4;
		gd.heightHint = 40;
		this.gameResultLabel.setLayoutData(gd);
	}

	private void addStartButton(Composite gameControl, FormToolkit toolkit) {
		final Button button = toolkit.createButton(gameControl, "", SWT.TOGGLE);		
		button.setImage(Images.PLAY);
		GridData gd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		gd.verticalSpan = 4;
		button.setLayoutData(gd);
		button.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				new Thread() {
					public void run() {
						try {
							control.start();
						} catch (NotEnoughPlayersException ex) {
							ex.printStackTrace();
							setErrorTextAsync("Za ma³o graczy.");
						} catch (GameAlreadyStartedException ex) {
							setErrorTextAsync("Za ma³o graczy.");
							setErrorTextAsync("Gra ju¿ siê rozpoczê³a!");
							ex.printStackTrace();
						}						
					}
				}.start();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}
	
	private void addSeatButton(final Composite gameControl, FormToolkit toolkit, final Camp camp, final Label label) {
		final Button button = toolkit.createButton(gameControl, "Usi¹dŸ", SWT.TOGGLE); 
		button.setText("Usi¹dŸ");
		button.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if (!button.getSelection()) {
					try {
						label.setText("Wolne");
						Player p = control.removePlayer(camp);
						button.setText("Usi¹dŸ");
						setGamePlayTextAsync(p.getName() + " " + GameControl.sex(p.getName(), "wsta³") + " od sto³u.");
					} catch (GameAlreadyStartedException ex) {
						setErrorTextAsync("Gra ju¿ siê zaczê³a, nie mo¿na wstaæ od sto³u!");
					}
					return;
				}
				
				InputDialog dialog = new InputDialog(gameControl.getShell(), "Wybór imienia", "Podaj imiê", "", null);
				if (dialog.open() == Window.OK) {
					String name = dialog.getValue();
					label.setText(name);
					try {
						control.addPlayer(name, camp);
						setGamePlayTextAsync(name + " " + GameControl.sex(name, "usiad³") + " do sto³u.");
					} catch (BoardNotRegisteredException e1) {
						setErrorTextAsync("B³¹d inicjalizacji");
						e1.printStackTrace();
					} catch (GameAlreadyStartedException ex) {
						setErrorTextAsync("Gra ju¿ siê zaczê³a, nie mo¿na do³¹czyæ do gry");
						ex.printStackTrace();
					}
					button.setText("Wstañ");
					//gameControl.pack();
					
				} else {
					button.setSelection(false);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}

	private Label addLabel(Composite gameControl, FormToolkit toolkit, String text) {
		Label label = toolkit.createLabel(gameControl, text);
		GridData gd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		gd.widthHint = 55;
		label.setLayoutData(gd);
		return label;
	}

	private void addImage(Composite gameControl,FormToolkit toolkit, Camp camp) {
		Canvas canvas = new Canvas(gameControl, SWT.NONE);
		GridData gd = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		gd.widthHint = 20;
		gd.heightHint = 20;
		canvas.setLayoutData(gd);
		canvas.addPaintListener(new CampPaintListener(camp));
		toolkit.adapt(canvas);
	}


	private class CampPaintListener implements PaintListener {
		private Camp camp;
		public CampPaintListener(Camp camp) {
			this.camp = camp;
		}
		@Override
		public void paintControl(PaintEvent e) {
			e.gc.drawImage(camp.getPawnImage(), 0, 0);
		}		
	}
	
	private void drawBoard(Composite grid) {

		addCells(grid, 2, RED_IMAGE, RED_LIGHT);
		new HorizontalCell(grid);
		addCells(grid, 2);
		addCell(grid, YELLOW_START, DEFAULT_LIGHT);
		new HorizontalCell(grid);		
		addCells(grid, 2, YELLOW_IMAGE, YELLOW_LIGHT);
		addCells(grid, 2, RED_IMAGE, RED_LIGHT);
		addCell(grid);
		addCell(grid, YELLOW_IMAGE, YELLOW_LIGHT);
		addCell(grid);
		addCells(grid, 2, YELLOW_IMAGE, YELLOW_LIGHT);

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
	
		addCells(grid, 2, GREEN_IMAGE, GREEN_LIGHT);
		new HorizontalCell(grid);
		addCell(grid);
		addCell(grid, GREEN_IMAGE, GREEN_LIGHT);
		addCell(grid);
		new HorizontalCell(grid);		
		addCells(grid, 2, BROWN_IMAGE, BROWN_LIGHT);
		
		addCells(grid, 2, GREEN_IMAGE, GREEN_LIGHT);
		addCell(grid, GREEN_START, DEFAULT_LIGHT);
		addCells(grid, 2);
		addCells(grid, 2, BROWN_IMAGE, BROWN_LIGHT);
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
		int id = IdMapping.INSTANCE.getActualValue();
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