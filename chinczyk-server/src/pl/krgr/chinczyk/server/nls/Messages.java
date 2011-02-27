package pl.krgr.chinczyk.server.nls;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "pl.krgr.chinczyk.server.nls.messages"; //$NON-NLS-1$
	public static String ServerImpl_CannotConnect;
	public static String ServerImpl_CannotStart;
	public static String ServerImpl_ServerAlreadyStarted;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
