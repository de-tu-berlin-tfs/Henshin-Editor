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
package de.tub.tfs.henshin.editor.editparts.rule.graphical;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;

import de.tub.tfs.henshin.editor.commands.rule.CreateNodeMappingCommand;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.NodeEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.NodeGraphicalEditPartPolicy;
import de.tub.tfs.henshin.editor.ui.rule.RuleView;

/**
 * A derived {@link NodeGraphicalEditPartPolicy} for {@link NodeEditPart}s
 * contained in {@link RuleView rule views} with handling of {@link Mapping node
 * mappings}.
 * 
 * @author Johann
 */
public class RuleNodeGraphicalEditPartPolicy extends
		NodeGraphicalEditPartPolicy {

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

		if (command instanceof CreateNodeMappingCommand) {
			final CreateNodeMappingCommand mapNodesCommand = (CreateNodeMappingCommand) command;
			final Node image = (Node) getHost().getModel();

			mapNodesCommand.setImage(image);

			return mapNodesCommand;
		}

		return super.getConnectionCompleteCommand(request);
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
		final Object newObject = request.getNewObject();

		if (newObject instanceof Mapping) {
			final Mapping newMapping = (Mapping) newObject;
			final Node origin = (Node) getHost().getModel();
			final Graph originGraph = origin.getGraph();

			CreateNodeMappingCommand command = new CreateNodeMappingCommand(
					newMapping, origin);

			if (originGraph.isNestedCondition()) {
				command.setContainer(originGraph.eContainer());
			} else if (originGraph.isLhs()) {
				command.setContainer(originGraph.getRule());
			}

			request.setStartCommand(command);

			return command;
		}

		return super.getConnectionCreateCommand(request);
	}
}
