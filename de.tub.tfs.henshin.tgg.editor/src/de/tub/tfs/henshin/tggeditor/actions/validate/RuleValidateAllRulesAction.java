package de.tub.tfs.henshin.tggeditor.actions.validate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateFTRuleToolBarAction;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.ProcessRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteFTRuleCommand;
import de.tub.tfs.henshin.tggeditor.dialogs.ValidTestDialog;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleFolder;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleFolderTreeEditPart;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.muvitor.ui.IDUtil;


/**
 * The class GenerateFTRuleAction generates Forward-Translation-Rule from a simple Rule. The Action
 * is registered in the Contextmenu of the Tree Editor.
 * @see GenerateFTRuleToolBarAction
 * @see ProcessRuleCommand
 */
public class RuleValidateAllRulesAction extends SelectionAction {

	/**
	 * The fully qualified ID.
	 */
	public static final String ID = "tggeditor.actions.ValidateAllRulesAction";
	
	/** The Constant DESC for the description. */
	static private final String DESC = "Validate all Rules";

	/** The Constant TOOLTIP for the tooltip. */
	static private final String TOOLTIP = "Validates all Rules of a TGG";
	
	/**
	 * The rule which is used as base for the FT-Rule.
	 */
	private List<Unit> rules;
	
	
	
	
	/**
	 * the constructor
	 * @param part
	 */
	public RuleValidateAllRulesAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText(DESC);
		setToolTipText(TOOLTIP);
	}

	/** Is only enabled for the context menu of a rule.
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selectedObject = selectedObjects.get(0);
		if ((selectedObject instanceof RuleFolderTreeEditPart)) {
			EditPart editpart = (EditPart) selectedObject;
			IndependentUnit ruleFolder = (IndependentUnit) editpart.getModel();
			rules = ruleFolder.getSubUnits(true);
			return true;
			
		}
		return false;
	}
	
	/** 
	 * Executes the GenerateFTRuleCommand.
	 * @see ProcessRuleCommand
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		if (rules != null && !rules.isEmpty()) {
			IDUtil.getHostEditor(rules.get(0)).clearAllMarker();
			
			List<String> errorMessages = new ArrayList<String>();

			//errorMessages.add("=== Only Errors ===================================");
			//for (Unit r : rules) {
		    //   if (r instanceof Rule)
			//		RuleValidAction.checkRuleValid(errorMessages,(Rule) r,false);
			//}
			errorMessages.add("=== Errors and Warnings ===================================");
			
			for (Unit r : rules) {
				if (r instanceof Rule)
					RuleValidAction.checkRuleValid(errorMessages,(Rule) r,true);

		}

			openDialog(errorMessages);
		}
	}
	/**
	 * opens the dialog with the given error messages, if no error messages given 
	 * opens the dialog with a check message
	 * @param errorMessages
	 */
	protected void openDialog(List<String> errorMessages) {
		if (errorMessages.size() == 0) {
			errorMessages.add("Everything Ok!");
		}
		ValidTestDialog vD = new ValidTestDialog(getWorkbenchPart().getSite()
				.getShell(), SWT.NULL, errorMessages);
		vD.open();
	}
}
