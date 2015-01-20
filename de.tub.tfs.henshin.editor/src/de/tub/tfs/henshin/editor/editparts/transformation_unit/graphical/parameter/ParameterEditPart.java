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

import java.util.List;
import java.util.Vector;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.editparts.transformation_unit.tree.parameter.ParameterComponentEditPolicy;
import de.tub.tfs.henshin.editor.figure.transformation_unit.ParameterFigure;
import de.tub.tfs.henshin.editor.model.properties.transformation_unit.ParameterPropertySource;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;

/**
 * The Class ParameterEditPart.
 */
public class ParameterEditPart extends AdapterGraphicalEditPart<Parameter>
		implements IGraphicalDirectEditPart {

	/**
	 * Instantiates a new parameter edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public ParameterEditPart(Parameter model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * henshineditor.editparts.tree.rule.RuleTreeEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.NODE_ROLE,
				new ParameterGraphicalEditPartPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new ParameterComponentEditPolicy());
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
	 * @see
	 * muvitorkit.gef.editparts.AdapterGraphicalEditPart#notifyChanged(org.eclipse
	 * .emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId) {
		case HenshinPackage.PARAMETER__NAME:
			((ParameterFigure) getFigure()).setName(getCastedModel().getName());
			break;
		case HenshinPackage.PARAMETER_MAPPING:
			final int type = notification.getEventType();
			switch (type) {
			case Notification.ADD:
			case Notification.ADD_MANY:
			case Notification.REMOVE:
			case Notification.REMOVE_MANY:
				refreshSourceConnections();
				refreshTargetConnections();
			case Notification.SET:
				refreshVisuals();
				break;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelSourceConnections
	 * ()
	 */
	@Override
	protected List<ParameterMapping> getModelSourceConnections() {
		Vector<ParameterMapping> list = new Vector<ParameterMapping>();
		for (ParameterMapping parameterMapping : getCastedModel().getUnit()
				.getParameterMappings()) {
			if (parameterMapping.getSource() == getModel()) {
				list.add(parameterMapping);
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelTargetConnections
	 * ()
	 */
	@Override
	protected List<ParameterMapping> getModelTargetConnections() {
		Vector<ParameterMapping> list = new Vector<ParameterMapping>();
		for (ParameterMapping parameterMapping : getCastedModel().getUnit()
				.getParameterMappings()) {
			if (parameterMapping.getTarget() == getModel()) {
				list.add(parameterMapping);
			}
		}
		return list;
	}

	/**
	 * Sets the color.
	 * 
	 * @param color
	 *            the new color
	 */
	public void setColor(Color color) {
		if (color == null) {
			color = ColorConstants.gray;
		}
		getFigure().setBackgroundColor(color);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		ParameterFigure figure = new ParameterFigure(
				getCastedModel().getName(), getImage());
		return figure;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	/**
	 * Gets the image.
	 * 
	 * @return the image
	 */
	protected Image getImage() {
		try {
			return IconUtil.getIcon("parameter16.png");
		} catch (Exception e) {

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		return new ParameterPropertySource(getCastedModel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditFeatureID()
	 */
	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.PARAMETER__NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditValidator()
	 */
	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return new NameEditValidator(getCastedModel().getUnit(),
				HenshinPackage.UNIT__PARAMETERS,
				getCastedModel(), true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart#
	 * getValueLabelTextBounds()
	 */
	@Override
	public Rectangle getValueLabelTextBounds() {
		return ((ParameterFigure) getFigure()).getNameLabelTextBounds();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart#
	 * updateValueDisplay(java.lang.String)
	 */
	@Override
	public void updateValueDisplay(String value) {

	}

}
