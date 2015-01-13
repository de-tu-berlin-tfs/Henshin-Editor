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
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateParameterCommand;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleGraphicalEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleNodeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleTreeEditPart;
import de.tub.tfs.henshin.tggeditor.util.ModelUtil;
import de.tub.tfs.henshin.tggeditor.util.ParameterUtil;
import de.tub.tfs.henshin.tggeditor.util.validator.InputEditorValidators;
import de.tub.tfs.henshin.tggeditor.util.validator.NameEditorValidator;
import de.tub.tfs.henshin.tggeditor.util.validator.RuleNodeNameEditorValidator;


public class CreateParameterAction extends SelectionAction {

	public static final String ID ="tggeditor.actions.create.CreateParameterAction";
	/** The trans unit. */
	protected Unit transUnit;
	
	/** The node. */
	protected TNode node;

	/**
	 * Instantiates a new creates the parameter action.
	 *
	 * @param part the part
	 */
	public CreateParameterAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Create parameter");
		setToolTipText("Create parameter");
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
			
			if (editpart.getModel() instanceof TNode
					&& ((TNode)editpart.getModel()).getGraph().getRule() != null) {
				
				node = (TNode) editpart.getModel();
				transUnit = (Unit) node.getGraph().getRule();
				
				return true;
			}
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
		String defaultVarName = ModelUtil.getNewChildDistinctName(
				transUnit, HenshinPackage.UNIT__PARAMETERS,
				"parameter");

		if (node != null) {
			String name=null;
			if (node.getName()!=null && !node.getName().isEmpty()){
				name = node.getName();
			}
			else{
				InputEditorValidators validators=new InputEditorValidators(new NameEditorValidator(transUnit,
						HenshinPackage.UNIT__PARAMETERS, true));
				validators.addValidator(new RuleNodeNameEditorValidator(node));
				InputDialog dialog = new InputDialog(getWorkbenchPart().getSite()
						.getShell(), "Node and parameter name Input",
						"Enter a name for the node and new parameter:", defaultVarName,validators
						);
				dialog.open();
				if (dialog.getReturnCode() == Window.OK) {
					name = dialog.getValue();
				}
			}
			if (name!=null){
				Command command = new CreateParameterAndRenameNodeCommand(transUnit,node, name);
				execute(command);
			}
			
		} 
		else {
			// asks the user for the new variable name, which has to be unique
			// in
			// current rule
			InputDialog dialog = new InputDialog(getWorkbenchPart().getSite()
					.getShell(), "Parameter Name Input",
					"Enter a name for the new parameter:", defaultVarName,
					new NameEditorValidator(transUnit,
							HenshinPackage.UNIT__PARAMETERS, true));
			dialog.open();
			
			if (dialog.getReturnCode() == Window.OK) {
				Command command = new CreateParameterCommand(transUnit, dialog.getValue());

				execute(command);

			}
		}
	}
}
