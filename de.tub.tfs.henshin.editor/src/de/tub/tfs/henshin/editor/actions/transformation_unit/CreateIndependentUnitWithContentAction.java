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

import java.util.Vector;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.transformation_unit.CreateTransformationUnitWithContentCommand;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;

/**
 * The Class CreateIndependentUnitWithContentAction.
 */
public class CreateIndependentUnitWithContentAction extends
		CreateTransformationUnitWithContentAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.CreateIndependentUnitWithContentAction";

	/**
	 * Instantiates a new creates the independent unit with content action.
	 * 
	 * @param part
	 *            the part
	 */
	public CreateIndependentUnitWithContentAction(IWorkbenchPart part) {
		super(part);
		maxContentCount = -1;
		setId(ID);
		setText("Create independent unit with content");
		setToolTipText("Create independent unit with content");
		selectedTransUnits = new Vector<Unit>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		String defaultTransformationName = ModelUtil.getNewChildDistinctName(
				transSys,
				HenshinPackage.MODULE__UNITS,
				"independentUnit");

		// asks the user for the new graph name, which has to be unique in this
		// TransfomationSystem
		InputDialog dialog = new InputDialog(
				getWorkbenchPart().getSite().getShell(),
				"Independent unit name input",
				"Enter a name for the new independent unit:",
				defaultTransformationName,
				new NameEditValidator(
						transSys,
						HenshinPackage.MODULE__UNITS,
						true));
		dialog.open();

		if (dialog.getReturnCode() == Window.OK) {
			Command command = new CreateTransformationUnitWithContentCommand<IndependentUnit>(
					transSys, parentObject, IndependentUnit.class,
					selectedTransUnits, dialog.getValue());
			if (command.canExecute()) {
				execute(command);
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
		return IconUtil.getDescriptor("independent16.png");
	}

}
