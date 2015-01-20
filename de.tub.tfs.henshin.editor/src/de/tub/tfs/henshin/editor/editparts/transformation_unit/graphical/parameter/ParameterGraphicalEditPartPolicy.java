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
package de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.parameter;

import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import de.tub.tfs.henshin.editor.commands.transformation_unit.parameter.CreateParameterMappingCommand;
import de.tub.tfs.henshin.editor.util.TransformationUnitUtil;

/**
 * The Class PortGraphicalEditPartPolicy.
 */
public class ParameterGraphicalEditPartPolicy extends GraphicalNodeEditPolicy
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
		if (command instanceof CreateParameterMappingCommand) {
			final CreateParameterMappingCommand parameterMappingCommand = (CreateParameterMappingCommand) command;
			Parameter source = parameterMappingCommand.getSource();
			Object targetObject = getHost().getModel();
			Parameter target = (Parameter) targetObject;
			parameterMappingCommand.setTarget(target);
			if (TransformationUnitUtil.getSubUnits(source.getUnit()).contains(
					target.getUnit())) {
				parameterMappingCommand.setTransformationUnit(source.getUnit());
				return parameterMappingCommand;
			}
			if (TransformationUnitUtil.getSubUnits(target.getUnit()).contains(
					source.getUnit())) {
				parameterMappingCommand.setTransformationUnit(target.getUnit());
				return parameterMappingCommand;
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
		final Object requestingObject = request.getNewObject();
		if (requestingObject instanceof ParameterMapping) {
			Parameter parameter = (Parameter) getHost().getModel();
			final Command command = new CreateParameterMappingCommand(parameter);
			request.setStartCommand(command);
			return command;
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		return null;
	}
}
