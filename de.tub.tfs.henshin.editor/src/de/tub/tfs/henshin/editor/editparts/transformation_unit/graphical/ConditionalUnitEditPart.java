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

import java.util.List;
import java.util.Vector;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.figure.transformation_unit.TransformationUnitFigure;
import de.tub.tfs.henshin.editor.ui.transformation_unit.TransUnitPage;
import de.tub.tfs.henshin.editor.util.ColorUtil;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.editor.util.TransformationUnitUtil;

/**
 * The Class ConditionalUnitEditPart.
 */
public class ConditionalUnitEditPart extends
		TransformationUnitEditPart<ConditionalUnit> {

	/**
	 * Instantiates a new conditional unit edit part.
	 * 
	 * @param transUnitPage
	 *            the trans unit page
	 * @param model
	 *            the model
	 */
	public ConditionalUnitEditPart(TransUnitPage transUnitPage,
			ConditionalUnit model) {
		super(transUnitPage, model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		IFigure figure = super.createFigure();
		figure.setBackgroundColor(ColorUtil.getColor(3));
		return figure;
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
		super.notifyChanged(notification);
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId) {
		case HenshinPackage.CONDITIONAL_UNIT__NAME:
			((TransformationUnitFigure) getFigure()).setName(getCastedModel()
					.getName());
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<EObject> getModelChildren() {
		List<EObject> list = new Vector<EObject>();
		list.addAll(TransformationUnitUtil
				.createConditionalUnitParts(getCastedModel()));
		list.addAll(getCastedModel().getParameters());
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("conditionalUnit25.png");
		} catch (Exception e) {
			return null;
		}
	}
}
