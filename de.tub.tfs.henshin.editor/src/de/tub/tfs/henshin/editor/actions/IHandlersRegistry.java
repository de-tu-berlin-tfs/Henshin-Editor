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
	 * @return
	 */
	public IAction getHandler(String id, Class<?> target);

	/**
	 * @param id
	 */
	public void registerHandler(IAction handler);

	/**
	 * @param handler
	 * @param target
	 */
	public void registerHandler(IAction handler, Class<?> target, String id);

	/**
	 * @return
	 */
	public IWorkbenchPart getWorkbenchPart();
}
