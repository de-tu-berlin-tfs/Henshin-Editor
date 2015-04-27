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
package de.tub.tfs.henshin.editor.actions.condition;

import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.condition.CreateConditionCommand;
import de.tub.tfs.henshin.editor.commands.condition.CreateFormulaCommand;
import de.tub.tfs.henshin.editor.editparts.condition.tree.ApplicationConditionTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.rule.tree.LhsRhsTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.rule.tree.RuleTreeEditPart;
import de.tub.tfs.henshin.editor.util.IconUtil;

/**
 * The Class CreateApplicationConditionAction.
 * 
 * @author Angeline Warning
 */
public class CreateApplicationConditionAction extends CreateFormulaAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.rule.condition.CreateApplicationConditionAction";

	/**
	 * Instantiates a new creates the application condition action.
	 * 
	 * @param part
	 *            the part
	 */
	public CreateApplicationConditionAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText("Application-Condition");
		setDescription("Create Application Condition");
		setToolTipText("Create a new application condition for the selected graph.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * henshineditor.actions.rule.condition.CreateFormulaAction#calculateEnabled
	 * ()
	 */
	@Override
	protected boolean calculateEnabled() {
		boolean enabled = super.calculateEnabled();
		if (!enabled && !getSelectedObjects().isEmpty()) {
			Object selectedObject = getSelectedObjects().get(0);
			if ((selectedObject instanceof EditPart)) {
				EditPart editPart = (EditPart) selectedObject;

				if (editPart instanceof ApplicationConditionTreeEditPart
						|| editPart instanceof RuleTreeEditPart) {
					return true;
				}

				if (editPart instanceof LhsRhsTreeEditPart) {
					return ((Graph) editPart.getModel()).isLhs();
				}
			}
		}

		return enabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.actions.condition.CreateConditionTreeAction#run()
	 */
	@Override
	public void run() {
		NestedCondition ac = HenshinFactory.eINSTANCE.createNestedCondition();

		Command command = null;
		if (parentFormula == null) {
			command = new CreateConditionCommand(premise, ac);
		} else {
			// Create a command with AC as first child of parent formula
			command = new CreateFormulaCommand(premise, parentFormula, ac);
			if (parentFormula instanceof BinaryFormula) {
				BinaryFormula binaryFormula = (BinaryFormula) parentFormula;
				if (binaryFormula.getLeft() != null
						&& binaryFormula.getRight() == null) {
					// Only if the first child is not null and the second child
					// is null,
					// then create a command with AND as second child of parent
					command = new CreateFormulaCommand(premise, parentFormula,
							ac, false);
				}
			}
		}

		execute(command);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return IconUtil.getDescriptor("appl-cond16x16.png");
	}
}
