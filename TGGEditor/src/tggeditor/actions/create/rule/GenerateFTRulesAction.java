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
import tggeditor.editparts.tree.rule.RuleFolder;
import tggeditor.editparts.tree.rule.RuleFolderTreeEditPart;
import tggeditor.util.NodeUtil;

/**
 * The class GenerateFTRuleAction generates Forward-Translation-Rule from a simple Rule. The Action
 * is registered in the Contextmenu of the Tree Editor.
 * @see GenerateFTRuleToolBarAction
 * @see GenerateFTRuleCommand
 */
public class GenerateFTRulesAction extends SelectionAction {

	/**
	 * The fully qualified ID.
	 */
	public static final String ID = "tggeditor.actions.GenerateFTRulesAction";
	
	/** The Constant DESC for the description. */
	static private final String DESC = "Generate All FT_Rules";

	/** The Constant TOOLTIP for the tooltip. */
	static private final String TOOLTIP = "Generates Forward Translation Rules for all TGG Rules";
	
	/**
	 * The rule which is used as base for the FT-Rule.
	 */
	private List<Rule> rules;
	
	/**
	 * the constructor
	 * @param part
	 */
	public GenerateFTRulesAction(IWorkbenchPart part) {
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
			if (editpart instanceof RuleFolderTreeEditPart) {
				
				RuleFolder ruleFolder = (RuleFolder) editpart.getModel();
				rules = ruleFolder.getRules();
				if (!rules.isEmpty()) {
					TGG layoutSystem = NodeUtil.getLayoutSystem(rules.get(0));
	
					if(layoutSystem == null) return false;
					EList<TRule> tRules = layoutSystem.getTRules();
					for(TRule temp: tRules) {
						for (Rule rule : rules) {
							if(temp.getRule().equals(rule)) return false;
						}
					}
				} else { return false; }
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
		if (rules != null) {
			for (Rule rule : rules) {
				GenerateFTRuleCommand command = new GenerateFTRuleCommand(rule);		
				super.execute(command);
			}
		}
	}
}
