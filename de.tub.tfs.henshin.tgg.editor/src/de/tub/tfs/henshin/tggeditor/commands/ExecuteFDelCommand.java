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

import java.util.List;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.interpreter.util.NodeUtil;

/**
 * The Class ExecuteOpRulesCommand executes all the given Rules ({@link TRule}) on a given graph. For the
 * execution are mainly the classes from org.eclipse.emf.henshin.interpreter used. The mapping 
 * of the RuleApplication will be checked with the class {@link OpRuleConstraint}.
 * There will be also the layouts for nodes and edges created.
 */
public class ExecuteFDelCommand extends ExecuteDelCommand {

	protected Graph graph;
	
	/**the constructor
	 * @param graph {@link ExecuteFDelCommand#graph}
	 * @param opRuleList {@link ExecuteFDelCommand#opRuleList}
	 */
	public ExecuteFDelCommand(Graph graph, List<Rule> opRuleList) {
		super(graph,opRuleList);
	}

	
	protected boolean isInTranslationComponent(TNode node){
		return NodeUtil.isSourceNode(node);
	};
	
	


}
