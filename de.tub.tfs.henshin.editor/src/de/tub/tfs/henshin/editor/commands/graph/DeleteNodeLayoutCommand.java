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
 * 
 */
package de.tub.tfs.henshin.editor.commands.graph;

import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.model.layout.LayoutSystem;
import de.tub.tfs.henshin.model.layout.NodeLayout;

/**
 * The Class DeleteNodeLayoutCommand.
 */
public class DeleteNodeLayoutCommand extends Command {

	/** The node layout. */
	private NodeLayout nodeLayout;

	/** The layout system. */
	private LayoutSystem layoutSystem;

	/**
	 * @param layoutSystem2
	 * @param node
	 */
	public DeleteNodeLayoutCommand(LayoutSystem layoutSystem2,
			NodeLayout nodeLayout) {
		this.layoutSystem = layoutSystem2;
		this.nodeLayout = nodeLayout;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return nodeLayout != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		layoutSystem.getLayouts().remove(nodeLayout);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		layoutSystem.getLayouts().add(nodeLayout);
	}

}
