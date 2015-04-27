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
import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.transformation_unit.CreateTransformationUnitCommand;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;

/**
 * @author nam
 * 
 */
public class CreateLoopUnitAction extends CreateTransformationUnitAction {

	public static final String ID = "de.tub.tfs.henshin.editor.actions.transformation_unit.CreateLoopUnitAction"; //$NON-NLS-1$

	private static final String TEXT = "Loop Unit";

	private static final String DESC = "Create a new Loop Unit";

	private static final String TOOLTIP = "Create a new Loop Unit";

	private static final ImageDescriptor ICON = ResourceUtil.ICONS.LOOP
			.descr(16);

	/**
	 * @param part
	 */
	public CreateLoopUnitAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText(TEXT);
		setDescription(DESC);
		setToolTipText(TOOLTIP);
		setImageDescriptor(ICON);
	}

	@Override
	public void run() {
		String defaultTransformationName = ModelUtil.getNewChildDistinctName(
				transformationSystem,
				HenshinPackage.MODULE__UNITS,
				"loopUnit");

		InputDialog dialog = new InputDialog(
				getWorkbenchPart().getSite().getShell(),
				"Loop Unit Name Input",
				"Enter a name for the new Loop Unit:",
				defaultTransformationName,
				new NameEditValidator(
						transformationSystem,
						HenshinPackage.MODULE__UNITS,
						true));
		dialog.open();

		if (dialog.getReturnCode() == Window.OK) {
			execute(new CreateTransformationUnitCommand<LoopUnit>(
					transformationSystem, LoopUnit.class, dialog.getValue()));
		}
	}
}
