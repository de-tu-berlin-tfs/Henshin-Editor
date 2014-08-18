/*******************************************************************************
 * Copyright (c) 2012, 2014 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.commands;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.interpreter.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;

/**
 * The Class ExecutionInitBTCommand creates the initial marking for executing the operational BT rules on a given graph. 
 */
public class ExecutionInitBTCommand extends ExecutionInitCommand {


	/**
	 * The graph on which all the rules will be applied.
	 */
	protected Graph graph;
	
	/**the constructor
	 * @param graph {@link ExecutionInitBTCommand#graph}
	 * @param opRuleList {@link ExecutionInitBTCommand#opRuleList}
	 */
	public ExecutionInitBTCommand(Graph graph) {
		super(graph);
		this.graph = graph;
	}

	protected boolean isInMarkedComponent(TNode node){
		return NodeUtil.isTargetNode(node);
	};

}
