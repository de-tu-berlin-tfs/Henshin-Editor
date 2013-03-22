package de.tub.tfs.henshin.tggeditor.actions.create.graph;


import java.util.List;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tggeditor.commands.create.CreateGraphCommand;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphFolderTreeEditPart;


public class CreateGraphAction extends SelectionAction {

	public static final String ID = "tggeditor.actions.create.CreateGraphAction";
	private Module transSys;
	
	public CreateGraphAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Create Graph");
		setToolTipText("Create Graph");
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
			if ((editpart instanceof GraphFolderTreeEditPart)) {
				transSys = (Module) editpart.getParent().getModel();
				return true;
			}
		}
		return false;
	}
	
	@Override 
	public void run() {
		
		int graphNr = transSys.getInstances().size()+1;
		
		InputDialog dialog = new InputDialog(
				getWorkbenchPart().getSite().getShell(),
				"CreateGraph",
				"Name for new graph: ",
				"Graph"+graphNr,
				null);
		dialog.open();
		if (dialog.getValue().startsWith("(")) {
			Shell shell = new Shell();
			MessageDialog.openInformation(shell, "Please choose another name", 
					"You are not allowed to use an opening brace for a graph name. Please choose another name without special characters.");
			shell.dispose();
		}
		else if (dialog.getReturnCode() != InputDialog.CANCEL) {
			System.out.println("Graph " + dialog.getValue() + "erzeugt in" + transSys);
			Command command = new CreateGraphCommand(transSys, dialog.getValue());
			execute(command);
		}
	}

}
