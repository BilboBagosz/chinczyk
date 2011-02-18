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

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
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
	private List<Button> buttons = new LinkedList<Button> ();
	
	private Label gameQuery;
	private StyledText gameResult;	
	
	private GameControl control;
	
	protected volatile boolean rolled = false;
	private Pawn selectedPawn = null;
	private Pawn pawnOver = null;
	private PawnSelectorListener listener = new PawnListener();
	
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
		control.setRequestHandler(new LocalRequestHandler());
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

		this.gameQuery = toolkit.createLabel(gameControl, "¯eby zacz¹æ naciœnij zielony przycisk PLAY", SWT.WRAP);
		this.gameQuery.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.verticalIndent = 5;
		gd.horizontalSpan = 4;
		gd.heightHint = 35;
		this.gameQuery.setLayoutData(gd);
		
		Button dice = toolkit.createButton(gameControl, "", SWT.PUSH);
		dice.setImage(Images.DICE);
		gd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		gd.horizontalSpan = 4;
		dice.setLayoutData(gd);
		dice.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				rolled = true;
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		this.gameResult = new StyledText(gameControl, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		toolkit.adapt(gameResult);
		this.gameResult.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN));
		gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd.verticalIndent = 5;
		gd.horizontalSpan = 4;
		gd.heightHint = 65;
		this.gameResult.setLayoutData(gd);
	}

	private void addStartButton(Composite gameControl, FormToolkit toolkit) {
		final Button button = toolkit.createButton(gameControl, "", SWT.PUSH);		
		button.setImage(Images.PLAY);
		GridData gd = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		gd.verticalSpan = 4;
		button.setLayoutData(gd);
		buttons.add(button);
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
							setErrorTextAsync("Gra ju¿ siê rozpoczê³a!");
							ex.printStackTrace();
						} catch (BoardNotRegisteredException ex) {
							setErrorTextAsync("Plansza nie zarejestrowana");
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
		buttons.add(button);
		button.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (control.isStarted()) {
					setErrorTextAsync("Gra ju¿ siê zaczê³a, nie mo¿na wstaæ od sto³u ani do niego do³¹czyæ !");
					return;
				}
				
				if (!button.getSelection()) {
					try {
						label.setText("Wolne");
						Player p = control.removePlayer(camp);
						button.setText("Usi¹dŸ");
						setResultTextAsync(p.getName() + " " + GameControl.sex(p.getName(), "wsta³") + " od sto³u.");
					} catch (GameAlreadyStartedException ex) {
						setErrorTextAsync("Gra ju¿ siê zaczê³a, nie mo¿na wstaæ od sto³u!");
					} catch (BoardNotRegisteredException ex) {
						setErrorTextAsync("Plansza nie zarejestrowana");
						ex.printStackTrace();
					}
					return;
				}
				
				InputDialog dialog = new InputDialog(gameControl.getShell(), "Wybór imienia", "Podaj imiê", "", null);
				if (dialog.open() == Window.OK) {
					String name = dialog.getValue();
					label.setText(name);
					try {
						control.addPlayer(name, camp);
						setResultTextAsync(name + " " + GameControl.sex(name, "usiad³") + " do sto³u.");
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
		new RegularCell(grid, null, null, null); // cannot add with addCell becuase it don't have ID
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
		RegularCell cell = new RegularCell(grid, cellImage, highlight, listener);
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
	
	private class PawnListener implements PawnSelectorListener {

		@Override
		public void pawnSelected(Pawn pawn) {
			setSelectedPawn(pawn);
		}

		@Override
		public void pawnOver(Pawn pawn) {
			setPawnOver(pawn);
		}		
	}

	private synchronized void setPawnOver(Pawn pawn) {
		if (this.pawnOver != null) {
			this.pawnOver.backlightRoad();
		}
		this.pawnOver = pawn;
	}
	
	private synchronized Pawn getPawnOver() {
		return this.pawnOver;
	}
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	}

	private void showMessage(String message, Color color) {
		int lenght = gameResult.getText().length();
		String newMessage = "> " + message + "\n";
		int messageLength = newMessage.length();
		gameResult.append(newMessage);
		StyleRange style = new StyleRange(lenght, messageLength, color, null);
		gameResult.setStyleRange(style);
		gameResult.setTopIndex(gameResult.getLineCount());
	}
	
	
	private void setResultTextAsync(final String message) {
		gameResult.getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				showMessage(message, Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN));
			}
		});
	}
	
	private void setGamePlayTextAsync(final String message) {
		gameQuery.getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				gameQuery.setText(message);
			}
		});
	}
	
	private void setErrorTextAsync(final String message) {
		gameResult.getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				showMessage(message, Display.getCurrent().getSystemColor(SWT.COLOR_RED));
			}
		});
	}
	
	
	private class LocalRequestHandler implements RequestHandler {
		
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

		@Override
		public void gameStarted() {
			enableButtons(false);
		}

		@Override
		public void gameEnded(List<Player> places) {
			StringBuilder sb = new StringBuilder("Gra zakoñczona \n");
			for (int i = 0; i < places.size(); i++) {
				sb.append(i + ". miejsce - " + places.get(i).getName() + "\n");
			}
			setResultTextAsync(sb.toString());
			enableButtons(true);
			setGamePlayTextAsync("¯eby zacz¹æ naciœnij zielony przycisk PLAY");
		}
		
		private void enableButtons(final boolean enable) {
			for (final Button button : buttons) {
				button.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						button.setEnabled(enable);
					}
				});
			}
		}

		@Override
		public Pawn requestMove(Player player, int movement) {
			while (true) {
				Pawn mouseOver = getPawnOver();
				if (mouseOver != null && player.containPawn(mouseOver)) {
					mouseOver.highlightRoad(movement);
				}
				Pawn selected = getSelectedPawn();
				if (selected != null && player.containPawn(getSelectedPawn())) {
					Pawn result = selected;
					selected.backlightRoad();
					setSelectedPawn(null);
					return result;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}				
			}
		}
	} //LocalRequestHandler	
	
	public synchronized Pawn getSelectedPawn() {
		return selectedPawn;
	}


	public synchronized void setSelectedPawn(Pawn selectedPawn) {
		this.selectedPawn = selectedPawn;
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
	} //CampPaintListener
}