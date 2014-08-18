/*******************************************************************************
 * Copyright (c) 2012, 2013 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.tools;


import org.eclipse.emf.ecore.EClass;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.swt.widgets.Shell;

import de.tub.tfs.henshin.tggeditor.commands.create.CreateNodeCommand;
import de.tub.tfs.henshin.tggeditor.util.dialogs.DialogUtil;


/**
 * The NodeCreationTool creates nodes in a graph.
 * @see de.tub.tfs.henshin.tggeditor.commands.create.CreateNodeCommand
 */
public class NodeCreationTool extends CreationTool {
	public NodeCreationTool() {
		super();
	}

	/**
	 * the constructor
	 * @param factory
	 */
	public NodeCreationTool(CreationFactory factory) {
		super(factory);
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.tools.AbstractTool#executeCommand(org.eclipse.gef.commands.Command)
	 */
	@Override
	protected void executeCommand(final Command command) {
		
		if (command instanceof CreateNodeCommand) {
			final CreateNodeCommand c = (CreateNodeCommand) command;
			//Module trafoSys;
			if (c.getGraph().eContainer() == null){
				return;
			}
			//trafoSys = (Module) EcoreUtil.getRootContainer(c.getGraph());

			
			Shell shell = new Shell();
			
			String nodeName = "";
			EClass eClass = (DialogUtil.runNodeCreationDialog(shell, c));
			shell.dispose();

			if (eClass != null) {
				c.setName(nodeName);
				c.setNodeType(eClass);
				super.executeCommand(command);
			}
		}
		
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.tools.AbstractTool#handleFinished()
	 */
	@Override
	protected void handleFinished() {
		reactivate();
		// super.handleFinished();

	}
}
