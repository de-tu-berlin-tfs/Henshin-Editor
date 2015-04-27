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
package de.tub.tfs.henshin.editor.editparts.condition.graphical;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Formula;

import de.tub.tfs.henshin.editor.figure.condition.BinaryFormulaFigure;
import de.tub.tfs.henshin.editor.util.HenshinNotification;
import de.tub.tfs.henshin.editor.util.SendNotify;

/**
 * @author angel
 * 
 */
public class BinaryFormulaEditPart extends FormulaEditPart<BinaryFormula> {

	/**
	 * @param model
	 */
	public BinaryFormulaEditPart(BinaryFormula model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		return new BinaryFormulaFigure(getCastedModel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<EObject> getModelChildren() {
		final List<EObject> children = new ArrayList<EObject>();

		final Formula left = getCastedModel().getLeft();
		if (left != null) {
			children.add(left);
		}

		final Formula right = getCastedModel().getRight();
		if (right != null) {
			children.add(right);
		}

		return children;
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
		int eventType = notification.getEventType();
		switch (eventType) {
		case HenshinNotification.BINARY_FORMULA_SWAP:
			refreshVisuals();
			SendNotify.sendAddFormulaNotify(getCastedModel().eContainer(),
					getCastedModel());

			break;
		case Notification.SET:
		case Notification.ADD:
		case Notification.REMOVE:
			refresh();
			SendNotify.sendAddFormulaNotify(getCastedModel().eContainer(),
					getCastedModel());
		default:
			break;
		}

		super.notifyChanged(notification);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		((BinaryFormulaFigure) getFigure()).refresh();
		super.refreshVisuals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#refresh()
	 */
	@Override
	public void refresh() {
		refreshChildren();
		refreshVisuals();
	}
}
