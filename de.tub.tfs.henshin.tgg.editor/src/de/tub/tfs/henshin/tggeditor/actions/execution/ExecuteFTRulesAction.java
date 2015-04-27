/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.actions.execution;

import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tggeditor.commands.CheckOperationConsistencyCommand;
import de.tub.tfs.henshin.tggeditor.commands.ExecuteFTRulesCommand;
import de.tub.tfs.henshin.tggeditor.commands.ExecutionInitFTCommand;


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
	protected CompoundCommand setCommand() {
		CompoundCommand cmd = new CompoundCommand();
		cmd.add(new ExecutionInitFTCommand(graph));
		ExecuteFTRulesCommand ftCmd =new ExecuteFTRulesCommand(graph, tRules); 
		cmd.add(ftCmd);
		cmd.add(new CheckOperationConsistencyCommand(ftCmd));
		return cmd;
	}
	
}
