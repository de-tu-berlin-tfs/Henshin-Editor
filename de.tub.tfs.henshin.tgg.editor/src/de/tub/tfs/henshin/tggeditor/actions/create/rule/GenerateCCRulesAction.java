/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.actions.create.rule;

import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateCCRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.ProcessRuleCommand;


/**
 * The class GenerateCCRulesAction generates Consistency-Creating-Rule from a TGG Rule. The Action
 * is registered in the Contextmenu of the Tree Editor.
 * @see GenerateCCRuleToolBarAction
 * @see ProcessRuleCommand
 */
public class GenerateCCRulesAction extends GenerateOpRulesAction {

	/**
	 * The fully qualified ID.
	 */
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateCCRulesAction";
	
	/**
	 * the constructor
	 * @param part
	 */
	public GenerateCCRulesAction(IWorkbenchPart part) {
		super(part);
		DESC = "Generate All CC_Rules";
		TOOLTIP = "Generates Consistency Creating Rules for all TGG Rules";
		setId(ID);
		setText(DESC);
		setToolTipText(TOOLTIP);
		opRuleTypeUpperCase = "CC";
		opRuleType=RuleUtil.TGG_CC_RULE;
	}
	@Override
	protected void setCommand(Rule rule, IndependentUnit container) {
		command = new GenerateCCRuleCommand((Rule)rule,container);		
	}
}
