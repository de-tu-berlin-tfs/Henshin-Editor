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
package de.tub.tfs.henshin.editor.editparts.rule.tree;

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
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.model.properties.rule.LhsRhsPropertySource;
import de.tub.tfs.henshin.editor.ui.condition.ConditionView;
import de.tub.tfs.henshin.editor.ui.rule.RulePage;
import de.tub.tfs.henshin.editor.ui.rule.RuleView;
import de.tub.tfs.henshin.editor.util.FormulaUtil;
import de.tub.tfs.henshin.editor.util.HenshinNotification;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.model.layout.EContainerDescriptor;
import de.tub.tfs.henshin.model.layout.HenshinLayoutFactory;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;
import de.tub.tfs.muvitor.ui.MuvitorTreeEditor;

/**
 * The Class LhsRhsTreeEditPart.
 * 
 * @author Johann
 */
public class LhsRhsTreeEditPart extends AdapterTreeEditPart<Graph> {

	private final EContainerDescriptor graphElements;

	/**
	 * @param model
	 */
	public LhsRhsTreeEditPart(Graph model) {
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
	public EObject getGraphicalViewModel() {
		return (EObject) getParent().getModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#performOpen()
	 */
	@Override
	protected void performOpen() {
		MuvitorTreeEditor.showView((Rule) getParent().getModel());

		super.performOpen();
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
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#getText()
	 */
	@Override
	protected String getText() {
		return getCastedModel().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<Object> getModelChildren() {
		List<Object> list = new ArrayList<Object>();

		if (isLhs()) {
			if (getCastedModel().getFormula() != null) {
				list.add(getCastedModel().getFormula());
			}

			list.add(graphElements);

		} else {
			list.addAll(getCastedModel().getNodes());
			list.addAll(getCastedModel().getEdges());
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.gef.editparts.AdapterTreeEditPart#notifyChanged(org.eclipse
	 * .emf.common.notify.Notification)
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

			List<IViewPart> viewsShowing = FormulaUtil.getViewsShowing();
			for (IViewPart viewPart : viewsShowing) {
				if (viewPart instanceof RuleView) {
					final RulePage rulePage = ((RuleView) viewPart)
							.getCurrentRulePage();
					rulePage.refreshFormula();
				}
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
	 * org.eclipse.gef.editparts.AbstractEditPart#understandsRequest(org.eclipse
	 * .gef.Request)
	 */
	@Override
	public boolean understandsRequest(Request req) {
		return super.understandsRequest(req)
				&& !REQ_DIRECT_EDIT.equals(req.getType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		return ResourceUtil.ICONS.GRAPH.img(18);
	}

	/**
	 * Checks if is lhs.
	 * 
	 * @return true, if is lhs
	 */
	private boolean isLhs() {
		Rule parent = getCastedModel().getRule();
		return getCastedModel() == parent.getLhs();
	}
}
