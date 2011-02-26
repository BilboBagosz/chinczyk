package pl.krgr.chinczyk.control.nls;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "pl.krgr.chinczyk.nls.messages"; //$NON-NLS-1$
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
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
