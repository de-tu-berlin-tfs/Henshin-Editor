package tggeditor.actions.create.graph;


import java.util.List;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.ui.IWorkbenchPart;

import tggeditor.commands.create.CreateNodeCommand;
import tggeditor.editparts.tree.GraphTreeEditPart;

public class CreateNodeAction extends SelectionAction {

	public static final String ID = "tggeditor.actions.create.CreateNodeAction";
	private Graph graph;
	
	public CreateNodeAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Create Node");
		setToolTipText("Create Node in the selected Graph");
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
			if ((editpart instanceof GraphTreeEditPart)) {
				graph = (Graph) editpart.getModel();
				return true;
			}
		}
		return false;
	}
	
	@Override 
	public void run() {
		InputDialog dialog = new InputDialog(
				getWorkbenchPart().getSite().getShell(),
				"CreateNode",
				"Name for new node: ",
				"",
				null);
		dialog.open();
		if (dialog.getReturnCode() != InputDialog.CANCEL) {
			System.out.println("Node " + dialog.getValue() + " created in " + graph.getName());
			Command command = new CreateNodeCommand(graph, dialog.getValue(), null);
			execute(command);
		}
	}

}
