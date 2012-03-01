/**
 * 
 */
package de.tub.tfs.henshin.editor.ui.dialog.resources;

import org.eclipse.core.resources.IResource;

/**
 * The Interface IResourceValidator.
 * 
 * @author nam
 */
public interface IResourceValidator {

	/**
	 * Checks if is valid.
	 * 
	 * @param obj
	 *            the obj
	 * @return true, if is valid
	 */
	public boolean isValid(IResource obj);
}
