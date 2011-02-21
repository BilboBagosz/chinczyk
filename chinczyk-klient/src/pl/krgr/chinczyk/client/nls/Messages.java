package pl.krgr.chinczyk.client.nls;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "pl.krgr.chinczyk.client.nls.messages"; //$NON-NLS-1$
	public static String ApplicationWorkbenchWindowAdvisor_ApplicationTitle;
	public static String GameControl_And;
	public static String GameControl_CannotMove;
	public static String GameControl_Congratulation;
	public static String GameControl_ExtraThrow;
	public static String GameControl_Finished;
	public static String GameControl_GameEnd;
	public static String GameControl_GameOn;
	public static String GameControl_Place;
	public static String GameControl_Players;
	public static String GameControl_PlayersSetSequence;
	public static String GameControl_RollDice;
	public static String GameControl_Starts;
	public static String GameControl_Thrown;
	public static String GameControl_ThrownSameResult;
	public static String GameControl_YourMove;
	public static String GameView_BoardNotRegistered;
	public static String GameView_CannoStandUp;
	public static String GameView_CannotJoin;
	public static String GameView_CannotStandUpOrJoin;
	public static String GameView_ChooseName;
	public static String GameView_EnterName;
	public static String GameView_Free;
	public static String GameView_FreeLabel;
	public static String GameView_GameAlreadyStarted;
	public static String GameView_GameEnd;
	public static String GameView_InitException;
	public static String GameView_InternalError;
	public static String GameView_Marker;
	public static String GameView_NotEnoughPlayers;
	public static String GameView_OnTable;
	public static String GameView_Place;
	public static String GameView_Seat;
	public static String GameView_StandUp;
	public static String GameView_StartMessage;
	public static String GameView_TakeSeat;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
