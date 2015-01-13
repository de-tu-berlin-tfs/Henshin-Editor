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
package de.tub.tfs.henshin.tggeditor.commands.move;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.TNodeObjectEditPart;


public class MoveManyNodeObjectsCommand extends CompoundCommand {
	
	private List<?> ep;
	private ChangeBoundsRequest req;

	private int deltaX;
	private int deltaY;

	/**
	 * creates a move command for every node object
	 *
	 * @param editparts list of all elements to move
	 * @param request the change bounds request
	 */
	public MoveManyNodeObjectsCommand(List<?> editparts, ChangeBoundsRequest request) {
		super();
		this.ep = editparts;
		this.req = request;
		
		this.deltaX =  request.getMoveDelta().x;
		this.deltaY = request.getMoveDelta().y;
		
	}
	
	@Override
	public void execute() {
		for (Object obj : ep) {
			TNode node = (TNode) ((EditPart)obj).getModel();
			node.eSetDeliver(false);
			((TNode) node).setComponent(null);
			
		}
		for (Object obj : ep) {
			TNode node = (TNode) ((EditPart)obj).getModel();
			if (deltaX != 0)
				node.setX(node.getX()+deltaX);
			if (deltaY != 0)
				node.setY(node.getY()+deltaY);
			
		}
		for (Object obj : ep) {
			TNode node = (TNode) ((EditPart)obj).getModel();
			node.eSetDeliver(true);
			((TNode) node).setComponent(null);
			
		}
	}

	@Override
	public void undo() {
		for (Object obj : ep) {
			TNode node = (TNode) ((EditPart)obj).getModel();
			node.eSetDeliver(false);
			((TNode) node).setComponent(null);
			
		}
		for (Object obj : ep) {
			TNode node = (TNode) ((EditPart)obj).getModel();
			if (deltaX != 0)
				node.setX(node.getX()-deltaX);
			if (deltaY != 0)
				node.setY(node.getY()-deltaY);
			
		}
		for (Object obj : ep) {
			TNode node = (TNode) ((EditPart)obj).getModel();
			node.eSetDeliver(true);
			((TNode) node).setComponent(null);
			
		}
	}
}
