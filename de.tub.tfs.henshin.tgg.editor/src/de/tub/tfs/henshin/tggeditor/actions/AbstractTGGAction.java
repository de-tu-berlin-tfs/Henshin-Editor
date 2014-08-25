/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.actions;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

public class AbstractTGGAction extends SelectionAction {

	public static final String ID ="";
	
	
	public AbstractTGGAction(IWorkbenchPart part) {
		super(part);
	}

	@Override
	protected boolean calculateEnabled() {
		return false;
	}

	@Override
	public void run() {
		super.run();
	}
}
