package de.tub.tfs.henshin.tggeditor.actions.create.constraint;

import java.util.List;

import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tggeditor.commands.create.constraint.CreateAndCommand;

public class CreateAndAction extends SelectionAction {

	public static final String ID = "tggeditor.actions.create.CreateAndAction";
	private Formula formula;
	
	public CreateAndAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Add AND");
		setToolTipText("Add AND");
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
			if ((editpart.getModel() instanceof Formula)) {
				this.formula = (Formula)editpart.getModel();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void run() {
		Command command = new CreateAndCommand(this.formula);
		execute(command);
	}

}
