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
package de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.parameter;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.gef.EditPolicy;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.model.properties.transformation_unit.ParameterMappingPropertySource;
import de.tub.tfs.muvitor.gef.editparts.AdapterConnectionEditPart;

/**
 * The Class PortMappingEditPart.
 */
public class ParameterMappingEditPart extends
		AdapterConnectionEditPart<ParameterMapping> {

	/**
	 * Instantiates a new port mapping edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public ParameterMappingEditPart(ParameterMapping model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		PolylineConnection pLine = new PolylineConnection();
		pLine.setForegroundColor(ColorConstants.blue);
		PolygonDecoration decoration = new PolygonDecoration();
		decoration.setTemplate(PolygonDecoration.TRIANGLE_TIP);
		pLine.setTargetDecoration(decoration);
		return pLine;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractConnectionEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ROLE,
				new ParameterMappingComponentEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
				new ParameterMappingEndpointEditPolicy());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterConnectionEditPart#performOpen()
	 */
	@Override
	protected void performOpen() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#
	 * getSourceConnectionAnchor()
	 */
	@Override
	protected ConnectionAnchor getSourceConnectionAnchor() {
		return super.getSourceConnectionAnchor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractConnectionEditPart#
	 * getTargetConnectionAnchor()
	 */
	@Override
	protected ConnectionAnchor getTargetConnectionAnchor() {
		return super.getTargetConnectionAnchor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		return new ParameterMappingPropertySource(getCastedModel());
	}

}
