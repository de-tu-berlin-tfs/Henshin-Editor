/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.actions.create.rule;

import java.util.ArrayList;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateBTRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateCCRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateFTRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateITRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateOpRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.ProcessRuleCommand;


/**
 * The class GenerateFTRuleAction generates the forward-translation-rule from a TGG rule. The action
 * is registered in the Contextmenu of the Tree Editor.
 * @see GenerateAllOpRuleToolBarAction
 * @see ProcessRuleCommand
 */
public class GenerateAllOpRuleAction extends GenerateOpRuleAction {

	/**
	 * The fully qualified ID.
	 */
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.GenerateAllOpRuleAction";
	
	protected ArrayList<GenerateOpRuleCommand> commandList;

	/**
	 * the constructor
	 * @param part
	 */
	public GenerateAllOpRuleAction(IWorkbenchPart part) {
		super(part);
		DESC 	= "Generate FT, BT, IT, CC Rule";
		TOOLTIP = "Generates all operational translation rules for this TGG rule";
		setId(ID);
		setText(DESC);
		setToolTipText(TOOLTIP);
		
		commandList = new ArrayList<GenerateOpRuleCommand>();
	}


	@Override
	protected void setCommand(Rule rule, IndependentUnit container) {
		commandList.add(new GenerateFTRuleCommand(rule,container));
		commandList.add(new GenerateBTRuleCommand(rule,container));
		commandList.add(new GenerateITRuleCommand(rule,container));
		commandList.add(new GenerateCCRuleCommand(rule,container));
	}

	/** 
	 * Executes all Generate..RuleCommands.
	 * @see ProcessRuleCommand
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		IndependentUnit container = findContainer((IndependentUnit) ((Module)EcoreUtil.getRootContainer(rule)).getUnit("RuleFolder")  ,rule);
		setCommand(rule, container);
		for (GenerateOpRuleCommand c : commandList) {
			super.execute(c);
		}
	}

}
