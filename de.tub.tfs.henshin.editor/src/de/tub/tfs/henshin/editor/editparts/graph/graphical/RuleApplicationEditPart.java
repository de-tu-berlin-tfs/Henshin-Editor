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
package de.tub.tfs.henshin.editor.editparts.graph.graphical;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import de.tub.tfs.henshin.editor.internal.RuleApplicationEObject;
import de.tub.tfs.henshin.editor.internal.UnitApplicationEObject;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;
import de.tub.tfs.muvitor.ui.utils.SWTResourceManager;

/**
 * The Class RuleApplicationEditPart.
 */
public class RuleApplicationEditPart extends
		AdapterGraphicalEditPart<RuleApplicationEObject> {

	/**
	 * Instantiates a new rule application edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public RuleApplicationEditPart(RuleApplicationEObject model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		Label label = new Label(" - "
				+ getCastedModel().getRuleApplication().getRule().getName());
		// SUSANNs settings. WAAAAAAAHHHHHHH, don't use Comic sans!!!!
		//label.setFont(SWTResourceManager.getFont("Comic Sans MS", 11,
		//		SWT.NORMAL));
		label.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.NORMAL));
		label.setTextAlignment(PositionConstants.LEFT);
		return label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterGraphicalEditPart#performOpen()
	 */
	@Override
	protected void performOpen() {
		if(getParent().getModel() instanceof UnitApplicationEObject) {
			UnitApplicationImpl ua = (UnitApplicationImpl) ((UnitApplicationEObject) getParent().getModel()).getUnitApplication();
			int index = ua.getAppliedRules().indexOf(getCastedModel().getRuleApplication());
			((UnitApplicationEditPart) getParent()).setCurrentRuleApplication(index);
		}
		//UnitApplication unitApplication = ((UnitApplicationEObject) getParent().getModel()).getUnitApplication();
		//int index = unitApplication.getAppliedRules().indexOf(getCastedModel().getRuleApplication());
		//((UnitApplicationEditPart) getParent()).setCurrentRuleApplication(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#fireSelectionChanged
	 * ()
	 */
	@Override
	protected void fireSelectionChanged() {
		super.fireSelectionChanged();
		refreshVisuals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		updateFigure();
		getFigure().repaint();
		super.refreshVisuals();
	}

	/**
	 * Update figure.
	 */
	protected void updateFigure() {
		if (getSelected() == 0) { // not selected
			if (getCastedModel().isExecuted()) {
				getFigure().setForegroundColor(ColorConstants.black);
			} else {
				getFigure().setForegroundColor(ColorConstants.gray);
			}
		} else { // selected
			if (getCastedModel().isExecuted()) {
				// SUSANNs settings: not green, please
				getFigure().setForegroundColor(ColorConstants.darkGreen);
			} else {
				// SUSANNs settings: not lightGreen, please
				Color myGreen = new Color(null, 0, 220, 100);
				getFigure().setForegroundColor(myGreen);
			}
		}

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
		refreshVisuals();
		super.notifyChanged(notification);
	}

}
