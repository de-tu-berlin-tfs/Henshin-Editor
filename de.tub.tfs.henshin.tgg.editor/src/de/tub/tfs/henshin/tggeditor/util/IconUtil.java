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
package de.tub.tfs.henshin.tggeditor.util;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import de.tub.tfs.henshin.tggeditor.TGGEditorActivator;
import de.tub.tfs.muvitor.ui.MuvitorActivator;

public class IconUtil {
	
	/**
	 * A string describing the folder containing the image files.
	 */
	private static final String defaultImagePath = "icons/";
	
	/**
	 * The plugin's {@link ImageRegistry} caching the images.
	 */
	private static final ImageRegistry reg = 
			TGGEditorActivator.getDefault().getImageRegistry();
	
	/**
	 * Returns the image descriptor from the plugin's image registry or directly
	 * from the file if it is not already registered.
	 * 
	 * @param filename
	 *            the filename of the icon
	 * @return an image descriptor corresponding to the icon
	 */
	final public static ImageDescriptor getDescriptor(final String filename) {
		ImageDescriptor result = reg.getDescriptor(filename);
		if (result != null) {
			return result;
		}
		result = MuvitorActivator.getImageDescriptor(defaultImagePath + filename);
		
		if (result == null) {
			result = AbstractUIPlugin.imageDescriptorFromPlugin(
					TGGEditorActivator.PLUGIN_ID,
					defaultImagePath + filename);
		}
		if (result != null) {
			reg.put(filename, result);
		}
		return result;
	}

	/**
	 * Returns the image descriptor from the plugin's image registry or directly
	 * from the file if it is not already registered.
	 * 
	 * @param filename
	 *            the filename of the icon
	 * @return an image descriptor corresponding to the icon
	 */
	final public static Image getIcon(final String filename) {
		final Image icon = reg.get(filename);
		
		if (icon != null) {
			return icon;
		}
		// create cached descriptor for plugin's image registry and then get
		// image
		getDescriptor(filename);
		return reg.get(filename);
	}
}
