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

import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.condition.CreateConditionCommand;
import de.tub.tfs.henshin.editor.commands.condition.CreateFormulaCommand;
import de.tub.tfs.henshin.editor.util.IconUtil;

/**
 * The Class CreateAndAction.
 * 
 * @author angel
 */
public class CreateAndAction extends CreateFormulaAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.rule.condition.CreateAndAction";

	/**
	 * Instantiates a new creates the and action.
	 * 
	 * @param part
	 *            the part
	 */
	public CreateAndAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText("And-Condition");
		setDescription("Create and condition");
		setToolTipText("Create and condition for the selected graph.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.actions.rule.condition.CreateBinaryFormulaAction#run()
	 */
	@Override
	public void run() {
		final And and = HenshinFactory.eINSTANCE.createAnd();

		Command command = null;
		if (parentFormula == null) {
			command = new CreateConditionCommand(premise, and);
		} else {
			// Create a command with AND as first child of parent formula
			command = new CreateFormulaCommand(premise, parentFormula, and);
			if (parentFormula instanceof BinaryFormula) {
				final BinaryFormula binaryFormula = (BinaryFormula) parentFormula;
				if (binaryFormula.getLeft() != null
						&& binaryFormula.getRight() == null) {
					// Only if the first child is not null and the second child
					// is null,
					// then create a command with AND as second child of parent
					command = new CreateFormulaCommand(premise, parentFormula,
							and, false);
				}
			}
		}

		execute(command);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * henshineditor.actions.condition.CreateFormulaAction#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return IconUtil.getDescriptor("and16x16.png");
	}
}
