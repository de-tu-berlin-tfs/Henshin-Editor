package de.tub.tfs.henshin.tggeditor.actions.create.rule;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateBTRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateFTRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.ProcessRuleCommand;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleTreeEditPart;
import de.tub.tfs.henshin.tggeditor.util.ModelUtil;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.dialogs.DialogUtil;


/**
 * The class GenerateFTRuleAction generates Forward-Translation-Rule from a simple Rule. The Action
 * is registered in the Contextmenu of the Tree Editor.
 * @see GenerateFTRuleToolBarAction
 * @see ProcessRuleCommand
 */
public class GenerateBTRuleAction extends SelectionAction {

	/**
	 * The fully qualified ID.
	 */
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateBTRuleAction";
	
	/** The Constant DESC for the description. */
	static private final String DESC = "Generate BT_Rule";

	/** The Constant TOOLTIP for the tooltip. */
	static private final String TOOLTIP = "Generates Backward translation for this TGG Rule";
	
	/**
	 * The rule which is used as base for the FT-Rule.
	 */
	protected Rule rule;
	
	/**
	 * the constructor
	 * @param part
	 */
	public GenerateBTRuleAction(IWorkbenchPart part) {
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
		if ((selectedObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selectedObject;
			if (editpart instanceof RuleTreeEditPart) {
				rule = (Rule) editpart.getModel();
				TGG layoutSystem = NodeUtil.getLayoutSystem(rule);
				if(layoutSystem == null) return false;
				EList<TRule> tRules = layoutSystem.getTRules();
				for(TRule temp: tRules) {
					if(temp.getRule().equals(rule)) {
						return false;
					}
				}
				return true;
			}
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
		if (rule == null) {
			rule = getRule();
		}
		IndependentUnit container = findContainer((IndependentUnit) ((Module)EcoreUtil.getRootContainer(rule)).getUnit("RuleFolder")  ,rule);
		ProcessRuleCommand command = new GenerateBTRuleCommand(rule,container);		
		super.execute(command);
	}
	
	/**
	 * Gets the rule.
	 *
	 * @return the rule
	 */
	private Rule getRule() {
		
		return DialogUtil.runRuleChoiceDialog(getWorkbenchPart().getSite()
				.getShell(),ModelUtil.getRules(rule.getModule()) );
	}
	
	private IndependentUnit findContainer(IndependentUnit ftFolder, Object obj) {
		for (Unit unit : ftFolder.getSubUnits()) {
			if (unit instanceof IndependentUnit) {
				IndependentUnit u = findContainer((IndependentUnit) unit, obj);
				if (u != null)
					return u;
			} else if (unit.equals(obj))
				return ftFolder;
		}

		return null;
		
		
	}

}
