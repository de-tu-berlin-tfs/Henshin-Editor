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

import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.editor.commands.transformation_unit.parameter.DeletePortMappingCommand;

/**
 * The Class PortMappingComponentEditPolicy.
 */
public class ParameterMappingComponentEditPolicy extends ConnectionEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ConnectionEditPolicy#getDeleteCommand(org
	 * .eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command getDeleteCommand(GroupRequest request) {
		return new DeletePortMappingCommand((ParameterMapping) getHost()
				.getModel());
	}

}
