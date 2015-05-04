package de.tub.tfs.henshin.tggeditor.actions.create.constraint;

import java.util.List;

import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tggeditor.commands.create.constraint.CreateNotCommand;

public class CreateNotAction extends SelectionAction {

	public static final String ID = "tggeditor.actions.create.CreateNotAction";
	private Formula formula;
	
	public CreateNotAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Add NOT");
		setToolTipText("Add NOT");
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
		Command command = new CreateNotCommand(this.formula);
		execute(command);
	}

}
