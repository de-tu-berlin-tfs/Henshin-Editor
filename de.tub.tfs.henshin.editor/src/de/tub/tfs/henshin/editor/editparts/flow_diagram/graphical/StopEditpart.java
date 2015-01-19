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
package de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.editor.editparts.HenshinEditPolicy;
import de.tub.tfs.henshin.editor.figure.flow_diagram.EndFigure;
import de.tub.tfs.henshin.model.flowcontrol.End;

/**
 * @author nam
 * 
 */
public class StopEditpart extends FlowElementEditPart<End> {

	public static final Point DEFAULT_FIG_LOC = new Point(100, 150);

	/**
	 * @param model
	 */
	public StopEditpart(End model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.
	 * FlowElementEditpart#hookCreateFigure()
	 */
	@Override
	protected IFigure hookCreateFigure() {
		return new EndFigure();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.
	 * FlowElementEditpart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();

		removeEditPolicy(EditPolicy.COMPONENT_ROLE);
		removeEditPolicy(HenshinEditPolicy.CLIPBOARD_ROLE);
	}
}
