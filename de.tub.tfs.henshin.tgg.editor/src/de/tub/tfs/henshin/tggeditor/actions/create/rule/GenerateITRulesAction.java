/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.actions.create.rule;

import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateITRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.ProcessRuleCommand;
import de.tub.tfs.henshin.tggeditor.util.GraphicalRuleUtil;

//NEW
//REF
/**
 * The class GenerateITRuleAction generates Integration-Translation-Rule from a simple Rule. The Action
 * is registered in the Contextmenu of the Tree Editor.
 * @see GenerateITRuleToolBarAction
 * @see ProcessRuleCommand
 */
public class GenerateITRulesAction extends GenerateOpRulesAction {

	/**
	 * The fully qualified ID.
	 */
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.GenerateITRulesAction";

	
	/**
	 * the constructor
	 * @param part
	 */
	public GenerateITRulesAction(IWorkbenchPart part) {
		super(part);
		DESC = "Generate All IT_Rules";
		TOOLTIP = "Generates Integration Translation Rules for all TGG Rules";
		setId(ID);
		setText(DESC);
		setToolTipText(TOOLTIP);
		opRuleTypeUpperCase = "IT";
		opRuleType=RuleUtil.TGG_IT_RULE;
	}
	
	@Override
	protected void setCommand(Rule rule, IndependentUnit container) {
		command = new GenerateITRuleCommand((Rule)rule, container);		
	}
	
	
}
