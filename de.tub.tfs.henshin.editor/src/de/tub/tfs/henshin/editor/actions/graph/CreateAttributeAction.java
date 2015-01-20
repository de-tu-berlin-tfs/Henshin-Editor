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
package de.tub.tfs.henshin.editor.actions.graph;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import de.tub.tfs.henshin.editor.commands.graph.CreateAttributesCommand;
import de.tub.tfs.henshin.editor.util.DialogUtil;
import de.tub.tfs.henshin.editor.util.IconUtil;

/**
 * The Class CreateAttributeAction.
 * 
 * @author Johann, Angeline
 */
public class CreateAttributeAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.CreateAttributeAction";

	/** The node. */
	private Node node;

	/**
	 * Instantiates a new creates the attribute action.
	 * 
	 * @param part
	 *            the part
	 */
	public CreateAttributeAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Attribute");
		setImageDescriptor(IconUtil.getDescriptor("attr16.png"));
		setToolTipText("Create a new Attribute for the selected node");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		// Checks, if there is selected object
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}

		Object selectedObject = selectedObjects.get(0);
		// Checks, if the selected object is from edit part
		if ((selectedObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selectedObject;

			// Checks, if the edit part is a node (tree) edit part
			if (editpart.getModel() instanceof Node) {
				node = (Node) editpart.getModel();

				// Checks, if node type is not null
				if (node.getType() != null) {
					// An attribute can only be created if there is at least one
					// attribute of node
					// which is not be set
					int allAttributeCount = node.getType().getEAllAttributes()
							.size();
					int setAttributeCount = node.getAttributes().size();
					if ((allAttributeCount - setAttributeCount) > 0) {
						return true;
					}
				}
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
		// Open the dialog to create attribute(s)
		Map<EAttribute, String> attributes = DialogUtil
				.runAttributeCreationDialog(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getActivePart().getSite().getShell(), node);

		if (attributes.size() > 0) {
			CreateAttributesCommand command = new CreateAttributesCommand(node);
			for (EAttribute attr : attributes.keySet()) {
				command.addCreateAttributeCommand(attr, attributes.get(attr));
			}
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
		return IconUtil.getDescriptor("attr16.png");
	}

}
