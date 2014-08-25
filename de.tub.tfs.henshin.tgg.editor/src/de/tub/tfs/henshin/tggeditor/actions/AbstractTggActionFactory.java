/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.actions;

import org.eclipse.ui.IWorkbenchPart;

public interface AbstractTggActionFactory {

	public AbstractTGGAction createAction(IWorkbenchPart part);
	
	public String getActionID();
}
