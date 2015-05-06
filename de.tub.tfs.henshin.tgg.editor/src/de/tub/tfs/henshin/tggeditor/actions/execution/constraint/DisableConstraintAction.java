package de.tub.tfs.henshin.tggeditor.actions.execution.constraint;

import java.util.List;

import org.eclipse.emf.henshin.model.Constraint;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tggeditor.commands.constraint.DisableConstraintCommand;
import de.tub.tfs.henshin.tggeditor.editparts.tree.constraint.ConstraintFolder;
import de.tub.tfs.henshin.tggeditor.editparts.tree.constraint.ConstraintTreeEditPart;

public class DisableConstraintAction extends SelectionAction {

	public static final String ID = "tggeditor.actions.execution.constraint.DisableConstraint";
	private TGG tgg;
	private Constraint constraint;
	
	public DisableConstraintAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Disable Constraint");
		setToolTipText("Disable Constraint");
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
			if ((editpart instanceof ConstraintTreeEditPart)) {
				tgg = ((ConstraintFolder)editpart.getParent().getModel()).getTgg();
				constraint = ((ConstraintTreeEditPart) editpart).getCastedModel();
				return true;
			}
		}
		return false;
	}

	@Override
	public void run() {
		Command command = new DisableConstraintCommand(tgg, constraint);
		execute(command);
	}
	
}
