package pl.krgr.chinczyk.client.nls;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "pl.krgr.chinczyk.client.nls.messages"; //$NON-NLS-1$
	public static String ApplicationWorkbenchWindowAdvisor_ApplicationTitle;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
