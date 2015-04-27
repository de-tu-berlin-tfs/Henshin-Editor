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

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.graph.CreateEdgeCommand;
import de.tub.tfs.henshin.editor.ui.dialog.CreateEdgeDialog;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.model.layout.EContainerDescriptor;

/**
 * The Class CreateEdgeAction.
 */
public class CreateEdgeAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.CreateEdgeAction";

	/** The graph. */
	private Graph graph;

	/**
	 * This constructor invokes the super constructor with the given
	 * {@code part}, sets the ID, action text, tool tip text, and initializes
	 * {@code targets} and {@code ereferences} to save the target nodes and the
	 * allow edges.
	 * 
	 * @param part
	 *            A workbench part.
	 */
	public CreateEdgeAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Edge");
		setToolTipText("Create Edge");
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
						&& editpart.getAdapter(Edge.class) != null) {
					graph = (Graph) ((EContainerDescriptor) selectedModel)
							.getContainer();
				}
			}
		}

		return graph != null;
	}

	/**
	 * �berpr�ft vor dem Erzeugen einer Kante, ob �berhaupt Knoten
	 * vorhanden sind. Sind keine Knoten vorhanden, wird dies dem User
	 * mitgeteilt. Ist mindestens ein Knoten vorhanden, so wird �ber ein
	 * Dialog sowohl Ziel- als auch Startknoten abgefragt. Existiert zwischen
	 * Ziel und Startknoten bereits eine Kante, wird dies dem User mitgeteilt
	 * und das Hinzuf�gen abgebrochen. Existiert dort noch keine Kante, so
	 * wird diese �ber den execute Befehl hinzugef�gt und dem User wird das
	 * erzeugen der Kante mitgeteilt.
	 */
	@Override
	public void run() {
		CreateEdgeDialog dialog = new CreateEdgeDialog(getWorkbenchPart()
				.getSite().getShell(), graph, "Create Edge");
		dialog.open();

		if (dialog.getReturnCode() == Window.OK) {
			Node source = dialog.getSource();
			Node target = dialog.getTarget();
			EReference type = dialog.getEdgeType();

			Command command = new CreateEdgeCommand(graph, source, target, type);

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
		return IconUtil.getDescriptor("edge18.png");
	}

}
