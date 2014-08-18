/*******************************************************************************
 * Copyright (c) 2012, 2013 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.actions.create.rule;

import java.util.List;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tggeditor.commands.create.CreateAttributeCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateAttributeConditionCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateParameterCommand;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleGraphicalEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleNodeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleTreeEditPart;
import de.tub.tfs.henshin.tggeditor.util.ModelUtil;
import de.tub.tfs.henshin.tggeditor.util.ParameterUtil;
import de.tub.tfs.henshin.tggeditor.util.validator.InputEditorValidators;
import de.tub.tfs.henshin.tggeditor.util.validator.NameEditorValidator;
import de.tub.tfs.henshin.tggeditor.util.validator.RuleNodeNameEditorValidator;


public class CreateAttributeConditonAction extends SelectionAction {

	public static final String ID ="tggeditor.actions.create.CreateAttributeConditonAction";
	/** The trans unit. */
	protected Unit transUnit;
	
	/** The node. */
	protected TNode node;

	/**
	 * Instantiates a new creates the parameter action.
	 *
	 * @param part the part
	 */
	public CreateAttributeConditonAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Create Attribute Conditon");
		setToolTipText("Create Attribute Conditon");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		
		node = null;
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selectedObject = selectedObjects.get(0);

		if ((selectedObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selectedObject;

			if (editpart.getModel() instanceof TGGRule) {
				// TODO Franky: only LHS 
				
				transUnit = (Unit) editpart.getModel();
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



		Command command = new CreateAttributeConditionCommand(transUnit, "");

		execute(command);


	}
}
