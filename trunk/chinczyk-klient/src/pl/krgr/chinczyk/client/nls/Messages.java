package pl.krgr.chinczyk.client.nls;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "pl.krgr.chinczyk.client.nls.messages"; //$NON-NLS-1$
	public static String ApplicationWorkbenchWindowAdvisor_ApplicationTitle;
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
