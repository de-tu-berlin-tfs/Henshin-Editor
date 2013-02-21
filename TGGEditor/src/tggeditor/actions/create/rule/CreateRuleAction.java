package tggeditor.actions.create.rule;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

import tgg.TGG;
import tggeditor.commands.create.rule.CreateRuleCommand;
import tggeditor.editparts.tree.rule.RuleFolderTreeEditPart;
import tggeditor.util.ModelUtil;
import tggeditor.util.NodeUtil;

public class CreateRuleAction extends SelectionAction {
	
	public static final String ID = "tggeditor.actions.create.CreateRuleAction";
	private Module transSys;

	public CreateRuleAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Create Rule");
		setToolTipText("Create Rule");
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
			if ((editpart instanceof RuleFolderTreeEditPart)) {
				transSys = (Module) editpart.getParent().getModel();
				return true;
			}
		}
		return false;
	}

	@Override
	public void run() {
		EList<Rule> rules = ModelUtil.getRules( transSys);
		int ruleNr = rules.size()+1;
		if (!rules.isEmpty()) {
			TGG tgg = NodeUtil.getLayoutSystem(rules.get(0));
			ruleNr -= tgg.getTRules().size();
		}
		
		InputDialog dialog = new InputDialog(
				getWorkbenchPart().getSite().getShell(), 
				"Create Rule", 
				"Name for a new Rule", 
				"Rule"+ruleNr, 
				null);
		dialog.open();
		if (dialog.getValue().startsWith("CR_")) {
			Shell shell = new Shell();
			MessageDialog.openInformation(shell, "Please choose another name", 
					"The string 'CR_' is used for automatically generated copies of rules for the critical pair analysis. " +
					"To avoid conflicts and for better overview, please choose another name for the rule not starting with 'CR_'");
			shell.dispose();
		}
		else if(dialog.getReturnCode() != Window.CANCEL){
			
			for (Rule r : rules) {
				if (dialog.getValue().equals(r.getName())) {
					Shell shell = new Shell();
					MessageDialog.openError(shell, "Rule already exists", 
							"A Rule with the name \""+r.getName()+"\" already exists.\n Please choose a different name.");
					shell.dispose();
					return;
				} 
			}
			System.out.println("Rule " + dialog.getValue() + " created in " + transSys.getName());
			Command command = new CreateRuleCommand(transSys, dialog.getValue());
			execute(command);
		}
		super.run();
	}
	
	
	
	
	

}
