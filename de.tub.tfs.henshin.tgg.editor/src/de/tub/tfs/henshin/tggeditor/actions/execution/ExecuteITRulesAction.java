package de.tub.tfs.henshin.tggeditor.actions.execution;

import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tggeditor.commands.CheckOperationConsistencyCommand;
import de.tub.tfs.henshin.tggeditor.commands.ExecuteITRulesCommand;
import de.tub.tfs.henshin.tggeditor.commands.ExecutionInitFTCommand;
import de.tub.tfs.henshin.tggeditor.commands.ExecutionInitITCommand;

//NEW
/**
 * The class ExecuteITRuleAction executes a Integration Transformation Rule. The class is shown in the context menu of the
 * Tree Editor and enabled when the IT rule folder is selected and IT Rules are available. The 
 * ExecuteITRuleCommand is used.
 * @see ExecuteITRuleCommand
 */
public class ExecuteITRulesAction extends ExecuteOpRulesAction {
	
	/** The fully qualified class ID. */
	public static final String ID = "henshineditor.actions.ExecuteITRulesAction";

	
	/**
	 * Instantiates a new execute rule action.
	 *
	 * @param part the part in which the action shall be registered
	 */
	public ExecuteITRulesAction(IWorkbenchPart part) {
		super(part);
		name_OP_RULE_FOLDER = "ITRuleFolder";
		DESC = "[=IT=]";
		TOOLTIP = "Execute all the IT Rules on the Graph";
		
		setId(ID);
		setText(DESC);
		setDescription(DESC);
		setToolTipText(TOOLTIP);
	}
	
	//REF 7
	@Override
	protected CompoundCommand setCommand() {
		CompoundCommand cmd = new CompoundCommand();
		cmd.add(new ExecutionInitITCommand(graph));
		ExecuteITRulesCommand itCmd =new ExecuteITRulesCommand(graph, tRules); 
		cmd.add(itCmd);
		cmd.add(new CheckOperationConsistencyCommand(itCmd));
		return cmd;
	}
	
}
