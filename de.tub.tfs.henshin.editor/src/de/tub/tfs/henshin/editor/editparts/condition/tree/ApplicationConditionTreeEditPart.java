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
package de.tub.tfs.henshin.editor.editparts.condition.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.editparts.condition.ApplicationConditionComponentEditPolicy;
import de.tub.tfs.henshin.editor.editparts.graph.tree.GraphTreeEditPart;
import de.tub.tfs.henshin.editor.model.properties.condition.ApplicationConditionPropertySource;
import de.tub.tfs.henshin.editor.ui.condition.ConditionView;
import de.tub.tfs.henshin.editor.util.FormulaUtil;
import de.tub.tfs.henshin.editor.util.HenshinNotification;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.model.layout.EContainerDescriptor;
import de.tub.tfs.henshin.model.layout.HenshinLayoutFactory;

/**
 * The Class NestedConditionTreeEditPart.
 * 
 * @author Johann
 */
public class ApplicationConditionTreeEditPart extends GraphTreeEditPart {

	private final EContainerDescriptor graphElements;

	/**
	 * Constructs an {@link ApplicationConditionTreeEditPart} for a given
	 * {@link Graph} model object
	 * 
	 * @param model
	 *            an {@link Graph} model object.
	 */
	public ApplicationConditionTreeEditPart(Graph model) {
		super(model);

		Map<EClass, EStructuralFeature> elementsContainmentMap = new HashMap<EClass, EStructuralFeature>();

		elementsContainmentMap.put(HenshinPackage.Literals.NODE,
				HenshinPackage.Literals.GRAPH__NODES);
		elementsContainmentMap.put(HenshinPackage.Literals.EDGE,
				HenshinPackage.Literals.GRAPH__EDGES);

		graphElements = HenshinLayoutFactory.eINSTANCE
				.createEContainerDescriptor();
		graphElements.setContainer(model);
		graphElements.setContainmentMap(elementsContainmentMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart#getGraphicalViewModel
	 * ()
	 */
	@Override
	protected EObject getGraphicalViewModel() {
		EObject e = getCastedModel().eContainer().eContainer();
		
		if (e instanceof Graph) {
			if (((Graph) e).isLhs()) {
				return getCastedModel().getRule();
			}
		}

		return getCastedModel().eContainer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.graph.tree.GraphTreeEditPart#
	 * getModelChildren()
	 */
	@Override
	protected List<Object> getModelChildren() {
		List<Object> list = new ArrayList<Object>();

		if (getCastedModel().getFormula() != null) {
			list.add(getCastedModel().getFormula());
		}

		list.add(graphElements);

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		return ResourceUtil.ICONS.EXIST.img(16);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new ApplicationConditionComponentEditPolicy());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * henshineditor.editparts.tree.graph.GraphTreeEditPart#notifyChanged(org
	 * .eclipse.emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		final int eventType = notification.getEventType();
		final Object oldValue = notification.getOldValue();
		final Object newValue = notification.getNewValue();

		switch (eventType) {
		case HenshinNotification.BINARY_FORMULA_SWAP:
			final ConditionView openedView = FormulaUtil
					.getOpenedConditionView((Formula) oldValue);
			if (openedView != null) {
				FormulaUtil.closeView(openedView);

				refreshChildren();
				AbstractTreeEditPart childEditpart = findEditPartFor(newValue);
				if (childEditpart != null) {
					childEditpart.performRequest(new Request(REQ_OPEN));
				}
			}
			break;
		case Notification.REMOVE:
		case Notification.ADD:
		case Notification.SET:
			if (oldValue instanceof Formula
					&& !(oldValue instanceof BinaryFormula)
					&& !(newValue instanceof BinaryFormula)) {
				FormulaUtil.closeConditionViewsShowing((Formula) oldValue);
			}
			refreshChildren();
			refreshVisuals();
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * henshineditor.editparts.tree.graph.GraphTreeEditPart#createPropertySource
	 * ()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		return new ApplicationConditionPropertySource(getCastedModel());
	}
}
