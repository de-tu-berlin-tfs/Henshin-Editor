package tggeditor;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
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
	
	/**
	 * Convenience method to access the extension defined in plugin.xml.
	 * 
	 * @param exPointID
	 *            The extension to be looked at.
	 * @param attribID
	 *            The unique attribute whose value is requested.
	 * @return The value of the specified attribute.
	 */
	static public String getUniqueExtensionAttributeValue(final String exPointID,
			final String attribID) {
		final String pluginName = getDefault().getBundle().getSymbolicName();
		String attrValue = MuvitorActivator.getUniqueExtensionAttributeValue(exPointID,attribID,pluginName);
		return attrValue;
		
	}
	
}
