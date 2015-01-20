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

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import de.tub.tfs.henshin.editor.editparts.graph.graphical.NodeEditPart;
import de.tub.tfs.henshin.model.layout.NodeLayout;

/**
 * The Class MoveNodeCommand.
 */
public class MoveNodeCommand extends Command {

	/** The request. */
	ChangeBoundsRequest request;

	/** The n l. */
	NodeLayout nL;

	/** The old x. */
	private int oldX;

	/** The old y. */
	private int oldY;

	/** The x. */
	private int x;

	/** The y. */
	private int y;

	/**
	 * Instantiates a new move node command.
	 * 
	 * @param nodeEditPart
	 *            the node edit part
	 * @param request
	 *            the request
	 */
	public MoveNodeCommand(NodeEditPart nodeEditPart,
			ChangeBoundsRequest request) {
		this.request = request;
		nL = (NodeLayout) nodeEditPart.getLayoutModel();
		oldX = nL.getX();
		oldY = nL.getY();
		this.x = nL.getX() + request.getMoveDelta().x;
		this.y = nL.getY() + request.getMoveDelta().y;
	}

	/**
	 * Instantiates a new move node command.
	 * 
	 * @param nL
	 *            the n l
	 * @param x
	 *            the Coordinate x
	 * @param y
	 *            the Coordinate y
	 */
	public MoveNodeCommand(NodeLayout nL, int x, int y) {
		super();
		this.nL = nL;
		this.x = x;
		this.y = y;
		oldX = nL.getX();
		oldY = nL.getY();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		if (oldX != x) {
			nL.setX(x);
		}
		if (oldY != y) {
			nL.setY(y);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#undo()
	 */
	@Override
	public void undo() {
		if (oldX != x) {
			nL.setX(oldX);
		}
		if (oldY != y) {
			nL.setY(oldY);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return nL != null;
	}

}
