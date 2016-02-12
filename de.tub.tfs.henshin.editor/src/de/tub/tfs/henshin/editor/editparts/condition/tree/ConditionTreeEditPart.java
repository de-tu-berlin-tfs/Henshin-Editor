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
package de.tub.tfs.henshin.editor.editparts.condition.tree;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.emf.henshin.model.UnaryFormula;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

import de.tub.tfs.henshin.editor.editparts.condition.ConditionComponentEditPolicy;
import de.tub.tfs.henshin.editor.ui.condition.ConditionView;
import de.tub.tfs.henshin.editor.util.FormulaUtil;
import de.tub.tfs.henshin.editor.util.HenshinNotification;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;
import de.tub.tfs.muvitor.ui.utils.SWTResourceManager;

/**
 * The Class ConditionTreeEditPart.
 */
public class ConditionTreeEditPart extends AdapterTreeEditPart<Formula> {

	/**
	 * Instantiates a new condition tree edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public ConditionTreeEditPart(Formula model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#getText()
	 */
	@Override
	protected String getText() {
		Formula formula = getCastedModel();
		if (formula instanceof And) {
			return "AND";
		} else if (formula instanceof Or) {
			return "OR";
		} else if (formula instanceof Not) {
			return "NOT";
		}
		return "Undefined Formula";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<EObject> getModelChildren() {
		List<EObject> children = new ArrayList<EObject>();

		Formula leftChild = null;
		Formula rightChild = null;

		if (getCastedModel() instanceof UnaryFormula) {
			leftChild = ((UnaryFormula) getCastedModel()).getChild();
		} else if (getCastedModel() instanceof BinaryFormula) {
			leftChild = ((BinaryFormula) getCastedModel()).getLeft();
			rightChild = ((BinaryFormula) getCastedModel()).getRight();
		}

		if (leftChild != null) {
			children.add(leftChild);
		}

		if (rightChild != null) {
			children.add(rightChild);
		}

		return children;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
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
	 * de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart#getGraphicalViewModel
	 * ()
	 */
	@Override
	protected EObject getGraphicalViewModel() {
		EObject e = getCastedModel().eContainer();

		if (e instanceof Graph) {
			if (((Graph) e).isLhs()) {
				return e.eContainer();
			}
		}

		return super.getGraphicalViewModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		return ResourceUtil.ICONS.RULER.img(16);
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
			refreshChildren();
			refreshVisuals();
			break;

		case HenshinNotification.SELECTED:
			getViewer().select(this);
			break;
		default:
			break;
		}

		super.notifyChanged(notification);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#fireSelectionChanged()
	 */
	@Override
	protected void fireSelectionChanged() {
		if (getSelected() == SELECTED_PRIMARY) {
			getCastedModel().eNotify(
					new NotificationImpl(HenshinNotification.TREE_SELECTED,
							false, true));
		}

		super.fireSelectionChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();

		if (widget instanceof TreeItem) {
			TreeItem treeItem = (TreeItem) widget;
			Color black = SWTResourceManager.getColor(0, 0, 0);
			treeItem.setForeground(black);
			// treeItem.setExpanded(true);
			
			Color red = SWTResourceManager.getColor(255, 0, 0);
			Formula formula = getCastedModel();
			if (formula instanceof UnaryFormula) {
				UnaryFormula unaryFormula = (UnaryFormula) formula;
				if (unaryFormula.getChild() == null) {
					// Colors node text with red if no child exist.
					treeItem.setForeground(red);
				}
			} else if (formula instanceof BinaryFormula) {
				BinaryFormula binaryFormula = (BinaryFormula) formula;
				if (binaryFormula.getLeft() == null
						|| binaryFormula.getRight() == null) {
					// Colors node text with red if less than two children exist
					treeItem.setForeground(red);
				}
			}
		}
	}
}
