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
/**
 * TransitionComponentEditPolicy.java
 *
 * Created 23.12.2011 - 14:53:46
 */
package de.tub.tfs.henshin.editor.editparts.flow_diagram;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.editor.commands.flow_diagram.DeleteTransitionCommand;
import de.tub.tfs.henshin.model.flowcontrol.Transition;

/**
 * @author nam
 * 
 */
public class TransitionComponentEditPolicy extends ComponentEditPolicy {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(
	 * org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		Object model = getHost().getModel();

		if (model instanceof Transition) {
			return new DeleteTransitionCommand((Transition) model);
		}

		return super.createDeleteCommand(deleteRequest);
	}
}
