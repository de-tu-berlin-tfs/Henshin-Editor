package tggeditor;

import org.osgi.framework.BundleContext;

import de.tub.tfs.muvitor.ui.MuvitorActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class TGGEditorActivator extends MuvitorActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "TGGEditor"; //$NON-NLS-1$
	
	public static final String ICON_PATH = "icons/";

	// The shared instance
	private static TGGEditorActivator plugin;
	
	/**
	 * The constructor
	 */
	public TGGEditorActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static TGGEditorActivator getDefault() {
		return plugin;
	}
}
