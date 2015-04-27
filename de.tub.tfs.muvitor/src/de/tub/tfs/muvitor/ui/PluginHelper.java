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
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * This is a utility class for plugins in eclipse. It was extracted from
 * {@link MuvitorActivator} to avoid copying it into a plugin.<br />
 * <strong>Note:</strong> Manage this utility class with the activator of your
 * plugin so no calls will be made to a deactivated plugin.
 * 
 * @author Winzent Fischer (winzent.fischer@berlin.de)
 */
public class PluginHelper {
	
	private final AbstractUIPlugin plugin;
	
	/**
	 * Creates a new utility class for a given plugin.
	 * 
	 * @param plugin
	 *            The new Plugin for this <code>PluginUtil</code>
	 */
	public PluginHelper(final AbstractUIPlugin plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Returns an image descriptor for the image file at the given
	 * plugin-relative path.
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public ImageDescriptor getImageDescriptor(final String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(plugin.getBundle().getSymbolicName(),
				path);
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
	public String getUniqueExtensionAttributeValue(final String exPointID, final String attribID) {
		final String pluginName = plugin.getBundle().getSymbolicName();
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
	
	/**
	 * Convenience method for logging an error with this plugin
	 * 
	 * @param message
	 *            The message for the {@link IStatus} to be logged for the
	 *            plugin via {@link MuvitorActivator}.
	 * @param e
	 *            An optional Exception to be reported.
	 */
	public void logError(final String message, final Exception e) {
		final String pluginName = plugin.getBundle().getSymbolicName();
		final IStatus status = new Status(IStatus.ERROR, pluginName, IStatus.OK, message, e);
		plugin.getLog().log(status);
	}
}
