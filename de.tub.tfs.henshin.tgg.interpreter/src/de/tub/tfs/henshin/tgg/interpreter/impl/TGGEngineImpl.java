package de.tub.tfs.henshin.tgg.interpreter.impl;
/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */


import java.util.HashMap;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.info.RuleInfo;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.interpreter.TGGEngine;

public class TGGEngineImpl extends EngineImpl implements TGGEngine {
	/**
	 * 
	 */

	private ObjectCopier copier;

	public TGGEngineImpl(EGraph graph) {
		this(graph,null,null,null);
	}
	
	/**
	 * @param executeFTRulesCommand
	 */
	public TGGEngineImpl(EGraph graph,HashMap<Node, Boolean> isTranslatedMap, 
			HashMap<Attribute, Boolean> isTranslatedAttributeMap, 
			HashMap<Edge, Boolean> isTranslatedEdgeMap) {
		// super(); // FIXME: why is this not called?
		this.copier = new ObjectCopier(graph,this,isTranslatedMap,isTranslatedAttributeMap,isTranslatedEdgeMap);
		this.getScriptEngine().put("ObjectCopier",copier );
		this.sortVariables = false;
		this.inverseMatchingOrder=false;
		
	}
	
	/* (non-Javadoc)
	 * @see de.tub.tfs.henshin.tgg.interpreter.impl.TGGEngine#postProcess(org.eclipse.emf.henshin.interpreter.Match)
	 */
	@Override
	public void postProcess(Match m){
		copier.postProcess(m);
	}
	
	@Override
	protected RuleInfo getRuleInfo(Rule rule) {
		RuleInfo ruleInfo = ruleInfos.get(rule);
		if (ruleInfo == null) {
			// Create the rule info:
			ruleInfo = new RuleInfo(rule, this);
			ruleInfos.put(rule, ruleInfo);
			// Listen to changes:
			//rule.eAdapters().add(ruleListener);
			
	
			// Check for missing factories:			
			for (Node node : ruleInfo.getChangeInfo().getCreatedNodes()) {
				if (node.getType()==null) {
					throw new RuntimeException("Missing type for " + node);
				}
				if (node.getType().getEPackage()==null || 
					node.getType().getEPackage().getEFactoryInstance()==null) {
					throw new RuntimeException("Missing factory for '" + node + 
							"'. Register the corresponding package, e.g. using PackageName.eINSTANCE.getName().");
				}
			}
		}
		return ruleInfo;
	}
}