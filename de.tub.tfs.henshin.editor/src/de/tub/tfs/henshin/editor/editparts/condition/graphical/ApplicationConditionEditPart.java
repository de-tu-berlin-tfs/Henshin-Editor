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
package de.tub.tfs.henshin.editor.editparts.condition.graphical;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.editor.editparts.condition.ConditionComponentEditPolicy;
import de.tub.tfs.henshin.editor.figure.condition.ApplicationConditionFigure;

/**
 * The Class ApplicationConditionEditPart.
 * 
 * @author Angeline
 */
public class ApplicationConditionEditPart extends
		FormulaEditPart<NestedCondition> {

	/**
	 * Instantiates a new application condition edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public ApplicationConditionEditPart(NestedCondition model) {
		super(model);

		registerAdapter(model.getConclusion());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		return new ApplicationConditionFigure(getCastedModel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new ConditionComponentEditPolicy());
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
		if (featureId == HenshinPackage.GRAPH__NAME) {
			refreshVisuals();

			EditPart parentEditPart = getParent();
			while (!(parentEditPart instanceof ConditionEditPart)) {
				parentEditPart = parentEditPart.getParent();
			}
			if (parentEditPart instanceof ConditionEditPart) {
				((ConditionEditPart) parentEditPart)
						.notifyChanged(notification);
			}
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
		((ApplicationConditionFigure) getFigure()).refresh();
		super.refreshVisuals();
	}
}
