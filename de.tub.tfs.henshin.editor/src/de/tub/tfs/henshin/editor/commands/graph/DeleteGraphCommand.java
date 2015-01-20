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
package de.tub.tfs.henshin.editor.commands.graph;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.rule.DeleteRuleNodeCommand;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The Class DeleteGraphCommand.
 */
public class DeleteGraphCommand extends CompoundCommand {

	/**
	 * Instantiates a new delete graph command.
	 * 
	 * @param graph
	 *            the graph
	 */
	public DeleteGraphCommand(Graph graph) {
		add(new SimpleDeleteEObjectCommand(graph));
		for (Node node : graph.getNodes()) {
			if (graph.isNestedCondition()) {
				add(new DeleteRuleNodeCommand(node));
			} else {
				add(new DeleteNodeCommand(node));
			}
		}
		
	}

	
}
