package de.tub.tfs.henshin.tggeditor.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.matching.constraints.BinaryConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainChange;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.interpreter.matching.constraints.ReferenceConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.UnaryConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;


/**
 * This class is for checking the correct mapping in a execution cycle of FTRules.
 * It is given to the {@link EmfEngine} of henshin. 
 * @see ExecuteFTRulesCommand
 * @see EmfEngine#registerUserConstraint(Class, Object...)
 */
public class FTRuleAttributeConstraintEMF implements UnaryConstraint {

	/**
	 * This hashmap will be filled during the execution of all the {@link TRule}s in the 
	 * {@link ExecuteFTRulesCommand}. The hashmap contains all the already translated nodes 
	 * of the graph on which the {@link TRule}s are executed.
	 */
	private HashMap<EObject, Boolean> isTranslatedMap;
	private Set<EObject> sourceNodeMap;
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
	private HashMap<EObject, HashMap<EReference, HashMap<EObject,Boolean>>> isTranslatedEdgeMap;

	
	
	/**
	 * The node which can be mapped to another node in the graph (see 
	 * {@link FTRuleConstraint#check(Node graphNode)}). The node could be a node in
	 * a {@link Rule} or in a nac.
	 */
	private TNode ruleNode;
	private String ruleNodeMarker;

	
	
	
	/**
	 * Whether the node is marked to be translated before executing the rule.
	 */
	private Attribute ruleAttribute=null;
	private String ruleAttributeMarker=null;
	

	/**
	 * the constructor
	 * @param ruleNode see {@link FTRuleConstraint#ruleNode}
	 * @param isTranslatedMap see {@link FTRuleConstraint#isTranslatedMap}
	 */
	public FTRuleAttributeConstraintEMF(Attribute attr, 
			Set<EObject> sourceNodeMap,
			HashMap<EObject, Boolean> isTranslatedMap, 
			HashMap<EObject,HashMap<EAttribute, Boolean>> isTranslatedAttributeMap) {
		if(attr==null) return;
		ruleNode = (TNode)attr.getNode();
		this.ruleNodeMarker = this.ruleNode.getMarkerType();
		ruleAttribute = attr;
		ruleAttributeMarker = ((TAttribute) attr).getMarkerType();
		this.sourceNodeMap = sourceNodeMap;
		this.isTranslatedMap = isTranslatedMap;
		this.isTranslatedAttributeMap = isTranslatedAttributeMap;
	}
	

	
	
	/** 
	 * Checks if the mapping in a {@link TRule}.
	 * @see org.eclipse.emf.henshin.interpreter.matching.constraints.HenshinUserConstraint#check(org.eclipse.emf.henshin.model.Node)
	 */
	@Override
	public boolean check(DomainSlot slot) {
		String ruleAttributeMarker = ((TAttribute) ruleAttribute)
				.getMarkerType();
		if(ruleAttributeMarker==null) return true;

		EObject graphNode = slot.getValue();
		if (isTranslatedMap.containsKey(graphNode)) {
				// case: node is context node, then graph node has to be translated already
				if (RuleUtil.Translated_Graph.equals(ruleNodeMarker)) {
					// check attributes
					// moreover, all edges have to be checked to be consistent with translatedEdgeMap
					return (checkAttributes(graphNode));
				}	
		}
		
		return true;
	}


	private boolean checkAttributes(EObject graphNode) {

		//find matching graph attribute (to the rule attribute)
		EAttribute eAttribute = ruleAttribute.getType();

		boolean markerIsValid = RuleUtil.checkAttributeMarkerEMF(
				ruleAttributeMarker, isTranslatedAttributeMap, graphNode, eAttribute);
		if (markerIsValid)
			return true;
		else
			return false;
	}

}
