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

import java.util.HashMap;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.interpreter.matching.constraints.UnaryConstraint;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;


/**
 * This class is for checking the correct mapping in a execution cycle of FTRules.
 * It is given to the {@link EmfEngine} of henshin. 
 * @see ExecuteFTRulesCommand
 * @see EmfEngine#registerUserConstraint(Class, Object...)
 */
public class OpRuleAttributeConstraintEMF implements UnaryConstraint {

	/**
	 * Hashmap with the attribute markers that is updated during the execution of the operational rules 
	 */
	private HashMap<EObject, HashMap<EAttribute,Boolean>> isTranslatedAttributeMap;

	/**
	 * Hashmap with the node markers that is updated during the execution of the operational rules 
	 */
	private HashMap<EObject, Boolean> isTranslatedNodeMap;
	
	
	/**
	 * The node which can be mapped to another node in the graph (see 
	 * {@link FTRuleConstraint#check(Node graphNode)}). The node could be a node in
	 * a {@link Rule} or in a nac.
	 */
	private TNode ruleTNode;
	private String ruleNodeMarker;
	
	
	
	protected TAttribute ruleAttr;
	private String ruleAttrMarker;
	protected EAttribute eAttribute;
	protected Boolean nullValueMatching;

	/**
	 * the constructor
	 * @param ruleTNode see {@link FTRuleConstraint#ruleTNode}
	 * @param isTranslatedMap see {@link FTRuleConstraint#isTranslatedMap}
	 */
	public OpRuleAttributeConstraintEMF(Attribute attr, 
			HashMap<EObject, Boolean> isTranslatedMap, 
			HashMap<EObject,HashMap<EAttribute, Boolean>> isTranslatedAttributeMap) {
		
		this((TAttribute)attr,isTranslatedMap,isTranslatedAttributeMap,true);
	}
	

	/**
	 * the constructor
	 * @param ruleTNode see {@link FTRuleConstraint#ruleTNode}
	 * @param isTranslatedMap see {@link FTRuleConstraint#isTranslatedMap}
	 */
	public OpRuleAttributeConstraintEMF(Attribute attr, 
			HashMap<EObject, Boolean> isTranslatedMap, 
			HashMap<EObject,HashMap<EAttribute, Boolean>> isTranslatedAttributeMap, 
			boolean nullValueMatching) {
		
		this.ruleTNode = (TNode)attr.getNode();
		this.ruleNodeMarker=ruleTNode.getMarkerType();
		
		this.ruleAttr = (TAttribute)attr;
		ruleAttrMarker = ruleAttr.getMarkerType();
		this.isTranslatedNodeMap = isTranslatedMap;
		this.isTranslatedAttributeMap = isTranslatedAttributeMap;
		this.eAttribute = attr.getType();
		this.nullValueMatching=nullValueMatching;

	}
	
	/** 
	 * Checks if the mapping in a {@link TRule}.
	 * @see org.eclipse.emf.henshin.interpreter.matching.constraints.HenshinUserConstraint#check(org.eclipse.emf.henshin.model.Node)
	 */
	@Override
	public boolean check(DomainSlot slot) {
		
		
		EObject graphNode = slot.getValue();
		

		// handle case, when attribute value is null
		if (graphNode.eGet(eAttribute) == null) {

			// rule explicitly requires null value
			if ("null".equals(ruleAttr.getValue()))
				return true;

			// inconsistent, if
			// matching does not allow null values
			if (nullValueMatching == false)
				return false;
		}
		if (ruleAttrMarker == null || RuleUtil.TR_UNSPECIFIED.equals(ruleAttrMarker))
			// attribute is not marked or marked with wild card - no marker restriction - only component restriction
			return true;

		
		// attribute is marked with [tr] or [!tr]
		
		if (isMarkedNode(graphNode)) {
			// case: node is in marked component

			if (RuleUtil.Not_Translated_Graph.equals(ruleNodeMarker))
				// case: node is to be translated ([!tr])
				// then: the node marker check ensures already that the
				// attribute marker fits, i.e., nothing to do
				return true;
			else
				// cases:
				// A. node is context node ([tr])
				// B. node is marked with unspecified marker ([tr?])
				// check the attribute marker
				return (checkAttribute(graphNode));
		}
		// case: node is not in marked component, nothing to check
		return true;
	}

	/**
	 * Checks if a graphnode is a source node.
	 * @param graphNode
	 * @return true if it is a source node, else false
	 */

	private boolean isMarkedNode(EObject graphNode) {
		return isTranslatedNodeMap.containsKey(graphNode);
	}


	private boolean checkAttribute(EObject graphNode) {

		// graph node is in translated map
		// rule attribute is marked with [tr] or [!tr], thus check the marker

		HashMap<EAttribute, Boolean> graphNodeMap = isTranslatedAttributeMap.get(graphNode);
		if(graphNodeMap == null)
			// no attribute markers for graph node available in graph
			return false;
		Boolean graphAttrMarker = graphNodeMap.get(eAttribute);
		if(graphAttrMarker == null)
			// no marker for this attribute available in graph
			return false;
			
		// attribute marker is available in graph, thus check it
		
		if (RuleUtil.Translated_Graph.equals(ruleAttrMarker)) {
			// attribute is only in context but not to be translated, thus it is already translated
			return (Boolean.TRUE.equals(graphAttrMarker));
		}
		if (RuleUtil.Not_Translated_Graph.equals(ruleAttrMarker)) {
			// attribute is to be translated, thus it is not yet translated
			return (Boolean.FALSE.equals(graphAttrMarker));
		}

		// unknown case: should not occur
		System.out.println("ERROR: during matching - checking of attribute markers failed.");
		return false;
	}

}
