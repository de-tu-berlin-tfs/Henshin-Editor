/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
package de.tub.tfs.muvitor.ui;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in's life cycle. It allows the editor
 * to access this plug-in's properties (defined in the plugin.xml), e.g. the
 * perspective and editor file extension. Also, the editor can store preference
 * values or use the plugin's image registry to cache images.
 * 
 * @author Tony Modica
 */
public class MuvitorActivator extends AbstractUIPlugin {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "de.tub.tfs.muvitor";
	
	/**
	 * The shared instance
	 */
	private static MuvitorActivator plugin;
	
	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static MuvitorActivator getDefault() {
		return plugin;
	}
	
	/**
	 * Returns an image descriptor for the image file at this plugins relative
	 * path.
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(final String path) {
		ImageRegistry reg = plugin.getImageRegistry();
		ImageDescriptor imgDescr = reg.getDescriptor(path);
		if (imgDescr == null) {
			imgDescr = imageDescriptorFromPlugin(PLUGIN_ID, path);
			if (imgDescr != null) reg.put(path, imgDescr);
		}// if
		return imgDescr;
	}// getImageDescriptor
	
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
		return
				getUniqueExtensionAttributeValue(exPointID,attribID,pluginName);
	}
	
	/**
	 * Convenience method for logging an error with this plugin
	 * 
	 * @param message
	 *            The message for the {@link IStatus} to be logged for the
	 *            plugin via {@link MuvitorActivator}.
	 * @param e
	 *            An optional Exception to be reported.
	 */
	static public void logError(final String message, final Exception e) {
		final String pluginName = getDefault().getBundle().getSymbolicName();
		final IStatus status = new Status(IStatus.ERROR, pluginName, IStatus.OK, message, e);
		getDefault().getLog().log(status);
	}
	
	/**
	 * The constructor
	 */
	public MuvitorActivator() {
		plugin = this;
	}
	
	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
	}
	
	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static String getUniqueExtensionAttributeValue(String exPointID,
			String attribID, String pluginName) {
		final IExtension[] extensions = Platform.getExtensionRegistry().getExtensions(pluginName);
		String attrValue = null;
		for (final IExtension extension : extensions) {
			// look for the given extension id
			if (extension.getExtensionPointUniqueIdentifier().equals(exPointID)) {
				final IConfigurationElement[] confElems = extension.getConfigurationElements();
				if (confElems.length != 1) {
					logError("The Plugin " + pluginName
							+ " does not specify a unique extension with ID " + exPointID, null);
				}
				attrValue = confElems[0].getAttribute(attribID);
				if (attrValue == null) {
					logError("The extension " + exPointID + " of the plugin " + pluginName
							+ " does not specify a unique attribute with ID " + attribID, null);
				} else {
					break;
				}
			}
		}
		return attrValue;

	}
	
}
