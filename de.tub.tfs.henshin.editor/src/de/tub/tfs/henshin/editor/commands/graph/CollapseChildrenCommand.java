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
 * CollapseChildrenCommand.java
 * created on 16.07.2012 00:38:58
 */
package de.tub.tfs.henshin.editor.commands.graph;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.util.HenshinCache;

/**
 * @author huuloi
 *
 */
public class CollapseChildrenCommand extends CompoundCommand {

	private Node node;

	public CollapseChildrenCommand(Node node) {
		this.node = node;
		
//		if (! new ValidateGraphAction(null).validate(node.getGraph())) {
//			MessageDialog.openInformation(null, Messages.INVALID, Messages.CAN_NOT_COLLAPSE_CHILDREN);
//		}
//		else {
			EList<Edge> outgoing = node.getOutgoing();
			for (Edge edge : outgoing) {
				if (edge.getType().isContainment() && 
					!HenshinCache.getInstance().getRemovedEditParts().contains(edge)
					) {
					add(new CollapseNodeCommand(edge.getTarget()));
				}
			}
//		}
	}
	
	@Override
	public boolean canExecute() {
		return node != null /*&& new ValidateGraphAction(null).validate(node.getGraph())*/;
	}
}
