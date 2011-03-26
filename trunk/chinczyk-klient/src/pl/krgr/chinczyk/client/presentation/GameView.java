package pl.krgr.chinczyk.client.presentation;

import static pl.krgr.chinczyk.client.presentation.Images.BROWN_IMAGE;
import static pl.krgr.chinczyk.client.presentation.Images.BROWN_START;
import static pl.krgr.chinczyk.client.presentation.Images.DEFAULT_IMAGE;
import static pl.krgr.chinczyk.client.presentation.Images.GREEN_IMAGE;
import static pl.krgr.chinczyk.client.presentation.Images.GREEN_START;
import static pl.krgr.chinczyk.client.presentation.Images.RED_IMAGE;
import static pl.krgr.chinczyk.client.presentation.Images.RED_START;
import static pl.krgr.chinczyk.client.presentation.Images.YELLOW_IMAGE;
import static pl.krgr.chinczyk.client.presentation.Images.YELLOW_START;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;

import pl.krgr.chinczyk.client.control.GameControlProxy;
import pl.krgr.chinczyk.client.network.CallBackEvent;
import pl.krgr.chinczyk.client.nls.Messages;
import pl.krgr.chinczyk.control.BoardNotRegisteredException;
import pl.krgr.chinczyk.control.BoardNotValidException;
import pl.krgr.chinczyk.control.GameAlreadyStartedException;
import pl.krgr.chinczyk.control.GameControl;
import pl.krgr.chinczyk.control.GameControlImpl;
import pl.krgr.chinczyk.control.NotEnoughPlayersException;
import pl.krgr.chinczyk.control.PlayerAlreadyRegisteredException;
import pl.krgr.chinczyk.control.PlayerNotReadyException;
import pl.krgr.chinczyk.control.RequestHandler;
import pl.krgr.chinczyk.model.BrownCamp;
import pl.krgr.chinczyk.model.Camp;
import pl.krgr.chinczyk.model.Cell;
import pl.krgr.chinczyk.model.ChangeListener;
import pl.krgr.chinczyk.model.GreenCamp;
import pl.krgr.chinczyk.model.IdMapping;
import pl.krgr.chinczyk.model.Pawn;
import pl.krgr.chinczyk.model.Player;
import pl.krgr.chinczyk.model.RedCamp;
import pl.krgr.chinczyk.model.YellowCamp;

public class GameView implements ChangeListener {
	
//	public static final String ID = "pl.krgr.chinczyk.client.presentation.MainView.view"; //$NON-NLS-1$
	
	private static final int GAME_RESULT_HEIGHT = 65;
	private static final int GAME_QUERY_HEIGHHT = 35;
	private static final int TOP_MARGIN = 40;
	private static final int NR_OF_COLUMNS = 11;
	
	private Map<Integer, Cell> boardMap = new HashMap<Integer, Cell> ();	
	private List<Button> buttons = new ArrayList<Button> ();
	
	private Label gameQuery;
	private StyledText gameResult;	
	
	private GameControl control;
	
	//wait for a player to press roll button
	protected volatile boolean rolled = false;
	private Pawn selectedPawn = null;
	private Pawn pawnOver = null;
	private PawnSelectorListener listener = new PawnListener();
	private Label redLabel;
	//private Shell shell;
	private Label yellowLabel;
	private Label brownLabel;
	private Label greenLabel;
	private RequestHandler requestHandler = new LocalRequestHandler();
	
	public GameView(Shell parent, GameControl control) {
		//this.shell = parent;
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
			
		RowLayout rowLayout = new RowLayout();
		rowLayout.marginTop = TOP_MARGIN;
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
		this.control = control; 		
		control.setRequestHandler(requestHandler);
		if (control instanceof GameControlProxy) {
			((GameControlProxy)this.control).setGameView(this);
		}
		try {
			control.registerBoard(boardMap);
		} catch (BoardNotValidException e) {
			setErrorTextAsync(Messages.GameView_InternalError);
			e.printStackTrace();
		} catch (GameAlreadyStartedException e) {
			setErrorTextAsync(Messages.GameView_GameAlreadyStarted);
			e.printStackTrace();
		}
	}
	
