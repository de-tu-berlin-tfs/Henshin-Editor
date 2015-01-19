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
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.editor.editparts.HenshinEditPolicy;
import de.tub.tfs.henshin.editor.editparts.SimpleEndpointEditPartPolicy;
import de.tub.tfs.henshin.editor.editparts.flow_diagram.TransitionClipboardEditPolicy;
import de.tub.tfs.henshin.editor.editparts.flow_diagram.TransitionComponentEditPolicy;
import de.tub.tfs.henshin.model.flowcontrol.ConditionalElement;
import de.tub.tfs.henshin.model.flowcontrol.Transition;
import de.tub.tfs.muvitor.gef.editparts.AdapterConnectionEditPart;

/**
 * @author nam
 * 
 */
public class TransitionEditpart extends AdapterConnectionEditPart<Transition> {

	/**
	 * Constructs an {@link TransitionEditpart} for a given {@link Transition}.
	 */
	public TransitionEditpart(Transition model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new TransitionComponentEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
				new SimpleEndpointEditPartPolicy());

		installEditPolicy(HenshinEditPolicy.CLIPBOARD_ROLE,
				new TransitionClipboardEditPolicy());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		PolylineConnection connection = new PolylineConnection();
		PolygonDecoration decoration = new PolygonDecoration();

		decoration.setTemplate(PolygonDecoration.TRIANGLE_TIP);

		connection.setTargetDecoration(decoration);
		connection.setLineWidth(2);

		if (getCastedModel().isAlternate()) {
			ConditionalElement prevous = (ConditionalElement) getCastedModel()
					.getPrevous();
			Label conditionLbl = new Label("[" + prevous.getAlternativeLabel()
					+ "]");

			conditionLbl.setOpaque(true);
			connection.add(conditionLbl, new MidpointLocator(connection, 0));
		}

		return connection;
	}

}
