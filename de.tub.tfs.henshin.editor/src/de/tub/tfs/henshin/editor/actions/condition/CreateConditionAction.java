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

import java.util.List;

import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.condition.CreateConditionCommand;
import de.tub.tfs.henshin.editor.editparts.condition.tree.ApplicationConditionTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.rule.tree.LhsRhsTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.rule.tree.RuleTreeEditPart;
import de.tub.tfs.henshin.editor.ui.dialog.condition.CreateConditionDialog;
import de.tub.tfs.henshin.editor.util.IconUtil;

/**
 * Create complete condition tree to nest in selected graph.
 * 
 * @author Angeline Warning
 */
public class CreateConditionAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.rule.condition.CreateConditionAction";

	/** Selected graph the condition should be nested to. */
	protected Graph premise;

	/**
	 * Instantiates a new creates the condition action.
	 * 
	 * @param part
	 *            the part
	 */
	public CreateConditionAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText("Condition Tree...");
		setDescription("Creates new condition");
		setToolTipText("Creates a new condition to nest in selected graph.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		final List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}

		premise = null;
		final Object selectedObject = selectedObjects.get(0);
		if (selectedObject instanceof EditPart) {
			final EditPart editPart = (EditPart) selectedObject;

			// If a rule is selected, then set the LHS as a premise
			// of a formula to create
			if (editPart instanceof RuleTreeEditPart) {
				premise = ((Rule) editPart.getModel()).getLhs();
			}

			// Only if LHS is selected, then set the LHS as a premise
			// of a formula to create
			if (editPart instanceof LhsRhsTreeEditPart) {
				final Graph selectedModel = (Graph) editPart.getModel();
				if (selectedModel.isLhs()) {
					premise = selectedModel;
				}
			}

			// If an application condition is selected, then set the
			// conclusion as a premise of the formula to create.
			if (editPart instanceof ApplicationConditionTreeEditPart) {
				premise = (Graph) editPart.getModel();
			}

			if (premise != null) {
				return premise.getFormula() == null;
			}
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		final CreateConditionDialog dialog = new CreateConditionDialog(
				getWorkbenchPart().getSite().getShell(),
				"Create Condition Dialog");
		dialog.open();

		if (dialog.getReturnCode() == Window.OK) {
			final Formula formula = dialog.getFormula();
			if (formula != null) {
				execute(new CreateConditionCommand(premise, formula));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return IconUtil.getDescriptor("cond-tree16x16.png");
	}
}
