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

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.tools.CreationTool;

import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkEdgeCommand;


/**
 * The MarkerCreationTool can mark or demark nodes in a rule with <++>.
 * @see MarkCommand
 */
public class MarkerCreationTool extends CreationTool {

	/**
	 * the constructor
	 */
	public MarkerCreationTool() {
		super();
	}

	/**
	 * the constructor
	 * @param aFactory
	 */
	public MarkerCreationTool(CreationFactory aFactory) {
		super(aFactory);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.tools.AbstractTool#handleFinished()
	 */
	@Override
	protected void handleFinished() {
		reactivate();
//		super.handleFinished();
	}

	@Override
	protected void executeCommand(Command command) {
//		if (command instanceof CreateMarkCommand ||
//				command instanceof DeleteMarkCommand) {
		if (command instanceof MarkCommand ||
				command instanceof MarkEdgeCommand) {
			super.executeCommand(command);
		}
	}
	
}
