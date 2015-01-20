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
package de.tub.tfs.henshin.editor.editparts.rule.graphical;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPolicy;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.editparts.graph.graphical.GraphEditPart;
import de.tub.tfs.henshin.editor.model.properties.rule.LhsRhsPropertySource;
import de.tub.tfs.henshin.editor.util.SendNotify;

/**
 * A {@link GraphEditPart} for {@link Rule#getLhs() LHS} and
 * {@link Rule#getRhs() RHS} of a {@link Rule rule}.
 * 
 * @author Johann
 */
public class LhsRhsEditPart extends GraphEditPart {

	/**
	 * Constructs a new {@link LhsRhsEditPart} for a given {@link Graph graph}.
	 * 
	 * @param model
	 *            a {@link Graph graph}, which must be either a
	 *            {@link Rule#getLhs() LHS} or a {@link Rule#getRhs() RHS} of a
	 *            {@link Rule rule}.
	 * 
	 * @see GraphEditPart#GraphEditPart(Graph)
	 */
	public LhsRhsEditPart(Graph model) {
		super(model);

		Assert.isLegal(model.isRhs() || model.isLhs());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		return new LhsRhsPropertySource(getCastedModel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * henshineditor.editparts.graphs.GraphEditPart#notifyChanged(org.eclipse
	 * .emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);

		final int featureId = notification.getFeatureID(HenshinPackage.class);

		switch (featureId) {
		case HenshinPackage.GRAPH__NODES:
			final int type = notification.getEventType();
			getCastedModel().getRule().eNotify(notification);
			switch (type) {
			case Notification.REMOVE:
			case Notification.REMOVE_MANY:
				if (notification.getOldValue() instanceof Node) {
					if (((Rule) getCastedModel().eContainer()).getLhs() == getCastedModel()) {
						for (Node n : getCastedModel().getNodes()) {
							SendNotify.sendUpdateMappingNotify(n);
						}
					}
				}
			}
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();

		removeEditPolicy(EditPolicy.COMPONENT_ROLE); // no deletion of LHS or
		// RHS
	}

}
