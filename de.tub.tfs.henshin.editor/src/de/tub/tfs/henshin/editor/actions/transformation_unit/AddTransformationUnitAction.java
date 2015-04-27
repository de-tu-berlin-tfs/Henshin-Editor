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
package de.tub.tfs.henshin.editor.actions.transformation_unit;

import java.util.List;

import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.transformation_unit.AddTransformationUnitCommand;
import de.tub.tfs.henshin.editor.internal.ConditionalUnitPart;
import de.tub.tfs.henshin.editor.util.DialogUtil;
import de.tub.tfs.henshin.editor.util.ResourceUtil;

/**
 * The Class AddTransformationUnitAction.
 */
public class AddTransformationUnitAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.AddTransformationUnitAction"; //$NON-NLS-1$

	private static final ImageDescriptor ICON = ResourceUtil.ICONS.TRANS_UNIT
			.descr(18);

	/** The transformation unit. */
	private Unit transformationUnit;

	/**
	 * Instantiates a new adds the transformation unit action.
	 * 
	 * @param part
	 *            the part
	 */
	public AddTransformationUnitAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Add Subunit");
		setToolTipText("Add Subunit");
		setImageDescriptor(ICON);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selectedObject = selectedObjects.get(0);
		if ((selectedObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selectedObject;
			if (editpart.getModel() instanceof Unit
					&& !(editpart.getModel() instanceof ConditionalUnit)
					&& !(editpart.getModel() instanceof Rule)) {
				transformationUnit = (Unit) editpart.getModel();
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
		Unit subUnit = null;
		
		if (transformationUnit instanceof ConditionalUnitPart) {
			subUnit = DialogUtil.runTransformationUnitChoiceForAddUnitDialog(
					getWorkbenchPart().getSite().getShell(),
					((ConditionalUnitPart) transformationUnit).getModel());
		} else {
			subUnit = DialogUtil
					.runTransformationUnitChoiceForAddUnitDialog(
							getWorkbenchPart().getSite().getShell(),
							transformationUnit);
		}
		if (subUnit != null) {
			Command command = new AddTransformationUnitCommand(
					transformationUnit, subUnit);
			execute(command);
		}
	}
}
