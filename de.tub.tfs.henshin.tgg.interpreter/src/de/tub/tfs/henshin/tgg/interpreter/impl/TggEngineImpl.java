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
package de.tub.tfs.henshin.tgg.interpreter.impl;
import java.io.ObjectOutputStream.PutField;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.info.RuleInfo;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.interpreter.TggEngine;
import de.tub.tfs.henshin.tgg.interpreter.TggTransformation;

public class TggEngineImpl extends EngineImpl implements TggEngine {
	/**
	 * 
	 */

	private static boolean invertMatchingOrder = true;
	
	public void setInverseMatchingOrder(boolean inverseMatchingOrder){
		invertMatchingOrder = inverseMatchingOrder;
		this.inverseMatchingOrder = inverseMatchingOrder;
		getOptions().put(OPTION_INVERSE_MATCHING_ORDER, inverseMatchingOrder);
	}
	
	protected ObjectCopier copier;
	
	protected TggTransformation trafo;

	public TggEngineImpl(EGraph graph) {
		this(graph,null);
	}
	
	/**
	 * @param executeFTRulesCommand
	 */
	public TggEngineImpl(EGraph graph,TggTransformation trafo) {
		// super(); // FIXME: why is this not called?
		this.trafo = trafo;
		if(trafo!=null)
			this.copier = new ObjectCopier(graph,trafo,this);
		else 
			this.copier = new ObjectCopier(graph,this);
		this.getScriptEngine().put("ObjectCopier",copier );
		this.getOptions().put(OPTION_SORT_VARIABLES, false);
		this.sortVariables = false;
		this.inverseMatchingOrder = invertMatchingOrder;
		getOptions().put(OPTION_INVERSE_MATCHING_ORDER, inverseMatchingOrder);
		
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

	public void updateOptions() {
		System.out.println("Setting inverse mathcing to " + invertMatchingOrder);
		inverseMatchingOrder = invertMatchingOrder;
		getOptions().put(OPTION_INVERSE_MATCHING_ORDER, invertMatchingOrder);
	}
	

}