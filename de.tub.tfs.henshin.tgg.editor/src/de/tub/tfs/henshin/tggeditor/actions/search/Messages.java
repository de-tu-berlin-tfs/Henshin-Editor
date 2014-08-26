package de.tub.tfs.henshin.tggeditor.actions.search;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "de.tub.tfs.henshin.tggeditor.actions.search.messages"; //$NON-NLS-1$
	public static String TypeSearchAction_Text;
	public static String TypeSearchAction_ToolTipText;
	public static String TypeSearchAction_SearchForNodeTypeTitle;
	public static String TypeSearchAction_SearchForNodeTypeMsg;
	public static String ModelSearchAction_Text;
	public static String ModelSearchAction_ToolTipText;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}


	private Messages() {
	}
}
