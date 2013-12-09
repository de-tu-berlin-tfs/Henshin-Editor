package de.tub.tfs.henshin.tggeditor.actions.execution;

import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tggeditor.commands.ExecuteBTRulesCommand;
import de.tub.tfs.henshin.tggeditor.commands.ExecuteOpRulesCommand;


/**
 * The class ExecuteBTRulesAction executes the BT Rules. The class is shown in the context menu of the
 * Tree Editor and enabled when the BT rule folder is selected and BT Rules are available. The 
 * ExecuteBTRuleCommand is used.
 * @see ExecuteBTRuleCommand
 */
public class ExecuteBTRulesAction extends ExecuteOpRulesAction {
	
	/** The fully qualified class ID. */
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.execution.ExecuteBTRulesAction";


	public ExecuteBTRulesAction(IWorkbenchPart part) {
		super(part);
		DESC = "[=BT=>]";
		TOOLTIP = "Execute all the BT Rules on the Graph";
		setId(ID);
		setText(DESC);
		setDescription(DESC);
		setToolTipText(TOOLTIP);

	}



	@Override
	protected ExecuteOpRulesCommand setCommand() {
		return new ExecuteBTRulesCommand(graph, tRules);
	}
	
}
