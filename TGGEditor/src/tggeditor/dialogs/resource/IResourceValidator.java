/**
 * 
 */
package tggeditor.dialogs.resource;

import org.eclipse.core.resources.IResource;

/**
 * The Interface IResourceValidator.
 *
 */
public interface IResourceValidator {
	
	/**
	 * Checks if is valid.
	 *
	 * @param obj the obj
	 * @return true, if is valid
	 */
	public boolean isValid(IResource obj);
}
