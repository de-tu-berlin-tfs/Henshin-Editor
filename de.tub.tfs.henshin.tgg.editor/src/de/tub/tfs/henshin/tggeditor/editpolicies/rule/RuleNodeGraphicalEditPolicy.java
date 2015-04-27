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
package de.tub.tfs.henshin.tggeditor.editpolicies.rule;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;

import de.tub.tfs.henshin.tggeditor.commands.create.CreateEdgeCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateRuleEdgeCommand;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.NodeGraphicalEditPolicy;


public class RuleNodeGraphicalEditPolicy extends NodeGraphicalEditPolicy {
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
			final CreateRuleEdgeCommand createRuleEdgeCommand = (CreateRuleEdgeCommand) command;
			if (createRuleEdgeCommand.isSourcePlace()) {

				createRuleEdgeCommand.setTarget((Node) getHost().getModel());
				return createRuleEdgeCommand;
			}
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
//			final Graph graph = (Graph) node.eContainer();
//			if (ModelUtil.graphInKernelRule(graph)) {
//				command = new CreateKernelEdgeCommand(node, (Edge) requestingObject);
//			}
//			if {
				command = new CreateRuleEdgeCommand(node, (Edge) requestingObject);	
//			}
			
			request.setStartCommand(command);
			return command;
		}
		return null;
	}
}
