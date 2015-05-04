package de.tub.tfs.henshin.tggeditor.actions.create.constraint;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tggeditor.commands.create.constraint.CreateConstraintCommand;
import de.tub.tfs.henshin.tggeditor.editparts.tree.constraint.ConstraintFolder;
import de.tub.tfs.henshin.tggeditor.editparts.tree.constraint.ConstraintFolderTreeEditPart;

public class CreateSourceConstraintAction extends SelectionAction {

	public static final String ID = "tggeditor.actions.create.CreateSourceConstraintAction";
	private TGG tgg;
	
	public CreateSourceConstraintAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Create Source Constraint");
		setToolTipText("Create Source Constraint");
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
		InputDialog dialog = new InputDialog(
				getWorkbenchPart().getSite().getShell(),
				"Create Source Constraint",
				"Name for new graph constraint: ",
				"Constraint"+(tgg.getConstraints().size()+1),
				null);
		dialog.open();
		if (dialog.getValue() != null && dialog.getReturnCode() != InputDialog.CANCEL) {
			Command command = new CreateConstraintCommand(tgg, dialog.getValue(), TripleComponent.SOURCE);
			execute(command);
		}
	}

}
