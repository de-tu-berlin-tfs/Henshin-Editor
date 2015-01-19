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
 * This class adds a Node type in CreateNodeCommand and executes the command.
 */
package de.tub.tfs.henshin.editor.actions.graph.tools;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.ui.PlatformUI;

import de.tub.tfs.henshin.editor.commands.graph.CreateNodeCommand;
import de.tub.tfs.henshin.editor.util.DialogUtil;

/**
 * The Class NodeCreationTool.
 * 
 * @author nam
 */
public class NodeCreationTool extends CreationTool {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.tools.AbstractTool#executeCurrentCommand()
	 */
	@Override
	protected void executeCurrentCommand() {
		final EClass type = DialogUtil.runNodeCreationDialog(PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActivePart().getSite().getShell(),
				(Graph) getTargetEditPart().getModel());
		final Command currentCmd = getCurrentCommand();

		if (type != null && currentCmd != null) {
			((CreateNodeCommand) currentCmd).setNodeType(type);

			super.executeCurrentCommand();
		}
	}
}
