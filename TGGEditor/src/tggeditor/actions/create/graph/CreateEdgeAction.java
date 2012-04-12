package tggeditor.actions.create.graph;


import java.util.List;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.ui.IWorkbenchPart;

import tggeditor.editparts.tree.GraphTreeEditPart;

public class CreateEdgeAction extends SelectionAction {

	public static final String ID = "tggeditor.actions.create.CreateEdgeAction";
	private Graph graph;
	
	public CreateEdgeAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Create Edge");
		setToolTipText("Create Edge");
	}
	
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
				
		Object selectedObject = selectedObjects.get(0);
		// Continue if the selected object is from edit part
		if ((selectedObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selectedObject;

			// Continue if the edit part is from graph tree edit part
			if (editpart instanceof GraphTreeEditPart) {
				graph = (Graph) editpart.getModel();
				return true;
			}
/* folgendes ist für die grafische Oberlfäche und muss noch impl. werden*/
//			if (editpart instanceof GraphElementsTreeEditPart) {
//				graph = ((GraphElementsContainterEObject) editpart.getModel()).getModel();
//				return true;
//			}
		}
		return false;
	}
	
	@Override 
	public void run() {
		InputDialog dialog = new InputDialog(
				getWorkbenchPart().getSite().getShell(),
				"CreateEdge",
				"Name for new edge: ",
				"new Edge",
				null);
		dialog.open();
		if (dialog.getReturnCode() != InputDialog.CANCEL) {
			System.out.println("Edge " + dialog.getValue() + "erzeugt in" + graph);
//			CreateEdgeCommand command = new CreateEdgeCommand(graph, "", "", dialog.getValue());
//			execute(command);
		}
	}

}
