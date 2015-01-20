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
 * ParameterMappingEditpart.java
 *
 * Created 26.12.2011 - 17:04:43
 */
package de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.editor.editparts.SimpleComponentEditPolicy;
import de.tub.tfs.henshin.editor.editparts.SimpleEndpointEditPartPolicy;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.ParameterMapping;
import de.tub.tfs.muvitor.gef.editparts.AdapterConnectionEditPart;

/**
 * A {@link ConnectionEditPart connection edit part} for
 * {@link ParameterMapping parameter mappings} in flow control
 * {@link FlowDiagram diagrams}.
 * 
 * @author nam
 * 
 */
public class ParameterMappingEditpart extends
		AdapterConnectionEditPart<ParameterMapping> {

	/**
	 * Constructs an {@link ParameterMappingEditpart} with a given
	 * {@link ParameterMapping model}.
	 * 
	 * @param model
	 *            the model object, typed as {@link ParameterMapping}.
	 * 
	 * @see AdapterConnectionEditPart#AdapterConnectionEditPart(org.eclipse.emf.ecore.EObject)
	 */
	public ParameterMappingEditpart(ParameterMapping model) {
		super(model);
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

		return connection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new SimpleComponentEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
				new SimpleEndpointEditPartPolicy());
	}

}
