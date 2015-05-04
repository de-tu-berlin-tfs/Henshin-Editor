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
package de.tub.tfs.henshin.tggeditor.editpolicies.graphical;

import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.AlignmentRequest;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.interpreter.impl.NodeTypes;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.create.CreateEdgeCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateNodeMappingCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateRuleEdgeCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkCommand;
import de.tub.tfs.henshin.tggeditor.commands.move.MoveNodeObjectCommand;
import de.tub.tfs.henshin.tggeditor.commands.move.ReconnectedEdgeCommand;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.TNodeObjectEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleNodeEditPart;
import de.tub.tfs.henshin.tggeditor.util.EdgeReferences;


/**
 * The Class NodeGraphicalEditPartPolicy.
 */
public class NodeGraphicalEditPolicy extends GraphicalNodeEditPolicy
		implements EditPolicy {

	PolylineConnection dummyConnect;
	protected Command command;
	
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
		command = request.getStartCommand();
		
		if (command instanceof CreateRuleEdgeCommand) {
			final CreateRuleEdgeCommand createEdgeCommand = (CreateRuleEdgeCommand) command;
			createEdgeCommand.setTarget((Node) getHost().getModel());
			return validatedEdgeCommand(createEdgeCommand);
		}
		if (command instanceof CreateEdgeCommand) {
			final CreateEdgeCommand createEdgeCommand = (CreateEdgeCommand) command;
			createEdgeCommand.setTarget((Node) getHost().getModel());
			return validatedEdgeCommand(createEdgeCommand);
		}
		
		if (command instanceof CreateNodeMappingCommand){
			final CreateNodeMappingCommand c = (CreateNodeMappingCommand ) command;
			if(c.getOrigial() != null){
				Node original = c.getOrigial();
				final Node image = (Node) getHost().getModel();
				if((original.getType() == image.getType() || 
						NodeTypes.isExtended(image.getType(), original.getType())) // SG: check inheritance relation
						&& !(image.eContainer().eContainer() instanceof Rule)) {
					c.setImage(image);
					c.setActualMappings(((NestedCondition)image.getGraph().eContainer()).getMappings());
					c.getStartMappingEditPart().setNumberForMapping(c.getMappingNumber());
					((TNodeObjectEditPart) getHost()).setNumberForMapping(c.getMappingNumber());
					c.setEndMappingEditPart((TNodeObjectEditPart)this.getHost());
					return c;
				}
				
			}
		}
		
		return null;
	}

	private Command validatedEdgeCommand(CreateEdgeCommand createEdgeCommand) {
		List<EReference> eReferences = 
				EdgeReferences.getSourceToTargetFreeReferences(createEdgeCommand.getSource(), createEdgeCommand.getTarget());
		if(eReferences.isEmpty())
			return null;
		return createEdgeCommand;		
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
		command = null;
		
		final Object requestingObject = request.getNewObject();
		if (requestingObject instanceof Edge) {
			if (getHost() instanceof RuleNodeEditPart) {
				CreateRuleEdgeCommand c = new CreateRuleEdgeCommand(
						(Node)getHost().getModel(),(Edge)requestingObject);
				request.setStartCommand(c);
				command = c;
				return c;
			}
			else {
				CreateEdgeCommand c = new CreateEdgeCommand(
						(Node)getHost().getModel(),(Edge)requestingObject);
				request.setStartCommand(c);
				command = c;
				return c;
			}
		}
		
		if(requestingObject instanceof Mapping && getHost() instanceof RuleNodeEditPart){
			final Mapping mapping = (Mapping)requestingObject;
//			NodeLayout nl = NodeUtil.getNodeLayout((Node) getHost().getModel());
			Node rhsNode = (Node) getHost().getModel();
			Node lhsNode = RuleUtil.getLHSNode(rhsNode);
			if(lhsNode!=null){
				CreateNodeMappingCommand c = new CreateNodeMappingCommand(lhsNode, mapping);
				int index = lhsNode.getGraph().getNodes().indexOf(lhsNode);
				c.setMappingNumber(index);
				c.setStartMappingEditPart((TNodeObjectEditPart)this.getHost());
				request.setStartCommand(c);
				command = c;
				return c;
			}
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
		//getHost().getParent().getViewer().deselectAll();
		getHost().getViewer().select(getHost());
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

	/**
	 * @param a_request
	 */
	protected Command getAligmentCommand(AlignmentRequest a_request) {
		TNode node = (TNode) getHost().getModel();
		//NodeLayout nL = NodeUtil.getNodeLayout(node);
		switch (a_request.getAlignment()) {
		case PositionConstants.LEFT:
			return new MoveNodeObjectCommand(node,(TNodeObjectEditPart) getHost(),
					a_request.getAlignmentRectangle().x, node.getY());
		case PositionConstants.RIGHT:
			return new MoveNodeObjectCommand(node,(TNodeObjectEditPart) getHost(),
					a_request.getAlignmentRectangle().x
							+ a_request.getAlignmentRectangle().width
							- getHostFigure().getBounds().width, node.getY());
		case PositionConstants.TOP:
			return new MoveNodeObjectCommand(node,(TNodeObjectEditPart) getHost(), node.getX(),
					a_request.getAlignmentRectangle().y);
		case PositionConstants.BOTTOM:
			return new MoveNodeObjectCommand(node,(TNodeObjectEditPart) getHost(), node.getX(),
					a_request.getAlignmentRectangle().y
							+ a_request.getAlignmentRectangle().height
							- getHostFigure().getBounds().height);
		case PositionConstants.CENTER:
			int newCenter = a_request.getAlignmentRectangle().x+a_request.getAlignmentRectangle().width/2;
			int x = newCenter - getHostFigure().getBounds().width/2;
			return new MoveNodeObjectCommand(
					node,(TNodeObjectEditPart) getHost(),x, node.getY());
		case PositionConstants.MIDDLE:
			int newMidle = a_request.getAlignmentRectangle().y+a_request.getAlignmentRectangle().height/2;
			int y = newMidle - getHostFigure().getBounds().height/2;

			return new MoveNodeObjectCommand(
					node,(TNodeObjectEditPart) getHost(),
					node.getX(),
					y);
		}
		return null;
	}

	/**
	 * Returns a connection to be used as feeback during creates.
	 * 
	 * @param req
	 *            the operation being performed
	 * @return a connection to use as feedback
	 */
	protected Connection createDummyConnection(Request req) {
		dummyConnect = new PolylineConnection();
		if (command != null) {
			if (command instanceof CreateNodeMappingCommand) {
				dummyConnect.setForegroundColor(ColorConstants.white);
//				dummyConnect.setBackgroundColor(ColorConstants.white);
			}
			else {
				dummyConnect.setForegroundColor(ColorConstants.black);
//				dummyConnect.setBackgroundColor(ColorConstants.black);
			}
		}
		return dummyConnect;
	}
}
