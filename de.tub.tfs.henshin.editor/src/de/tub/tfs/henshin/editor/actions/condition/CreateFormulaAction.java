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
package de.tub.tfs.henshin.editor.actions.condition;

import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.UnaryFormula;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.editparts.condition.tree.ConditionTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.tree.GraphTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.rule.tree.LhsRhsTreeEditPart;

/**
 * The Class CreateFormulaAction. This option is enabled only if the selected
 * graph in tree doesn't have a formula or the selected formula is not complete.
 * 
 * @author angel
 */
public class CreateFormulaAction extends CreateConditionAction {

	/** The parent formula. */
	protected Formula parentFormula;

	/**
	 * Instantiates a new creates the not action.
	 * 
	 * @param part
	 *            the part
	 */
	public CreateFormulaAction(IWorkbenchPart part) {
		super(part);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * henshineditor.actions.condition.CreateConditionTreeAction#calculateEnabled
	 * ()
	 */
	@Override
	protected boolean calculateEnabled() {
		boolean enable = super.calculateEnabled();
		parentFormula = null;

		if (!getSelectedObjects().isEmpty()) {
			Object selectedObject = getSelectedObjects().get(0);
			if ((selectedObject instanceof EditPart)) {
				EditPart editPart = (EditPart) selectedObject;
				if (editPart instanceof ConditionTreeEditPart) {
					parentFormula = (Formula) editPart.getModel();

					do {
						editPart = editPart.getParent();
					} while (!isGraphOrLhsTreeEditPart(editPart));

					premise = (Graph) editPart.getModel();
					enable |= !isChildFormulaComplete(parentFormula);
				} else if (editPart instanceof GraphTreeEditPart) {

				}
			}
		}
		return enable;
	}

	/**
	 * Checks, if the given edit part is from type graph tree edit part or
	 * LHS/RHS tree edit part.
	 * 
	 * @param editPart
	 *            Edit part to check.
	 * @return {@code true} if the given edit part is from type graph tree edit
	 *         part or LHS/RHS tree edit part, {@code false} otherwise.
	 */
	private boolean isGraphOrLhsTreeEditPart(final EditPart editPart) {
		return editPart instanceof GraphTreeEditPart
				|| editPart instanceof LhsRhsTreeEditPart;
	}

	/**
	 * Checks if the given formula is an unary formula with child or if the
	 * given formula is a binary formula with left and right children. In this
	 * case, returns {@code true}.
	 * 
	 * @param parentFormula
	 *            A formula whose children have to be checked.
	 * @return {@code true} if the given formula is an unary formula with child
	 *         or the given formula is a binary formula with left and right
	 *         children, {@code false} if the given formula is an unary formula
	 *         without child or has only a left or right child.
	 */
	private boolean isChildFormulaComplete(final Formula parentFormula) {
		boolean isChildFormulaComplete = false;
		if (parentFormula instanceof UnaryFormula) {
			final UnaryFormula unaryFormula = (UnaryFormula) parentFormula;
			isChildFormulaComplete = unaryFormula.getChild() != null;
		} else if (parentFormula instanceof BinaryFormula) {
			final BinaryFormula binaryFormula = (BinaryFormula) parentFormula;
			isChildFormulaComplete = binaryFormula.getLeft() != null
					&& binaryFormula.getRight() != null;
		}

		return isChildFormulaComplete;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * henshineditor.actions.condition.CreateConditionAction#getImageDescriptor
	 * ()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}
}