	private void drawControls(Composite gameControl, FormToolkit toolkit) {
		
		addImage(gameControl, toolkit, RedCamp.INSTANCE);
		redLabel = addLabel(gameControl, toolkit, Messages.GameView_FreeLabel);
		addSeatButton(gameControl, toolkit, RedCamp.INSTANCE, redLabel);
		addStartButton(gameControl, toolkit);
		
		addImage(gameControl, toolkit, YellowCamp.INSTANCE);
		yellowLabel = addLabel(gameControl, toolkit, Messages.GameView_FreeLabel);
		addSeatButton(gameControl, toolkit, YellowCamp.INSTANCE, yellowLabel);
		
		addImage(gameControl, toolkit, BrownCamp.INSTANCE);
		brownLabel = addLabel(gameControl, toolkit, Messages.GameView_FreeLabel);
		addSeatButton(gameControl, toolkit, BrownCamp.INSTANCE, brownLabel);

		addImage(gameControl, toolkit, GreenCamp.INSTANCE);
		greenLabel = addLabel(gameControl, toolkit, Messages.GameView_FreeLabel);
		addSeatButton(gameControl, toolkit, GreenCamp.INSTANCE, greenLabel);

		Label separator = toolkit.createSeparator(gameControl, SWT.HORIZONTAL);		
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd.horizontalSpan = 4;
		separator.setLayoutData(gd);

		this.gameQuery = toolkit.createLabel(gameControl, Messages.GameView_StartMessage, SWT.WRAP);
		this.gameQuery.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.verticalIndent = 5;
		gd.horizontalSpan = 4;
		gd.heightHint = GAME_QUERY_HEIGHHT;
		this.gameQuery.setLayoutData(gd);
		
		Button dice = toolkit.createButton(gameControl, "", SWT.PUSH); //$NON-NLS-1$
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
		gd.heightHint = GAME_RESULT_HEIGHT;
		this.gameResult.setLayoutData(gd);
	}

