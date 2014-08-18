/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */

package de.tub.tfs.henshin.tgg.interpreter.impl;

import java.util.HashMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.interpreter.matching.constraints.UnaryConstraint;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;


/**
 * handling of translation maps during execution:
 * 1) init maps:
 * node Map      :  nM[x]=false for each node of input graph
 * attribute Map :  nA[x]=[]    for each node of input graph
 * edge Map      :  nE[x]=[]    for each node of input graph
 * 
 * 2) after each trafo ste:
 * node Map      :  nM[x]       =true for each translated node x
 * attribute Map :  nA[x][a]    =true for each translated attribute of node x
 * edge Map      :  nE[x][xt][e]=true for each translated edge from x to xt
 * 
 * */
 

/**
 * This class is for use during match finding, it checks that the translation markers fit to the mappings of the match.
 * It is given to the {@link EmfEngine} of henshin. 
 * @see ExecuteOpRulesCommand
 * @see EmfEngine#registerUserConstraint(Class, Object...)
 */
public class OpRuleNodeConstraintEMF implements UnaryConstraint {

	/**
	 * This hashmap is initially filled with [x]=false and will be modified to [x]=true 
	 * during the execution of all the {@link TRule}s in the 
	 * {@link ExecuteOpRulesCommand}. The hashmap contains all the nodes that shall be translated
	 * of the graph on which the {@link TRule}s are executed.
	 */
	private HashMap<EObject, Boolean> isTranslatedMap;
	
	
	/**
	 * The node which can be mapped to another node in the graph (see 
	 * {@link FTRuleConstraint#check(Node graphNode)}). The node could be a node in
	 * a {@link Rule} or in a nac.
	 */
	private TNode ruleTNode;
	private String ruleNodeMarker;
	

	/**
	 * the constructor
	 * @param ruleNode see {@link FTRuleConstraint#ruleTNode}
	 * @param isTranslatedMap see {@link FTRuleConstraint#isTranslatedMap}
	 */
	public OpRuleNodeConstraintEMF(Node ruleNode, 
			HashMap<EObject, Boolean> isTranslatedMap) {
		this.ruleTNode = (TNode)ruleNode;
		this.ruleNodeMarker=ruleTNode.getMarkerType();
		this.isTranslatedMap = isTranslatedMap;
	}
	

	
	
	/** 
	 * Checks whether the node mapping is compatible with the translation markers in the {@link TRule}.
	 * @see org.eclipse.emf.henshin.interpreter.matching.constraints.HenshinUserConstraint#check(org.eclipse.emf.henshin.model.Node)
	 */
	@Override
	public boolean check(DomainSlot slot) {

		EObject graphNode = slot.getValue();
		if (ruleNodeMarker==null || RuleUtil.TR_UNSPECIFIED.equals(ruleNodeMarker)){
			// node is not marked or marked with wild card - no marker restriction - only component restriction
				return true;				
		}

		// rule node has translation marker
		if (isInMarkedComponent(graphNode)) {
			// node is in the marked component
			if (RuleUtil.Translated_Graph.equals(ruleNodeMarker)
					&& isTranslatedMap.get(graphNode)) {
				// case: node is context node, then graph node has to be
				// translated already
				return true;
			}
			if (RuleUtil.Not_Translated_Graph.equals(ruleNodeMarker)
					&& isTranslatedMap.get(graphNode)==false) {
				// case: node is to be translated, then graph node has to be
				// untranslated
				return true;
			}
		}
		
		return false;

//		// the following code should never be used. The marked component has to be initialized always correctly by the commands for executing the transformation 
//		// for unmarked components - 
//		Node rhsNode=RuleUtil.getRHSNode(this.ruleTNode);
//		if (NodeUtil.isSourceNodeByPosition((TNode) rhsNode)) 
//			return false;
//		else
	}


	private boolean isInMarkedComponent(EObject graphNode) {
		return isTranslatedMap.containsKey(graphNode);
	}

}
