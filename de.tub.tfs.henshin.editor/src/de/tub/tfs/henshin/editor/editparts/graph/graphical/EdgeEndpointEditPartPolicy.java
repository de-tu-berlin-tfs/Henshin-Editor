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

import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;

/**
 * The Class EdgeEndpointEditPartPolicy.
 */
public class EdgeEndpointEditPartPolicy extends ConnectionEndpointEditPolicy {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.SelectionHandlesEditPolicy#addSelectionHandles
	 * ()
	 */
	@Override
	protected void addSelectionHandles() {
		super.addSelectionHandles();
		((PolylineConnection) getConnection()).setLineWidth(2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.SelectionHandlesEditPolicy#
	 * removeSelectionHandles()
	 */
	@Override
	protected void removeSelectionHandles() {
		super.removeSelectionHandles();
		((PolylineConnection) getConnection()).setLineWidth(1);
	}

}
