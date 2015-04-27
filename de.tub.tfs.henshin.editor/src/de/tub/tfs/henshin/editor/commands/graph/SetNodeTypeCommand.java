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
package de.tub.tfs.henshin.editor.commands.graph;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;

/**
 * The Class SetNodeTypeCommand.
 */
public class SetNodeTypeCommand extends Command {

	/** The type. */
	private EClass type;

	/** The node. */
	private Node node;

	/**
	 * Instantiates a new sets the node type command.
	 * 
	 * @param node
	 *            the node
	 * @param type
	 *            the type
	 */
	public SetNodeTypeCommand(Node node, EClass type) {
		this.type = type;
		this.node = node;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return type != null && node != null && !HenshinLayoutUtil.INSTANCE.hasOriginInKernelRule(node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return super.canUndo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		EClass temp = this.node.getType();
		this.node.setType(this.type);
		type = temp;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		execute();
	}

}
