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
package de.tub.tfs.henshin.editor.actions.rule;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.rule.CreateAttributeConditionCommand;
import de.tub.tfs.henshin.editor.ui.dialog.CreateAttributeConditionDialog;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;

/**
 * The Class CreateRuleAction.
 */
public class CreateAttributeConditionAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.CreateAttributeConditionAction"; //$NON-NLS-1$

	/** The transformation system. */
	private Rule rule;

	/**
	 * Instantiates a new creates the rule action.
	 * 
	 * @param part
	 *            the part
	 */
	public CreateAttributeConditionAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Attribute condition");
		setToolTipText("Create attribute condition");
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

		rule = null;

		Object selectedObject = selectedObjects.get(0);

		if (selectedObject instanceof IAdaptable) {
			rule = (Rule) ((IAdaptable) selectedObject)
					.getAdapter(AttributeCondition.class);

			if (rule == null) {
				if (selectedObject instanceof EditPart) {
					EditPart editpart = (EditPart) selectedObject;

					if ((editpart.getModel() instanceof Rule)) {
						rule = (Rule) editpart.getModel();
					}
				}
			}
		}

		return rule != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		String defaultRuleName = ModelUtil.getNewChildDistinctName(rule,
				HenshinPackage.RULE__ATTRIBUTE_CONDITIONS, "aCondition");

		CreateAttributeConditionDialog dialog = new CreateAttributeConditionDialog(
				getWorkbenchPart().getSite().getShell(), SWT.DEFAULT,
				defaultRuleName, new NameEditValidator(rule,
						HenshinPackage.RULE__ATTRIBUTE_CONDITIONS, true));
		dialog.open();

		if (!dialog.isCancel()) {
			Command command = new CreateAttributeConditionCommand(rule,
					dialog.getName(), dialog.getConditionText());
			execute(command);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return IconUtil.getDescriptor("aCondition22.png");
	}

}
