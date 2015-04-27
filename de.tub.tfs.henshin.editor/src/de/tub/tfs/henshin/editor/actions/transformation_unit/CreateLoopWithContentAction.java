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

import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.rule.CreateLoopWithContentCommand;
import de.tub.tfs.henshin.editor.util.ResourceUtil;

/**
 * The Class ExecuteRuleAction.
 */
public class CreateLoopWithContentAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.CreateLoopWithContentAction"; //$NON-NLS-1$

	/** The Constant DESC. */
	static private final String DESC = "Create Loop with content";

	/** The Constant TOOLTIP. */
	static private final String TOOLTIP = "Create Loop with content";

	/** The rule. */
	protected Unit unit;
	
	private static final ImageDescriptor ICON = ResourceUtil.ICONS.LOOP
			.descr(16);



	/**
	 * Instantiates a new execute rule action.
	 * 
	 * @param part
	 *            the part
	 */
	public CreateLoopWithContentAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText(DESC);
		setDescription(DESC);
		setToolTipText(TOOLTIP);
		setToolTipText(TOOLTIP);
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

		if (!(selectedObject instanceof EditPart))
			return false;

		Object model = ((EditPart) selectedObject).getModel();

		if (!(model instanceof Unit))
			return false;

		unit = (Unit) model;
		setText(DESC);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		assert(unit == null): "ERROR: Creation of Loop unit. Rule must not be null.";
		execute(new CreateLoopWithContentCommand(unit));
	}

//	@Override
//	public ImageDescriptor getImageDescriptor() {
//		return IconUtil.getDescriptor("play16.png");
//	}



	

	
}
