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
 * This class adds a parameter name in CreateParameterCommand and executes the command.
 */
package de.tub.tfs.henshin.editor.actions.transformation_unit.tools;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;

import de.tub.tfs.henshin.editor.commands.transformation_unit.parameter.CreateParameterAndRenameNodeCommand;
import de.tub.tfs.henshin.editor.commands.transformation_unit.parameter.CreateParameterCommand;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.editor.util.validator.InputEditorValidators;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.henshin.editor.util.validator.RuleNodeNameEditorValidator;

/**
 * The Class ParameterCreationTool.
 * 
 * @author Johann
 */
public class ParameterCreationTool extends CreationTool {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.tools.AbstractTool#executeCurrentCommand()
	 */
	@Override
	protected void executeCurrentCommand() {
		if (getCurrentCommand() instanceof CreateParameterCommand) {
			CreateParameterCommand command = (CreateParameterCommand) getCurrentCommand();
			if (command.getParameter().getName() == null
					|| command.getParameter().getName().isEmpty()) {
				Unit transUnit = command.getTransformationUnit();
				String defaultVarName = ModelUtil.getNewChildDistinctName(
						transUnit,
						HenshinPackage.UNIT__PARAMETERS,
						"parameter");

				/*
				 * asks the user for the new parameter name, which has to be
				 * unique in current transformation unit
				 */
				InputDialog dialog = new InputDialog(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getActivePart().getSite().getShell(),
						"Parameter Name Input",
						"Enter a name for the new parameter:", defaultVarName,
						new NameEditValidator(transUnit,
								HenshinPackage.UNIT__PARAMETERS,
								true));
				dialog.open();

				if (dialog.getReturnCode() == Window.OK) {
					command.getParameter().setName(dialog.getValue());
					super.executeCurrentCommand();
				}
			} else {
				super.executeCurrentCommand();
			}
		}

		if (getCurrentCommand() instanceof CreateParameterAndRenameNodeCommand) {
			CreateParameterAndRenameNodeCommand command = (CreateParameterAndRenameNodeCommand) getCurrentCommand();
			Unit transUnit = command.getTransformationUnit();
			String defaultVarName = ModelUtil.getNewChildDistinctName(
					transUnit, HenshinPackage.UNIT__PARAMETERS,
					"parameter");

			// asks the user for the new parameter name, which has to be unique
			// in current transformation unit
			InputEditorValidators validators = new InputEditorValidators(
					new NameEditValidator(transUnit,
							HenshinPackage.UNIT__PARAMETERS,
							true));
			validators.addValidator(new RuleNodeNameEditorValidator(command
					.getNode()));
			InputDialog dialog = new InputDialog(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getActivePart()
					.getSite().getShell(), "Node and parameter name Input",
					"Enter a name for the node and new parameter:",
					defaultVarName, validators);
			dialog.open();
			if (dialog.getReturnCode() == Window.OK) {

				command = new CreateParameterAndRenameNodeCommand(transUnit,
						command.getNode(), dialog.getValue());
				setCurrentCommand(command);
				super.executeCurrentCommand();
			}

		}

	}
}
