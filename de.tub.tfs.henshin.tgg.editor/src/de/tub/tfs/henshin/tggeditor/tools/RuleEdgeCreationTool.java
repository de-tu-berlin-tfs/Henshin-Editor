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
package de.tub.tfs.henshin.tggeditor.tools;

import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.ConnectionCreationTool;
import org.eclipse.ui.PlatformUI;

import de.tub.tfs.henshin.tggeditor.commands.create.CreateEdgeCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateRuleEdgeCommand;
import de.tub.tfs.henshin.tggeditor.util.EdgeReferences;
import de.tub.tfs.henshin.tggeditor.util.dialogs.DialogUtil;


/**
 * The RuleEdgeCreationTool creates edges in a rule.
 * @see de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateRuleEdgeCommand
 */
public class RuleEdgeCreationTool extends ConnectionCreationTool {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.tools.AbstractTool#executeCurrentCommand()
	 */
	@Override
	protected void executeCurrentCommand() {
		final Command currentCmd = getCurrentCommand();
		if (currentCmd instanceof CreateRuleEdgeCommand ||
				currentCmd instanceof CreateEdgeCommand) {
			CreateEdgeCommand createRuleEdgeCommand = (CreateEdgeCommand) currentCmd;
			Node source = createRuleEdgeCommand.getSource();
			Node target = createRuleEdgeCommand.getTarget();
			
			List<EReference> eReferences = 
				EdgeReferences.getSourceToTargetReferences(source, target);
			EReference type = DialogUtil.runEdgeTypeSelectionDialog(PlatformUI
					.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.getActivePart().getSite().getShell(), eReferences);
	
			if (type != null) {
				createRuleEdgeCommand.setTypeReference(type);
				setCurrentCommand(createRuleEdgeCommand);
				super.executeCurrentCommand();
			}
		}
	}
	
	@Override
	protected boolean handleCreateConnection() {
		getCurrentViewer().deselectAll();
		
		return super.handleCreateConnection();
	}
}
