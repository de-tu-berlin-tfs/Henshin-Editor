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

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.transformation_unit.CreateTransformationUnitCommand;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;

/**
 * The Class CreateSequentialUnitAction.
 */
public class CreateSequentialUnitAction extends CreateTransformationUnitAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.CreateSequentialUnitAction";

	/**
	 * Instantiates a new creates the sequential unit action.
	 * 
	 * @param part
	 *            the part
	 */
	public CreateSequentialUnitAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Sequential Unit");
		setToolTipText("Creates a new sequential tranformation unit");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		String defaultTransformationName = ModelUtil.getNewChildDistinctName(
				transformationSystem,
				HenshinPackage.MODULE__UNITS,
				"sequentialUnit");

		// asks the user for the new graph name, which has to be unique in this
		// TransfomationSystem
		InputDialog dialog = new InputDialog(
				getWorkbenchPart().getSite().getShell(),
				"Sequential unit name input",
				"Enter a name for the new sequential unit:",
				defaultTransformationName,
				new NameEditValidator(
						transformationSystem,
						HenshinPackage.MODULE__UNITS,
						true));
		dialog.open();

		if (dialog.getReturnCode() == Window.OK) {
			Command command = new CreateTransformationUnitCommand<SequentialUnit>(
					transformationSystem, SequentialUnit.class,
					dialog.getValue());
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
		return IconUtil.getDescriptor("seqUnit18.png");
	}

}
