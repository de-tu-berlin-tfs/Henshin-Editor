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

import java.util.List;

import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.condition.SwapBinaryFormulaCommand;
import de.tub.tfs.henshin.editor.editparts.condition.tree.ConditionTreeEditPart;
import de.tub.tfs.henshin.editor.util.IconUtil;

/**
 * Swap selected OR with AND or selected AND with OR.
 * 
 * @author Angeline Warning
 */
public class SwapBinaryFormulaAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.rule.condition.SwapBinaryFormulaAction";

	private BinaryFormula binaryFormula;

	/**
	 * @param part
	 */
	public SwapBinaryFormulaAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setDescription("Swap binary formula");
		setToolTipText("Swap binary formula (OR -> AND or AND -> OR).");
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

		final Object selectedObject = selectedObjects.get(0);
		if ((selectedObject instanceof EditPart)) {
			final EditPart editPart = (EditPart) selectedObject;
			if (editPart instanceof ConditionTreeEditPart
					&& editPart.getModel() instanceof BinaryFormula) {
				if (editPart.getModel() instanceof And) {
					setText("Swap AND -> OR");
				} else if (editPart.getModel() instanceof Or) {
					setText("Swap OR -> AND");
				}

				binaryFormula = (BinaryFormula) editPart.getModel();
				return true;
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
		final SwapBinaryFormulaCommand command = new SwapBinaryFormulaCommand(
				binaryFormula);
		execute(command);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return IconUtil.getDescriptor("swap16x16.png");
	}
}
