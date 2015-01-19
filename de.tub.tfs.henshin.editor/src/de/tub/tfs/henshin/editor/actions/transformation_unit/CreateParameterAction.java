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

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.transformation_unit.parameter.CreateParameterAndRenameNodeCommand;
import de.tub.tfs.henshin.editor.commands.transformation_unit.parameter.CreateParameterCommand;
import de.tub.tfs.henshin.editor.editparts.rule.graphical.LhsRhsEditPart;
import de.tub.tfs.henshin.editor.editparts.rule.graphical.RuleNodeEditPart;
import de.tub.tfs.henshin.editor.editparts.rule.tree.LhsRhsTreeEditPart;
import de.tub.tfs.henshin.editor.internal.ConditionalUnitPart;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.editor.util.ParameterUtil;
import de.tub.tfs.henshin.editor.util.validator.InputEditorValidators;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.henshin.editor.util.validator.RuleNodeNameEditorValidator;
import de.tub.tfs.henshin.model.layout.EContainerDescriptor;

/**
 * The Class CreateParameterAction.
 */
public class CreateParameterAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.CreateParameterAction";

	/** The trans unit. */
	protected Unit transUnit;

	/** The node. */
	protected Node node;

	/**
	 * Instantiates a new creates the parameter action.
	 * 
	 * @param part
	 *            the part
	 */
	public CreateParameterAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Parameter");
		setToolTipText("Create a new Parameter");

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
			Object selectedModel = editpart.getModel();

			if ((selectedModel instanceof Unit && !(selectedModel instanceof ConditionalUnitPart))) {
				transUnit = (Unit) editpart.getModel();

				return true;
			}

			if (selectedModel instanceof EContainerDescriptor
					&& editpart.getAdapter(Parameter.class) != null) {
				transUnit = (Unit) ((EContainerDescriptor) selectedModel)
						.getContainer();

				return true;
			}

			if (editpart.getModel() instanceof Node
					&& ((editpart.getParent() instanceof LhsRhsTreeEditPart) || (editpart instanceof RuleNodeEditPart && (editpart
							.getParent() instanceof LhsRhsEditPart)))) {

				node = (Node) editpart.getModel();
				transUnit = (Unit) node.getGraph().eContainer();

				return ParameterUtil.getParameter(node) == null;
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
		String defaultVarName = ModelUtil.getNewChildDistinctName(transUnit,
				HenshinPackage.UNIT__PARAMETERS, "p");

		if (node != null) {
			String name = null;
			if (node.getName() != null && !node.getName().isEmpty()) {
				name = node.getName();
			} else {
				InputEditorValidators validators = new InputEditorValidators(
						new NameEditValidator(transUnit,
								HenshinPackage.UNIT__PARAMETERS,
								true));
				validators.addValidator(new RuleNodeNameEditorValidator(node));
				InputDialog dialog = new InputDialog(getWorkbenchPart()
						.getSite().getShell(), "Node and parameter name Input",
						"Enter a name for the node and new parameter:",
						defaultVarName, validators);
				dialog.open();
				if (dialog.getReturnCode() == Window.OK) {
					name = dialog.getValue();
				}
			}
			if (name != null) {
				Command command = new CreateParameterAndRenameNodeCommand(
						transUnit, node, name);
				execute(command);
			}

		} else {
			// asks the user for the new variable name, which has to be unique
			// in current rule
			InputDialog dialog = new InputDialog(getWorkbenchPart().getSite()
					.getShell(), "Parameter Name Input",
					"Enter a name for the new parameter:", defaultVarName,
					new NameEditValidator(transUnit,
							HenshinPackage.UNIT__PARAMETERS,
							true));
			dialog.open();

			if (dialog.getReturnCode() == Window.OK) {
				Command command = new CreateParameterCommand(transUnit,
						dialog.getValue());

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
		return IconUtil.getDescriptor("parameter16.png");
	}

}
