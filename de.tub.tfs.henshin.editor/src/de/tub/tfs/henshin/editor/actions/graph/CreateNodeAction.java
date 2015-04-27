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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.graph.CreateNodeCommand;
import de.tub.tfs.henshin.editor.util.DialogUtil;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.model.layout.EContainerDescriptor;

/**
 * The Class CreateNodeAction.
 */
public class CreateNodeAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.CreateNodeAction";

	/**
	 * 
	 */
	private static final String ICON = "node18.png";

	/** The graph. */
	private Graph graph;

	/**
	 * Instantiates a new creates the node action.
	 * 
	 * @param part
	 *            the part
	 */
	public CreateNodeAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText("Node");
		setToolTipText("Create a new Node");
		setImageDescriptor(IconUtil.getDescriptor(ICON));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();

		graph = null;

		if (selectedObjects.size() == 1) {

			Object selectedObject = selectedObjects.get(0);

			if ((selectedObject instanceof EditPart)) {
				EditPart editpart = (EditPart) selectedObject;
				Object selectedModel = editpart.getModel();

				if (selectedModel instanceof Graph) {
					graph = (Graph) selectedModel;
				}

				if (selectedModel instanceof EContainerDescriptor
						&& editpart.getAdapter(Node.class) != null) {
					graph = (Graph) ((EContainerDescriptor) selectedModel)
							.getContainer();
				}
			}
		}

		return graph != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		EClass selectedType = DialogUtil.runNodeCreationDialog(
				getWorkbenchPart().getSite().getShell(), graph);
		if (selectedType != null) {
			Command command = new CreateNodeCommand(graph, selectedType);

			execute(command);
		}
	}
}
