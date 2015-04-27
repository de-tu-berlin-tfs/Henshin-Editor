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
package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import org.eclipse.emf.henshin.model.MultiUnit;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.interpreter.util.NodeUtil;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteOpRuleCommand;
//NEW GERARD
public class GenerateITRuleCommand extends GenerateOpRuleCommand {

	public GenerateITRuleCommand(Rule rule) {
		this(rule,null);
		
	}
	
	//REF 4
	//public GenerateITRuleCommand(Rule rule,IndependentUnit unit) {
	// NEW SUSANN
	public GenerateITRuleCommand(Rule rule, MultiUnit unit) {
		super(rule,unit);
		prefix = "IT_";
		OP_RULE_CONTAINER_PREFIX = "ITRule_";
		OP_RULE_FOLDER = "ITRuleFolder";
		OP_RULES_PNG = "ITRules.png";
	}
	
	//REF 5
	protected void deleteTRule(Rule tr) {
		DeleteOpRuleCommand deleteCommand = new DeleteOpRuleCommand(
				tr,null,RuleUtil.TGG_IT_RULE);
		deleteCommand.execute();
	}


	@Override
	protected void addNodeProcessors(){
		// process all nodes in the source and target component
		nodeProcessors.put(TripleComponent.SOURCE, new OpRuleNodeProcessor());
		nodeProcessors.put(TripleComponent.TARGET, new OpRuleNodeProcessor());
	};

	
	
	@Override
	protected String getRuleMarker() {
		return RuleUtil.TGG_IT_RULE;
	}
	
	@Override
	protected boolean filterNode(TNode node) {
		return NodeUtil.isSourceNode(node) || NodeUtil.isTargetNode(node);
	}
}
