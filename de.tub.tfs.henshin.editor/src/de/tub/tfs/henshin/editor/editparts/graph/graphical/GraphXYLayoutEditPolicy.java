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
package de.tub.tfs.henshin.editor.editparts.graph.graphical;

import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.AlignmentRequest;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import de.tub.tfs.henshin.editor.commands.graph.CreateNodeCommand;
import de.tub.tfs.henshin.editor.commands.graph.MoveManyNodesCommand;

/**
 * The Class GraphXYLayoutEditPolicy.
 */
public class GraphXYLayoutEditPolicy extends XYLayoutEditPolicy implements
		EditPolicy {

	
	@Override
	protected Command createChangeConstraintCommand(
			ChangeBoundsRequest request, EditPart child, Object constraint) {
		// TODO Auto-generated method stub
		return super.createChangeConstraintCommand(request, child, constraint);
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse
	 * .gef.requests.CreateRequest)
	 */
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Command command = null;
		if (request.getNewObject() instanceof Node) {
			final Node node = (Node) request.getNewObject();
			final Graph graph = (Graph) getHost().getModel();
			final Rectangle constraint = (Rectangle) getConstraintFor(request);

			command = new CreateNodeCommand(graph, node, constraint.x,
					constraint.y);
		}

		return command;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#
	 * getMoveChildrenCommand(org.eclipse.gef.Request)
	 */
	@Override
	protected Command getMoveChildrenCommand(Request request) {
		ChangeBoundsRequest req = (ChangeBoundsRequest) request;
		List<?> editparts = req.getEditParts();
		Command move = new MoveManyNodesCommand(editparts, req);
		return move;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#
	 * getAlignChildrenCommand(org.eclipse.gef.requests.AlignmentRequest)
	 */
	@Override
	protected Command getAlignChildrenCommand(AlignmentRequest request) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#getAddCommand
	 * (org.eclipse.gef.Request)
	 */
	@Override
	protected Command getAddCommand(Request generic) {
		return null;
	}

}
