package tggeditor.actions.create.rule;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import tgg.TGG;
import tgg.TRule;
import tggeditor.commands.create.rule.GenerateFTRuleCommand;
import tggeditor.editparts.tree.rule.RuleTreeEditPart;
import tggeditor.util.ModelUtil;
import tggeditor.util.NodeUtil;
import tggeditor.util.dialogs.DialogUtil;

/**
 * The class GenerateFTRuleAction generates Forward-Translation-Rule from a simple Rule. The Action
 * is registered in the Contextmenu of the Tree Editor.
 * @see GenerateFTRuleToolBarAction
 * @see GenerateFTRuleCommand
 */
public class GenerateFTRuleAction extends SelectionAction {

	/**
	 * The fully qualified ID.
	 */
	public static final String ID = "tggeditor.actions.GenerateFTRuleAction";
	
	/** The Constant DESC for the description. */
	static private final String DESC = "Generate FT_Rule";

	/** The Constant TOOLTIP for the tooltip. */
	static private final String TOOLTIP = "Generates Forward for this TGG Rule";
	
	/**
	 * The rule which is used as base for the FT-Rule.
	 */
	protected Rule rule;
	
	/**
	 * the constructor
	 * @param part
	 */
	public GenerateFTRuleAction(IWorkbenchPart part) {
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
	 * @see GenerateFTRuleCommand
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		if (rule == null) {
			rule = getRule();
		}
		GenerateFTRuleCommand command = new GenerateFTRuleCommand(rule);		
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

}
