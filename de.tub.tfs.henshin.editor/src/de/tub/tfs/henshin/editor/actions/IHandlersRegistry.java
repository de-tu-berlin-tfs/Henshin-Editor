/**
 * IHandlersProvider.java
 *
 * Created 19.12.2011 - 21:13:17
 */
package de.tub.tfs.henshin.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author nam
 * 
 */
public interface IHandlersRegistry {

	/**
	 * @param id
	 */
	public void registerHandler(IAction handler, String id);

	/**
	 * @param id
	 */
	public IAction getHandler(String id);

	/**
	 * @return
	 */
	public IWorkbenchPart getWorkbenchPart();
}
