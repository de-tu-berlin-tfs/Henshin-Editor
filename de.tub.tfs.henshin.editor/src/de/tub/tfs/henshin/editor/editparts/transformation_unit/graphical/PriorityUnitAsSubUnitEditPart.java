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

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.figure.transformation_unit.SubUnitFigure;
import de.tub.tfs.henshin.editor.ui.transformation_unit.TransUnitPage;
import de.tub.tfs.henshin.editor.util.ColorUtil;
import de.tub.tfs.henshin.editor.util.IconUtil;

/**
 * The Class PriorityUnitAsSubUnitEditPart.
 */
public class PriorityUnitAsSubUnitEditPart extends
		SubUnitEditPart<PriorityUnit> {

	/**
	 * Instantiates a new priority unit as sub unit edit part.
	 * 
	 * @param transUnitPage
	 *            the trans unit page
	 * @param transformationUnit
	 *            the transformation unit
	 * @param model
	 *            the model
	 */
	public PriorityUnitAsSubUnitEditPart(TransUnitPage transUnitPage,
			Unit transformationUnit, PriorityUnit model) {
		super(transUnitPage, transformationUnit, model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("priority25.png");
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * henshineditor.editparts.transformation.SubUnitEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		IFigure figure = super.createFigure();
		figure.setBackgroundColor(ColorUtil.getColor(4));
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
		case HenshinPackage.PRIORITY_UNIT__NAME:
			((SubUnitFigure) getFigure()).setName(getCastedModel().getName());
			return;
		}
	}

}
