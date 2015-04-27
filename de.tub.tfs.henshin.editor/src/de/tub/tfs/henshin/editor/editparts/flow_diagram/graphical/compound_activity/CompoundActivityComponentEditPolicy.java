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
 * CompoundActivityComponentEditPolicy.java
 *
 * Created 09.01.2012 - 14:53:51
 */
package de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.compound_activity;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.editor.commands.flow_diagram.DeleteCompoundActivityCommand;
import de.tub.tfs.henshin.model.flowcontrol.CompoundActivity;

/**
 * @author nam
 * 
 */
public class CompoundActivityComponentEditPolicy extends ComponentEditPolicy {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(
	 * org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new DeleteCompoundActivityCommand((CompoundActivity) getHost()
				.getModel());
	}
}