	private void addStartButton(Composite gameControl, FormToolkit toolkit) {
		final Button button = toolkit.createButton(gameControl, "", SWT.PUSH);		 //$NON-NLS-1$
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
							control.prestart();
							control.start();
						} catch (NotEnoughPlayersException ex) {
							ex.printStackTrace();
							setErrorTextAsync(Messages.GameView_NotEnoughPlayers);
						} catch (GameAlreadyStartedException ex) {
							setErrorTextAsync(Messages.GameView_GameAlreadyStarted);
							ex.printStackTrace();
						} catch (BoardNotRegisteredException ex) {
							setErrorTextAsync(Messages.GameView_BoardNotRegistered);
							ex.printStackTrace();
						} catch (PlayerNotReadyException e) {
							setErrorTextAsync("Nie wszyscy gracze s¹ gotowi do gry.");
							e.printStackTrace();
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
		final Button button = toolkit.createButton(gameControl, Messages.GameView_TakeSeat, SWT.TOGGLE); 
		button.setText(Messages.GameView_TakeSeat);
		buttons.add(button);
		button.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (control.isStarted()) {
					setErrorTextAsync(Messages.GameView_CannotStandUpOrJoin);
					return;
				}
				
				if (!button.getSelection()) {
					try {
						Player p = control.removePlayer(camp);
						label.setText(Messages.GameView_Free);
						button.setText(Messages.GameView_TakeSeat);
						setResultTextAsync(p.getName() + " " + GameControlImpl.sex(p.getName(), "wsta³") + " od sto³u."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					} catch (GameAlreadyStartedException ex) {
						setErrorTextAsync(Messages.GameView_CannoStandUp);
						button.setSelection(true);
					} catch (BoardNotRegisteredException ex) {
						setErrorTextAsync(Messages.GameView_BoardNotRegistered);
						ex.printStackTrace();
						button.setSelection(true);
					}
					return;
				}
				
				InputDialog dialog = new InputDialog(gameControl.getShell(), Messages.GameView_ChooseName, Messages.GameView_EnterName, "", null); //$NON-NLS-3$ //$NON-NLS-1$
				if (dialog.open() == Window.OK) {
					String name = dialog.getValue();
					try {
						control.addPlayer(name, camp);
						setResultTextAsync(name + " " + GameControlImpl.sex(name, Messages.GameView_Seat) + Messages.GameView_OnTable); //$NON-NLS-1$
					} catch (BoardNotRegisteredException e1) {
						setErrorTextAsync(Messages.GameView_InitException);
						e1.printStackTrace();
						button.setSelection(false);
						return;
					} catch (GameAlreadyStartedException ex) {
						setErrorTextAsync(Messages.GameView_CannotJoin);
						ex.printStackTrace();
						button.setSelection(false);
						return;
					} catch (PlayerAlreadyRegisteredException ex) {
						setErrorTextAsync("Gracz ju¿ zosta³ zarejestrowany");
						ex.printStackTrace();
						button.setSelection(false);
						return;
					}
					label.setText(name);
					button.setText(Messages.GameView_StandUp);
				} else {
					button.setSelection(false);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}

	public void setPlayerInfo(final Player player) {
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				Camp camp = player.getCamp();
				int index = 0;
				if (camp instanceof RedCamp) {			
					index = 0;
					redLabel.setText(player.getName());
				} else if (camp instanceof YellowCamp) {
					index = 2;
					yellowLabel.setText(player.getName());
				} else if (camp instanceof BrownCamp) {
					index = 3;
					brownLabel.setText(player.getName());			
				} else if (camp instanceof GreenCamp) {
					index = 4;
					greenLabel.setText(player.getName());			
				}
				Button button = buttons.get(index);
				button.setSelection(true);
				button.setText(Messages.GameView_StandUp);
			}
		});
	}

	public void clearPlayerInfo(final Camp camp) {
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				int index = 0;
				if (camp instanceof RedCamp) {			
					index = 0;
					redLabel.setText(Messages.GameView_FreeLabel);
				} else if (camp instanceof YellowCamp) {
					index = 2;
					yellowLabel.setText(Messages.GameView_FreeLabel);
				} else if (camp instanceof BrownCamp) {
					index = 3;
					brownLabel.setText(Messages.GameView_FreeLabel);			
				} else if (camp instanceof GreenCamp) {
					index = 4;
					greenLabel.setText(Messages.GameView_FreeLabel);			
				}
				Button button = buttons.get(index);
				button.setSelection(false);
				button.setText(Messages.GameView_TakeSeat);
			}
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
		IdMapping.INSTANCE.reset();

		addCells(grid, 2, RED_IMAGE);
		new HorizontalCell(grid);
		addCells(grid, 2);
		addCell(grid, YELLOW_START);
		new HorizontalCell(grid);		
		addCells(grid, 2, YELLOW_IMAGE);
		addCells(grid, 2, RED_IMAGE);
		addCell(grid);
		addCell(grid, YELLOW_IMAGE);
		addCell(grid);
		addCells(grid, 2, YELLOW_IMAGE);

		addHorizontals(grid, YELLOW_IMAGE);
		
		addCell(grid, RED_START);
		addCells(grid, 4);
		addCell(grid, YELLOW_IMAGE);
		addCells(grid, 6);
		addCells(grid, 4, RED_IMAGE);
		new RegularCell(grid, null, null); // cannot add with addCell becuase it don't have ID
		addCells(grid, 4, BROWN_IMAGE);
		addCells(grid, 6);
		addCell(grid, GREEN_IMAGE);
		addCells(grid, 4);
		addCell(grid, BROWN_START);

		addHorizontals(grid, GREEN_IMAGE);
	
		addCells(grid, 2, GREEN_IMAGE);
		new HorizontalCell(grid);
		addCell(grid);
		addCell(grid, GREEN_IMAGE);
		addCell(grid);
		new HorizontalCell(grid);		
		addCells(grid, 2, BROWN_IMAGE);
		
		addCells(grid, 2, GREEN_IMAGE);
		addCell(grid, GREEN_START);
		addCells(grid, 2);
		addCells(grid, 2, BROWN_IMAGE);
	}

	private void addHorizontals(Composite grid, Image image) {
		new HorizontalBigCell(grid);
		addCell(grid);
		addCell(grid, image);
		addCell(grid);
		new HorizontalBigCell(grid);
		addCell(grid);
		addCell(grid, image);
		addCell(grid);
	}

	private void addCells(Composite grid, int number) {
		addCells(grid, number, DEFAULT_IMAGE);
	}
	
	private void addCells(Composite grid, int number, Image image) {
		for (int i = 0; i < number; i++) {
			addCell(grid, image);
		}
	}
	
	private void addCell(Composite grid, Image cellImage, Pawn pawn) {
		RegularCell cell = new RegularCell(grid, cellImage, listener);
		int id = IdMapping.INSTANCE.getActualValue();
		cell.setId(id);
		boardMap.put(id, cell);
		if (pawn != null) {
			cell.setPawn(pawn);
			pawn.setBaseCell(cell);
		}
	}
	
	private void addCell(Composite grid, Image cellImage) {
		addCell(grid, cellImage, null);
	}
	
	private void addCell(Composite grid) {
		addCell(grid, DEFAULT_IMAGE, null);
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

	private void showMessage(String message, Color color) {
		int lenght = gameResult.getText().length();
		String newMessage = Messages.GameView_Marker + message + "\n"; //$NON-NLS-2$ //$NON-NLS-1$
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
			StringBuilder sb = new StringBuilder(Messages.GameView_GameEnd);
			for (int i = 0; i < places.size(); i++) {
				sb.append((i+1) + Messages.GameView_Place + places.get(i).getName() + "\n"); //$NON-NLS-2$ //$NON-NLS-1$
			}
			setResultTextAsync(sb.toString());
			enableButtons(true);
			setGamePlayTextAsync(Messages.GameView_StartMessage);
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
			player.highlightEnabled(movement);
			while (true) {
				Pawn mouseOver = getPawnOver();
				if (mouseOver != null && player.containPawn(mouseOver)) {
					mouseOver.highlightRoad(movement);
				}
				Pawn selected = getSelectedPawn();
				if (selected != null && player.containPawn(selected)) {
					if (!selected.canMove(movement)) continue;
					Pawn result = selected;
					selected.backlightRoad();
					setSelectedPawn(null);
					player.backlightAll();
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
			e.gc.drawImage(AbstractCell.getPawnImage(camp.getPawnImage()), 0, 0);
		}		
	} //CampPaintListener

	@Override
	public void notifyChange(Object o) {
		if (control instanceof GameControlProxy) {
			GameControlProxy proxy = (GameControlProxy) control;
			if (o instanceof Room) {			
				Room room = (Room) o;
				if (room.getId() == proxy.getRoom().getId()) {
					proxy.synchronizePlayers(false);
				}
			}
		}
		if (o instanceof GameResultMessage) {
			requestHandler.handleResultMessage(((GameResultMessage) o).getMessage());
		}
		if (o instanceof GameStartedMessage) {
			requestHandler.gameStarted();
		}
		if (o instanceof GameQueryMessage) {
			requestHandler.handleQueryMessage(((GameQueryMessage) o).getMessage());
		}
		if (o instanceof GameErrorMessage) {
			requestHandler.handleErrorMessage(((GameErrorMessage) o).getMessage());
		}
		if (o instanceof RequestRollMessage) {
			requestHandler.requestRoll(null);
		}
		if (o instanceof RequestMoveMessage) {
			RequestMoveMessage request = (RequestMoveMessage) o;
			Player p = null;
			for (Player player : control.getPlayers()) {
				if (player != null && player.getCamp() == request.getCamp()) {
					p = player;
					break;
				}
			}
			Pawn pawn = requestHandler.requestMove(p, request.getMovement());
			request.getHandlerCallback().commandExecuted(new CallBackEvent(true, Integer.toString(pawn.getActualPosition())));
		}
	}
}