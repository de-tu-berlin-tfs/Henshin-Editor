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
