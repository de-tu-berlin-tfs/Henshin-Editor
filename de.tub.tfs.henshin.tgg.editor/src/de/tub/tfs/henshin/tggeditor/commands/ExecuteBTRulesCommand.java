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


/**
 * The Class ExecuteFTRulesCommand executes all the given Rules ({@link TRule}) on a given graph. For the
 * execution are mainly the classes from org.eclipse.emf.henshin.interpreter used. The mapping 
 * of the RuleApplication will be checked with the class {@link FTRuleConstraint}.
 * There will be also the layouts for nodes and edges created.
 */
public class ExecuteBTRulesCommand extends ExecuteOpRulesCommand {


	
	
	/**the constructor
	 * @param graph {@link ExecuteBTRulesCommand#graph}
	 * @param fTRuleList {@link ExecuteBTRulesCommand#opRulesList}
	 */
	public ExecuteBTRulesCommand(Graph graph, List<Rule> fTRuleList) {
		super(graph,fTRuleList);
		consistencyType = "Target";
		consistencyTypeLowerCase = "target";
	}
}
