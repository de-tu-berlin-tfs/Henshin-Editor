package de.tub.tfs.henshin.tggeditor.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainChange;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.interpreter.matching.constraints.ReferenceConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.UnaryConstraint;
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
import de.tub.tfs.henshin.tggeditor.util.TggHenshinEGraph;


/**
 * This class is for checking the correct mapping in a execution cycle of FTRules.
 * It is given to the {@link EmfEngine} of henshin. 
 * @see ExecuteFTRulesCommand
 * @see EmfEngine#registerUserConstraint(Class, Object...)
 */
public class BTRuleAttributeConstraint implements UnaryConstraint {

	/**
	 * This hashmap will be filled during the execution of all the {@link TRule}s in the 
	 * {@link ExecuteFTRulesCommand}. The hashmap contains all the already translated nodes 
	 * of the graph on which the {@link TRule}s are executed.
	 */
	private HashMap<Node, Boolean> isTranslatedMap;
	/**
	 * This hashmap will be filled during the execution of all the {@link TRule}s in the 
	 * {@link ExecuteFTRulesCommand}. The hashmap contains all the already translated edges 
	 * of the graph on which the {@link TRule}s are executed.
	 */
	private HashMap<Attribute, Boolean> isTranslatedAttributeMap;
	/**
	 * This hashmap will be filled during the execution of all the {@link TRule}s in the 
	 * {@link ExecuteFTRulesCommand}. The hashmap contains all the already translated edges 
	 * of the graph on which the {@link TRule}s are executed.
	 */
	private HashMap<Edge, Boolean> isTranslatedEdgeMap;

	
	
	/**
	 * The node which can be mapped to another node in the graph (see 
	 * {@link FTRuleConstraint#check(Node graphNode)}). The node could be a node in
	 * a {@link Rule} or in a nac.
	 */
	private Node node;

	/**
	 * The rule containing the node.
	 */
	private Rule rule=null;
	
	
	
	/**
	 * Whether the node is marked to be translated before executing the rule.
	 */
	private Boolean nodeIsTranslated;
	private EGraph graph;
	private Attribute attr;

	/**
	 * the constructor
	 * @param node see {@link FTRuleConstraint#node}
	 * @param isTranslatedMap see {@link FTRuleConstraint#isTranslatedMap}
	 */
	public BTRuleAttributeConstraint(Attribute attr, 
			HashMap<Node, Boolean> isTranslatedMap, 
			HashMap<Attribute, Boolean> isTranslatedAttributeMap, 
			EGraph graph) {
		this.graph = graph;
		this.node = attr.getNode();
		this.attr = attr;
		this.isTranslatedMap = isTranslatedMap;
		this.isTranslatedAttributeMap = isTranslatedAttributeMap;
		this.nodeIsTranslated = NodeUtil.getNodeIsTranslated(this.node);
		if (nodeIsTranslated == null)
			nodeIsTranslated = true;
		if (this.node.eContainer().eContainer() instanceof Rule) {
			// node is contained in the RHS
			rule = (Rule) this.node.eContainer().eContainer();
		} 
		else // node is contained in a NAC 
			if(this.node.eContainer().eContainer().eContainer() instanceof Rule){
			rule = (Rule) this.node.eContainer().eContainer().eContainer();
		}
		
	}
	

	
	
	/** 
	 * Checks if the mapping in a {@link TRule}.
	 * @see org.eclipse.emf.henshin.interpreter.matching.constraints.HenshinUserConstraint#check(org.eclipse.emf.henshin.model.Node)
	 */
	@Override
	public boolean check(DomainSlot slot) {
		Node graphNode = getGraphNode(slot,graph);
		if (isTargetNode((TNode) graphNode)) {
			if (this.node.eContainer().eContainer() instanceof Rule) {
				// case: node is context node, then graph node has to be translated already
				if (nodeIsTranslated && isTranslatedMap.get(graphNode)) {
					// check attributes
					// moreover, all edges have to be checked to be consistent with translatedEdgeMap
					return (checkAttributes(graphNode));
				}	
			}
			
		}

		return true;
	}

	private boolean isTargetNode(TNode graphNode) {
		return isTranslatedMap.get(graphNode)!=null;

	}




	private Node getGraphNode(DomainSlot slot, EGraph graph) {
		// TODO Auto-generated method stub
		return ((TggHenshinEGraph)graph).getObject2NodeMap().get(slot.getValue());
	}
	
	private Node getGraphNode(EObject slot, EGraph graph) {
		// TODO Auto-generated method stub
		return ((TggHenshinEGraph)graph).getObject2NodeMap().get(slot);
	}




	private boolean checkAttributes(Node graphNode) {
		//find matching graph attribute (to the rule attribute)
		Attribute graphAttribute = ExecuteFTRulesCommand.findAttribute(graphNode, attr.getType());
		if (graphAttribute == null) 
			return false;
		if (RuleUtil.Translated.equals(((TAttribute) attr).getMarkerType() )) {
			// attribute is to be translated, thus it is not yet translated
			if (isTranslatedAttributeMap.get(graphAttribute))
				return false;
		}
		else // attribute is only in context but not to be translated, thus it is already translated
			if (!isTranslatedAttributeMap.get(graphAttribute))
				return false;

		return true;
	}

	
}
