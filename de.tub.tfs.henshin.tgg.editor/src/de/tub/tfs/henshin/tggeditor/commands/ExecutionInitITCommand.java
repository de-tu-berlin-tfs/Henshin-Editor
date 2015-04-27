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
package de.tub.tfs.henshin.tggeditor.commands;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.interpreter.util.NodeUtil;

//NEW GERARD
/**
 * The Class ExecutionInitFTCommand creates the initial marking for executing the operational FT rules on a given graph. 
 */
public class ExecutionInitITCommand extends ExecutionInitCommand {


	/**
	 * The graph on which all the rules will be applied.
	 */
	protected Graph graph;
	
	/**the constructor
	 * @param graph {@link ExecutionInitITCommand#graph}
	 * @param opRuleList {@link ExecutionInitITCommand#opRuleList}
	 */
	public ExecutionInitITCommand(Graph graph) {
		super(graph);
		this.graph = graph;
	}
	
	protected boolean isInMarkedComponent(TNode node){
		return NodeUtil.isSourceNode(node)|| NodeUtil.isTargetNode(node);
	};

}
