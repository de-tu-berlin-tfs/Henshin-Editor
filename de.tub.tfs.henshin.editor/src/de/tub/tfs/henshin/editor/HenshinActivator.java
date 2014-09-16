package de.tub.tfs.henshin.editor;

import org.osgi.framework.BundleContext;

import de.tub.tfs.muvitor.ui.MuvitorActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class HenshinActivator extends MuvitorActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "de.tub.tfs.henshin.editor"; //$NON-NLS-1$

	public static final String ICON_PATH = "icons/";

	// The shared instance
	private static HenshinActivator plugin;

	/**
	 * The constructor
	 */
	public HenshinActivator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static HenshinActivator getDefault() {
		return plugin;
	}
}
