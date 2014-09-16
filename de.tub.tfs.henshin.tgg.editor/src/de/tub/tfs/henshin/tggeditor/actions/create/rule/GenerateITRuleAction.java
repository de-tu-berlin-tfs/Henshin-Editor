/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.actions.create.rule;

import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateITRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.ProcessRuleCommand;

//NEW GERARD
/**
 * The class GenerateFIRuleAction generates the integration-translation-rule from a TGG rule. The action
 * is registered in the Contextmenu of the Tree Editor.
 * @see GenerateITRuleToolBarAction
 * @see ProcessRuleCommand
 */
public class GenerateITRuleAction extends GenerateOpRuleAction {

	/**
	 * The fully qualified ID.
	 */
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.GenerateITRuleAction";
	
	/**
	 * the constructor
	 * @param part
	 */
	public GenerateITRuleAction(IWorkbenchPart part) {
		super(part);
		DESC 	= "Generate IT_Rule";
		TOOLTIP = "Generates the integration translation rule for this TGG Rule";
		setId(ID);
		setText(DESC);
		setToolTipText(TOOLTIP);
	}

	@Override
	protected void setCommand(Rule rule, IndependentUnit container) {
		command = new GenerateITRuleCommand(rule, container);		
	}

}
