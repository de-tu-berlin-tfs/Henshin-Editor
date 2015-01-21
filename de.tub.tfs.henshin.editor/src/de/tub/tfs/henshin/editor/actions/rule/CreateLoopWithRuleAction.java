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
package de.tub.tfs.henshin.editor.actions.rule;

import java.util.List;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.rule.CreateLoopWithRuleCommand;

/**
 * The Class ExecuteRuleAction.
 */
public class CreateLoopWithRuleAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.CreateLoopWithRuleAction"; //$NON-NLS-1$

	/** The Constant DESC. */
	static private final String DESC = "Create Loop with Rule";

	/** The Constant TOOLTIP. */
	static private final String TOOLTIP = "Create Loop with Rule";

	/** The rule. */
	protected Rule rule;



	/**
	 * Instantiates a new execute rule action.
	 * 
	 * @param part
	 *            the part
	 */
	public CreateLoopWithRuleAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText(DESC);
		setDescription(DESC);
		setToolTipText(TOOLTIP);

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

		if (!(model instanceof Rule))
			return false;

		rule = (Rule) model;
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
		assert(rule == null): "ERROR: Creation of Loop unit. Rule must not be null.";
		execute(new CreateLoopWithRuleCommand(rule));
	}

//	@Override
//	public ImageDescriptor getImageDescriptor() {
//		return IconUtil.getDescriptor("play16.png");
//	}



	

	
}
