package de.tub.tfs.henshin.tggeditor.actions.execution;

import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tggeditor.commands.ExecuteCCRulesCommand;
import de.tub.tfs.henshin.tggeditor.commands.ExecuteOpRulesCommand;


/**
 * The class ExecuteCCRuleAction executes all CC Rules. The class is shown in the context menu of the
 * Tree Editor and enabled when the CC rule folder is selected and CC Rules are available. The 
 * ExecuteCCRuleCommand is used.
 * @see ExecuteCCRuleCommand
 */
public class ExecuteCCRulesAction extends ExecuteOpRulesAction {
	
	/** The fully qualified class ID. */
	public static final String ID = "henshineditor.actions.ExecuteCCRulesAction";

	
	/**
	 * Instantiates a new execute rule action.
	 *
	 * @param part the part in which the action shall be registered
	 */
	public ExecuteCCRulesAction(IWorkbenchPart part) {
		super(part);
		name_OP_RULE_FOLDER = "CCRuleFolder";
		DESC = "[=CC=>]";
		TOOLTIP = "Execute all the CC Rules on the Graph";
		
		setId(ID);
		setText(DESC);
		setDescription(DESC);
		setToolTipText(TOOLTIP);
	}

	@Override
	protected ExecuteOpRulesCommand setCommand() {
		return new ExecuteCCRulesCommand(graph, tRules);
	}
	
}
