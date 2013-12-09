package de.tub.tfs.henshin.tggeditor.actions.create.rule;

import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateFTRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.ProcessRuleCommand;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;


/**
 * The class GenerateFTRuleAction generates Forward-Translation-Rule from a simple Rule. The Action
 * is registered in the Contextmenu of the Tree Editor.
 * @see GenerateFTRuleToolBarAction
 * @see ProcessRuleCommand
 */
public class GenerateFTRulesAction extends GenerateOpRulesAction {

	/**
	 * The fully qualified ID.
	 */
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.GenerateFTRulesAction";

	
	/**
	 * the constructor
	 * @param part
	 */
	public GenerateFTRulesAction(IWorkbenchPart part) {
		super(part);
		DESC = "Generate All FT_Rules";
		TOOLTIP = "Generates Forward Translation Rules for all TGG Rules";
		setId(ID);
		setText(DESC);
		setToolTipText(TOOLTIP);
		opRuleTypeUpperCase = "FT";
		opRuleType=RuleUtil.TGG_FT_RULE;
	}
	@Override
	protected void setCommand(Rule rule, IndependentUnit container) {
		command = new GenerateFTRuleCommand((Rule)rule,container);		
	}
	
}
