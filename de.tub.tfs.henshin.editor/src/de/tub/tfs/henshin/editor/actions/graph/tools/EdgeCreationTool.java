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
/**
 * This class adds a Edge type in CreateEdgeCommand and executes the command.
 */
package de.tub.tfs.henshin.editor.actions.graph.tools;

import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.ConnectionCreationTool;
import org.eclipse.ui.PlatformUI;

import de.tub.tfs.henshin.editor.commands.graph.CreateEdgeCommand;
import de.tub.tfs.henshin.editor.util.DialogUtil;
import de.tub.tfs.henshin.editor.util.EdgeReferences;

/**
 * The Class EdgeCreationTool.
 */
public class EdgeCreationTool extends ConnectionCreationTool {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.tools.AbstractTool#executeCurrentCommand()
	 */
	@Override
	protected void executeCurrentCommand() {
		final Command currentCmd = getCurrentCommand();

		if (currentCmd instanceof CreateEdgeCommand) {
			CreateEdgeCommand createEdgeCommand = (CreateEdgeCommand) currentCmd;
			Node source = createEdgeCommand.getSource();
			Node target = createEdgeCommand.getTarget();

			List<EReference> eReferences = EdgeReferences
					.getSourceToTargetFreeReferences(source, target);
			EReference type = DialogUtil.runEdgeTypeSelectionDialog(PlatformUI
					.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.getActivePart().getSite().getShell(), eReferences);

			if (type != null) {
				createEdgeCommand.setTypeReference(type);
				executeCommand(createEdgeCommand);
			}
		}
	}

}
