package de.tub.tfs.henshin.tggeditor.actions.create.rule;

import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateFTRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.ProcessRuleCommand;


/**
 * The class GenerateFTRuleAction generates the forward-translation-rule from a TGG rule. The action
 * is registered in the Contextmenu of the Tree Editor.
 * @see GenerateFTRuleToolBarAction
 * @see ProcessRuleCommand
 */
public class GenerateFTRuleAction extends GenerateOpRuleAction {

	/**
	 * The fully qualified ID.
	 */
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.GenerateFTRuleAction";
	
	/**
	 * the constructor
	 * @param part
	 */
	public GenerateFTRuleAction(IWorkbenchPart part) {
		super(part);
		DESC 	= "Generate FT_Rule";
		TOOLTIP = "Generates the forward translation rule for this TGG Rule";
		setId(ID);
		setText(DESC);
		setToolTipText(TOOLTIP);
	}


	@Override
	protected void setCommand(Rule rule, IndependentUnit container) {
		command = new GenerateFTRuleCommand(rule,container);		
	}

}
