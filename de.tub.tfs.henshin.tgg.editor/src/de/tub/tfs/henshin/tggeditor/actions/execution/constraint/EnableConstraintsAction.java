package de.tub.tfs.henshin.tggeditor.actions.execution.constraint;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tggeditor.commands.constraint.EnableConstraintsCommand;
import de.tub.tfs.henshin.tggeditor.editparts.tree.constraint.ConstraintFolder;
import de.tub.tfs.henshin.tggeditor.editparts.tree.constraint.ConstraintFolderTreeEditPart;

public class EnableConstraintsAction extends SelectionAction {

	public static final String ID = "tggeditor.actions.execution.constraint.EnableConstraintsAction";
	private TGG tgg;
	
	public EnableConstraintsAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Enable all Constraints");
		setToolTipText("Enable all Constraints");
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
		Command command = new EnableConstraintsCommand(tgg);
		execute(command);
	}

}
