package de.tub.tfs.henshin.tgg.interpreter;

import java.util.HashMap;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.interpreter.matching.constraints.UnaryConstraint;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TRule;


/**
 * This class is for checking the correct mapping in a execution cycle of FTRules.
 * It is given to the {@link EmfEngine} of henshin. 
 * @see ExecuteFTRulesCommand
 * @see EmfEngine#registerUserConstraint(Class, Object...)
 */
public class OpRuleAttributeConstraintEMF implements UnaryConstraint {

	/**
	 * This hashmap will be filled during the execution of all the {@link TRule}s in the 
	 * {@link ExecuteFTRulesCommand}. The hashmap contains all the already translated nodes 
	 * of the graph on which the {@link TRule}s are executed.
	 */
	private Set<EObject> markedNodesMap;
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
	
	
	
	private Attribute attr;
	

	/**
	 * the constructor
	 * @param ruleTNode see {@link FTRuleConstraint#ruleTNode}
	 * @param isTranslatedMap see {@link FTRuleConstraint#isTranslatedMap}
	 */
	public OpRuleAttributeConstraintEMF(Attribute attr, 
			Set<EObject> markedNodesMap,
			HashMap<EObject, Boolean> isTranslatedMap, 
			HashMap<EObject,HashMap<EAttribute, Boolean>> isTranslatedAttributeMap) {
		
		this.ruleTNode = (TNode)attr.getNode();
		this.ruleNodeMarker=ruleTNode.getMarkerType();
		
		this.attr = attr;
		this.markedNodesMap = markedNodesMap;
		this.isTranslatedAttributeMap = isTranslatedAttributeMap;

	}
	

	
	
	/** 
	 * Checks if the mapping in a {@link TRule}.
	 * @see org.eclipse.emf.henshin.interpreter.matching.constraints.HenshinUserConstraint#check(org.eclipse.emf.henshin.model.Node)
	 */
	@Override
	public boolean check(DomainSlot slot) {
		
		
		EAttribute eAttribute = attr.getType();

		
		EObject graphNode = slot.getValue();

		// attribute value shall not be null
		if (!graphNode.eIsSet(eAttribute))
			return false;
		
		
		
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
				// C. node node is not marked
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
		return markedNodesMap.contains(graphNode);
	}


	private boolean checkAttribute(EObject graphNode) {

		//find matching graph attribute (to the rule attribute)
		EAttribute eAttribute = attr.getType();
		
		
		if (null==((TAttribute) attr).getMarkerType()){ 
			// no marker available (e.g. in NAC)
			return true;
		}
		if (RuleUtil.TR_UNSPECIFIED.equals(((TAttribute) attr).getMarkerType())) {
			// marker is unspecified, i.e., arbitrary (e.g. in NAC)
			return true;
		}
		if (RuleUtil.Translated_Graph.equals(((TAttribute) attr).getMarkerType())) {
			// attribute is only in context but not to be translated, thus it is already translated
			if (isTranslatedAttributeMap.get(graphNode).containsKey(eAttribute))
				if (isTranslatedAttributeMap.get(graphNode).get(eAttribute))
				return true;
		}
		if (RuleUtil.Not_Translated_Graph.equals(((TAttribute) attr).getMarkerType())) {
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
