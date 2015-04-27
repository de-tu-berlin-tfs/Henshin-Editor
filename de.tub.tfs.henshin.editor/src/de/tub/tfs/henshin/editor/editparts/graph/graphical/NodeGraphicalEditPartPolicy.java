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

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.AlignmentRequest;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import de.tub.tfs.henshin.editor.commands.graph.CreateEdgeCommand;
import de.tub.tfs.henshin.editor.commands.graph.MoveNodeCommand;
import de.tub.tfs.henshin.editor.commands.graph.ReconnectedEdgeCommand;
import de.tub.tfs.henshin.editor.util.EdgeReferences;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.model.layout.NodeLayout;

/**
 * The Class NodeGraphicalEditPartPolicy.
 */
public class NodeGraphicalEditPartPolicy extends GraphicalNodeEditPolicy
		implements EditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getConnectionCompleteCommand
	 * (org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected Command getConnectionCompleteCommand(
			CreateConnectionRequest request) {
		final Command command = request.getStartCommand();

		if (command instanceof CreateEdgeCommand) {
			final CreateEdgeCommand createEdgeCommand = (CreateEdgeCommand) command;
			List<EReference> eReferences = EdgeReferences
					.getSourceToTargetFreeReferences(createEdgeCommand
							.getSource(), ((Node) getHost().getModel()));
			if (eReferences.size() == 0) {
				return null;
			}
			createEdgeCommand.setTarget((Node) getHost().getModel());

			return createEdgeCommand;
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getConnectionCreateCommand
	 * (org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		Command command;

		final Object requestingObject = request.getNewObject();
		if (requestingObject instanceof Edge) {
			final Node node = (Node) getHost().getModel();

			command = new CreateEdgeCommand(node, (Edge) requestingObject);

			request.setStartCommand(command);

			return command;
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getSourceConnectionAnchor
	 * (org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected ConnectionAnchor getSourceConnectionAnchor(
			CreateConnectionRequest request) {
		return new ChopboxAnchor(getHostFigure());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		Edge edge = (Edge) request.getConnectionEditPart().getModel();
		Node target = edge.getTarget();
		Node source = (Node) getHost().getModel();
		if (edge.getSource() != source)
			return new ReconnectedEdgeCommand(edge, source, target);
		else
			return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		Edge edge = (Edge) request.getConnectionEditPart().getModel();
		Node target = (Node) getHost().getModel();
		Node source = edge.getSource();
		if (target != edge.getTarget())
			return new ReconnectedEdgeCommand(edge, source, target);
		else
			return null;
	}

	/**
	 * @param a_request
	 */
	protected Command getAligmentCommand(AlignmentRequest a_request) {
		NodeLayout nL = HenshinLayoutUtil.INSTANCE.getLayout((Node) getHost()
				.getModel());
		switch (a_request.getAlignment()) {
		case PositionConstants.LEFT:
			return new MoveNodeCommand(nL, a_request.getAlignmentRectangle().x,
					nL.getY());
		case PositionConstants.RIGHT:
			return new MoveNodeCommand(nL, a_request.getAlignmentRectangle().x
					+ a_request.getAlignmentRectangle().width
					- getHostFigure().getBounds().width, nL.getY());
		case PositionConstants.TOP:
			return new MoveNodeCommand(nL, nL.getX(),
					a_request.getAlignmentRectangle().y);
		case PositionConstants.BOTTOM:
			return new MoveNodeCommand(nL, nL.getX(),
					a_request.getAlignmentRectangle().y
							+ a_request.getAlignmentRectangle().height
							- getHostFigure().getBounds().height);
		case PositionConstants.CENTER:
			int newCenter = a_request.getAlignmentRectangle().x
					+ a_request.getAlignmentRectangle().width / 2;
			int x = newCenter - getHostFigure().getBounds().width / 2;
			return new MoveNodeCommand(nL, x, nL.getY());
		case PositionConstants.MIDDLE:
			int newMidle = a_request.getAlignmentRectangle().y
					+ a_request.getAlignmentRectangle().height / 2;
			int y = newMidle - getHostFigure().getBounds().height / 2;
			return new MoveNodeCommand(nL, nL.getX(), y);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getCommand(org.eclipse
	 * .gef.Request)
	 */
	@Override
	public Command getCommand(Request request) {
		if (REQ_ALIGN.equals(request.getType())) {
			AlignmentRequest a_request = (AlignmentRequest) request;
			return getAligmentCommand(a_request);
		}
		return super.getCommand(request);
	}

}
