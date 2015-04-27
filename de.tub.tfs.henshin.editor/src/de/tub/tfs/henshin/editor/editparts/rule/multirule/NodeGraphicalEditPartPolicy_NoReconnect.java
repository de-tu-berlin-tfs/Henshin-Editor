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
 * 
 */
package de.tub.tfs.henshin.editor.editparts.rule.multirule;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ReconnectRequest;

import de.tub.tfs.henshin.editor.editparts.graph.graphical.NodeGraphicalEditPartPolicy;

/**
 * @author Johann
 * 
 */
public class NodeGraphicalEditPartPolicy_NoReconnect extends
		NodeGraphicalEditPartPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.editparts.graphs.NodeGraphicalEditPartPolicy#
	 * getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.editparts.graphs.NodeGraphicalEditPartPolicy#
	 * getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		return null;
	}

}
