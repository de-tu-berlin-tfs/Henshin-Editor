package tggeditor.actions.create.rule;

import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPart;

import tggeditor.commands.create.rule.CreateNACCommand;
import tggeditor.editparts.tree.rule.RuleTreeEditPart;

public class CreateNACAction extends SelectionAction {
	
	public static final String ID = "tggeditor.actions.create.CreateNACAction";
	private Rule rule;
	
	public CreateNACAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Create NAC");
		setToolTipText("Create NAC");
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
			if ((editpart instanceof RuleTreeEditPart)) {
				rule = (Rule) editpart.getModel();
				return true;
			}			
		}
		return false;
	}


	@Override
	public void run() {
	
		int nacNr = 1;
		if (rule.getLhs().getFormula() != null) {
			TreeIterator<EObject> iter = rule.getLhs().getFormula().eAllContents();
			while (iter.hasNext()) {
				EObject o = iter.next();
				if (o instanceof NestedCondition) {
					nacNr += 1;
				}
			}
		}
		
		InputDialog dialog = new InputDialog(
				getWorkbenchPart().getSite().getShell(), 
				"Create NAC", 
				"Name for a new NAC", 
				"NAC"+nacNr, 
				null);
		dialog.open();
		if(dialog.getReturnCode() != Window.CANCEL){
			System.out.println("NAC " + dialog.getValue() + " created in " + rule);
			Command command = new CreateNACCommand(rule, dialog.getValue());
			execute(command);
		}
		super.run();
	}
}
