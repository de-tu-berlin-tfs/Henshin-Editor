package de.tub.tfs.henshin.tggeditor.actions.execution.constraint;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tggeditor.commands.constraint.DisableConstraintsCommand;
import de.tub.tfs.henshin.tggeditor.editparts.tree.constraint.ConstraintFolder;
import de.tub.tfs.henshin.tggeditor.editparts.tree.constraint.ConstraintFolderTreeEditPart;

public class DisableConstraintsAction extends SelectionAction {

	public static final String ID = "tggeditor.actions.execution.constraint.DisableConstraintsAction";
	private TGG tgg;
	
	public DisableConstraintsAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Disable all Constraints");
		setToolTipText("Disable all Constraints");
	}

	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selecObject = selectedObjects.get(0);
				
		if ((selecObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selecObject;
			if ((editpart instanceof ConstraintFolderTreeEditPart)) {
				tgg = ((ConstraintFolder)editpart.getModel()).getTgg();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void run() {
		Command command = new DisableConstraintsCommand(tgg);
		execute(command);
	}

}
