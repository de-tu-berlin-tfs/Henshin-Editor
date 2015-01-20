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
package de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPolicy;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.figure.transformation_unit.TransformationUnitPartFigure;
import de.tub.tfs.henshin.editor.internal.TransformationUnitPart;
import de.tub.tfs.henshin.editor.model.properties.transformation_unit.TransformationUnitPartPropertySource;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;

/**
 * @author Johann
 * 
 */
public class TransformationUnitPartAsSubUnitEditPart<T extends TransformationUnitPart<?>>
		extends AdapterGraphicalEditPart<T> {

	/**
	 * @param model
	 */
	public TransformationUnitPartAsSubUnitEditPart(T model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		IFigure figure = new TransformationUnitPartFigure(getCastedModel()
				.getName(), 200);
		return figure;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new TransformationUnitPartASSubUnitXYLayoutEditPolicy());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterGraphicalEditPart#performOpen()
	 */
	@Override
	protected void performOpen() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		return new TransformationUnitPartPropertySource(getCastedModel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected List getModelChildren() {
		final List<EObject> children = new ArrayList<EObject>();
		final Unit unit = getCastedModel().getModel();
		final EStructuralFeature feature = getCastedModel().getFeature();
		Object object = unit.eGet(feature);
		if (object != null) {
			if (feature.isMany()) {
				children.addAll((List<Unit>) object);
			} else {
				children.add((Unit) object);
			}
		}
		return children;
	}

}
