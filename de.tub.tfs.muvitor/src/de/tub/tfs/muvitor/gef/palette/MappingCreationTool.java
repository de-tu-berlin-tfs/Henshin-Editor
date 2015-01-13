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
package de.tub.tfs.muvitor.gef.palette;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Tool;
import org.eclipse.gef.tools.ConnectionCreationTool;

/**
 * This {@link Tool} allows to create connections (e.g. node mappings) between
 * edit parts of two different {@link EditPartViewer}s. It will not get in the
 * "invalid" state if the mouse leaves the current viewer as does the normal
 * {@link ConnectionCreationTool}.
 */
public class MappingCreationTool extends ConnectionCreationTool {
	
	public MappingCreationTool() {
		super();
	}
	
	/**
	 * Overwritten to prevent the tool to get in invalid state if we focus on
	 * another viewer.
	 * 
	 * @see org.eclipse.gef.tools.ConnectionCreationTool#handleFocusLost()
	 */
	@Override
	protected boolean handleFocusLost() {
		return false;
	}
	
	/**
	 * Overwritten to allow moving in another viewer and nevertheless continue
	 * updating the tool.
	 * 
	 * @see org.eclipse.gef.tools.AbstractConnectionCreationTool#handleMove()
	 */
	@Override
	protected boolean handleMove() {
		if (isInState(STATE_CONNECTION_STARTED | STATE_INITIAL | STATE_ACCESSIBLE_DRAG_IN_PROGRESS)) {
			updateTargetRequest();
			updateTargetUnderMouse();
			showSourceFeedback();
			showTargetFeedback();
			setCurrentCommand(getCommand());
		}
		return true;
	}
}
