package de.tub.tfs.henshin.tggeditor.actions.execution;

import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tggeditor.commands.ExecuteFTRulesCommand;
import de.tub.tfs.henshin.tggeditor.commands.ExecuteOpRulesCommand;


/**
 * The class ExecuteFTRuleAction executes a FT Rule. The class is shown in the context menu of the
 * Tree Editor and enabled when the FT rule folder is selected and FT Rules are available. The 
 * ExecuteFTRuleCommand is used.
 * @see ExecuteFTRuleCommand
 */
public class ExecuteFTRulesAction extends ExecuteOpRulesAction {
	
	/** The fully qualified class ID. */
	public static final String ID = "henshineditor.actions.ExecuteFTRulesAction";

	
	/**
	 * Instantiates a new execute rule action.
	 *
	 * @param part the part in which the action shall be registered
	 */
	public ExecuteFTRulesAction(IWorkbenchPart part) {
		super(part);
		name_OP_RULE_FOLDER = "FTRuleFolder";
		DESC = "[=FT=>]";
		TOOLTIP = "Execute all the FT Rules on the Graph";
		
		setId(ID);
		setText(DESC);
		setDescription(DESC);
		setToolTipText(TOOLTIP);
	}

	@Override
	protected ExecuteOpRulesCommand setCommand() {
		return new ExecuteFTRulesCommand(graph, tRules);
	}
	
}
