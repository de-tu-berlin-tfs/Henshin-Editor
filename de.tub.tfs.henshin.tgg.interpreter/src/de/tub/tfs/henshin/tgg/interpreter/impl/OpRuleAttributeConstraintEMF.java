/*******************************************************************************
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
	 * This hashmap will be filled during the execution of all the {@link TRule}s in the 
	 * {@link ExecuteFTRulesCommand}. The hashmap contains all the already translated edges 
	 * of the graph on which the {@link TRule}s are executed.
	 */
	private HashMap<EObject, HashMap<EAttribute,Boolean>> isTranslatedAttributeMap;
	/**
	 * This hashmap will be filled during the execution of all the {@link TRule}s in the 
	 * {@link ExecuteFTRulesCommand}. The hashmap contains all the already translated edges 
	 * of the graph on which the {@link TRule}s are executed.
	 * An edge is identified by the triple (source, type, target)
	 */

	
	
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
		if (graphNode.eGet(eAttribute)==null)
			// inconsistent, if 
			// a) matching does not allow null values or 
			// b) attribute value is to be translated (it is not in the hash map of marked attributes and cannot be marked)
			if(nullValueMatching==false || RuleUtil.Not_Translated_Graph.equals(ruleAttrMarker))
				return false;
		
		if (ruleAttrMarker == null || RuleUtil.TR_UNSPECIFIED.equals(ruleAttrMarker))
			// attribute is not marked or marked with wild card - no marker restriction - only component restriction
			return true;
		
		if (isMarkedNode(graphNode)) {
			// case: node is in marked component

			if (RuleUtil.Not_Translated_Graph.equals(ruleNodeMarker))
				// case:
				// node is to be translated, then the node marker check
				// ensures already that the attribute marker fits, i.e., nothing
				// to do
				return true;
			else
				// cases:
				// A. node is context node
				// B. node is marked with unspecified marker
				// C. node is not marked
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
		return isTranslatedAttributeMap.containsKey(graphNode);
	}


	private boolean checkAttribute(EObject graphNode) {

		//find matching graph attribute (to the rule attribute)
		EAttribute eAttribute = ruleAttr.getType();
		
		
		if (null==((TAttribute) ruleAttr).getMarkerType()){ 
			// no marker available (e.g. in NAC)
			return true;
		}
		if (RuleUtil.Translated_Graph.equals(ruleAttrMarker)) {
			// attribute is only in context but not to be translated, thus it is already translated
			if (isTranslatedAttributeMap.get(graphNode).containsKey(eAttribute))
				if (isTranslatedAttributeMap.get(graphNode).get(eAttribute))
				return true;
		}
		if (RuleUtil.Not_Translated_Graph.equals(ruleAttrMarker)) {
			// attribute is to be translated, thus it is not yet translated
			if (!isTranslatedAttributeMap.get(graphNode).containsKey(eAttribute))
				return true;
			else if (!isTranslatedAttributeMap.get(graphNode).get(eAttribute))
				return true;
		}
		// case: match is inconsistent with marking
		return false;
	}

}
