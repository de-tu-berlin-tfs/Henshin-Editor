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
package de.tub.tfs.henshin.tggeditor.tools;

import org.eclipse.gef.tools.CreationTool;

public class ParameterCreationTool extends CreationTool {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.tools.AbstractTool#executeCurrentCommand()
	 */
	@Override
	protected void executeCurrentCommand() {
		/*if (getCurrentCommand() instanceof CreateParameterCommand) {
			CreateParameterCommand command = (CreateParameterCommand) getCurrentCommand();
			if (command.getParameter().getName() == null
					|| command.getParameter().getName().isEmpty()) {
				Unit transUnit = command.getTransUnit();
				String defaultVarName = ModelUtil.getNewChildDistinctName(
						transUnit,
						HenshinPackage.TRANSFORMATION_UNIT__PARAMETERS,
						"parameter");

				// asks the user for the new parameter name, which has to be
				// unique
				// in current transformation unit
				InputDialog dialog = new InputDialog(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getActivePart().getSite().getShell(),
						"Parameter Name Input",
						"Enter a name for the new parameter:", defaultVarName,
						new NameEditorValidator(transUnit,
								HenshinPackage.TRANSFORMATION_UNIT__PARAMETERS,
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
					transUnit, HenshinPackage.TRANSFORMATION_UNIT__PARAMETERS,
					"parameter");

			// asks the user for the new parameter name, which has to be unique
			// in current transformation unit
			InputEditorValidators validators = new InputEditorValidators(
					new NameEditorValidator(transUnit,
							HenshinPackage.TRANSFORMATION_UNIT__PARAMETERS,
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

		}*/

	}
}
