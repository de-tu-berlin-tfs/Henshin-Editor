/*******************************************************************************
 * Copyright (c) 2012, 2013 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.editpolicies.rule;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkEdgeCommand;


public class RuleEdgeXYLayoutEditPolicy extends XYLayoutEditPolicy implements
		EditPolicy {

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Command command = null;
		if (request.getNewObject() instanceof Mapping) {
			Edge rhsEdge = (Edge) getTargetEditPart(request).getModel();
			command = new MarkEdgeCommand(rhsEdge);
		}
		return command;
	}

}
