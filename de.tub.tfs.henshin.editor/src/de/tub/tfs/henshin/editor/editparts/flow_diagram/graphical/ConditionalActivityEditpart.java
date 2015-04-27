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
 * ConditionalActivityEditpart.java
 *
 * Created 18.12.2011 - 15:59:51
 */
package de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical;

import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.Request;

import de.tub.tfs.henshin.editor.figure.flow_diagram.ConditionalActivityFigure;
import de.tub.tfs.henshin.editor.figure.flow_diagram.ConditionalActivityFigureAnchor;
import de.tub.tfs.henshin.model.flowcontrol.ConditionalActivity;
import de.tub.tfs.henshin.model.flowcontrol.Transition;

/**
 * @author nam
 * 
 */
public class ConditionalActivityEditpart extends
		ActivityEditPart<ConditionalActivity> {

	/**
	 * @param model
	 */
	public ConditionalActivityEditpart(ConditionalActivity model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.ActivityEditpart
	 * #hookCreateFigure()
	 */
	@Override
	protected IFigure hookCreateFigure() {
		return new ConditionalActivityFigure();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.
	 * FlowElementEditpart
	 * #getTargetConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		return new ConditionalActivityFigureAnchor(getFigure());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.
	 * FlowElementEditpart#getTargetConnectionAnchor(org.eclipse.gef.Request)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return new ConditionalActivityFigureAnchor(getFigure());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.
	 * FlowElementEditpart#getModelSourceConnections()
	 */
	@Override
	protected List<Transition> getModelSourceConnections() {
		List<Transition> outGoing = super.getModelSourceConnections();

		Transition altOut = getCastedModel().getAltOut();

		if (altOut != null) {
			outGoing.add(altOut);
		}

		return outGoing;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.
	 * FlowElementEditpart
	 * #getSourceConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		return new ConditionalActivityFigureAnchor(getFigure(), true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.
	 * FlowElementEditpart#getSourceConnectionAnchor(org.eclipse.gef.Request)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return new ConditionalActivityFigureAnchor(getFigure(), true);
	}
}
