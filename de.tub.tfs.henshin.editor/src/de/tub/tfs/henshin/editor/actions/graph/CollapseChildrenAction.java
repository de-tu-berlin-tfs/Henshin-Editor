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
 * CollapseNodeAction.java
 * created on 14.07.2012 12:30:04
 */
package de.tub.tfs.henshin.editor.actions.graph;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.graph.CollapseChildrenCommand;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.NodeEditPart;
import de.tub.tfs.henshin.editor.interfaces.Messages;
import de.tub.tfs.henshin.editor.util.HenshinCache;

/**
 * @author huuloi
 *
 */
public class CollapseChildrenAction extends SelectionAction {
	
	public static final String ID = "de.tub.tfs.henshin.editor.actions.graph.CollapseChildrenAction";
	
	private Node node;

	public CollapseChildrenAction(IWorkbenchPart part) {
		super(part);
		
		setId(ID);
		setText(Messages.COLLAPSE_NODE);
		setDescription(Messages.COLLAPSE_NODE_DESC);
		setToolTipText(Messages.COLLAPSE_NODE_DESC);
	}

	@Override
	protected boolean calculateEnabled() {

		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		
		Object selectedObject = selectedObjects.get(0);
		if (selectedObject instanceof NodeEditPart) {
			NodeEditPart nodeEditPart = (NodeEditPart) selectedObject;
			node = nodeEditPart.getCastedModel();
			EList<Edge> outgoing = node.getOutgoing();
			for (Edge edge : outgoing) {
				if (edge.getType().isContainment() && 
					!HenshinCache.getInstance().getRemovedEditParts().contains(edge)
				) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void run() {
		CollapseChildrenCommand command = new CollapseChildrenCommand(node);
		execute(command);
	}
}
