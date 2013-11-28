package de.tub.tfs.henshin.tggeditor.commands;

import java.util.HashMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.interpreter.matching.constraints.UnaryConstraint;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tggeditor.util.NodeTypes;
import de.tub.tfs.henshin.tggeditor.util.NodeTypes.NodeGraphType;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.util.TggHenshinEGraph;

public class FTRuleNodeConstraint implements UnaryConstraint {

	

	/**
	 * This hashmap will be filled during the execution of all the {@link TRule}s in the 
	 * {@link ExecuteFTRulesCommand}. The hashmap contains all the already translated nodes 
	 * of the graph on which the {@link TRule}s are executed.
	 */
	private HashMap<Node, Boolean> isTranslatedMap;

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

	private EGraph eGraph;

	/**
	 * the constructor
	 * @param node see {@link FTRuleConstraint#node}
	 * @param isTranslatedMap see {@link FTRuleConstraint#isTranslatedMap}
	 */
	public FTRuleNodeConstraint(Node node, 
			HashMap<Node, Boolean> isTranslatedMap,EGraph eGraph) {
		this.eGraph = eGraph;
		this.node = node;
		this.isTranslatedMap = isTranslatedMap;
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
		Node graphNode = getGraphNode(slot);
		if (isSourceNode(graphNode)) {
			if (this.node.eContainer().eContainer() instanceof Rule) {
				// case: node is context node, then graph node has to be translated already
				if (nodeIsTranslated && isTranslatedMap.get(graphNode)) {
					// check attributes
					// moreover, all edges have to be checked to be consistent with translatedEdgeMap
					return true;
				}				
				else if (!nodeIsTranslated && isTranslatedMap.get(graphNode)!=null && isTranslatedMap.get(graphNode).equals(false)) {
					// since node is not yet translated, 
					// also the adjacent edges and attributes are not yet translated and do not need to be checked
					return true;
				}
			}
			// for NAC nodes (evntl only not mapped from LHS to NAC graph ???)
			else if (isTranslatedMap.containsKey(graphNode)) {
					return true;
			}
			// else
			return false;
		}
		
		// graph node is in TARGET or CORRESPONDENCE component: check that rule node is not in source component
		Node rhsNode=RuleUtil.getRHSNode(node);
		if (NodeUtil.isSourceNodeByPosition((TNode) rhsNode)) 
			return false;
		else
			return true;
	}

	private Node getGraphNode(DomainSlot slot) {
		return ((TggHenshinEGraph)eGraph).getObject2NodeMap().get(slot.getValue());
	}
	
	private Node getGraphNode(EObject slot) {
		return ((TggHenshinEGraph)eGraph).getObject2NodeMap().get(slot);
	}
	
	/**
	 * Checks if a graphnode is a source node.
	 * @param graphNode
	 * @return true if it is a source node, else false
	 */
	private boolean isSourceNode(Node graphNode) {
		//return isTranslatedMap.get(graphNode)!=null;
		NodeGraphType type = NodeTypes.getNodeGraphType(graphNode);
		return type == NodeGraphType.SOURCE;
	}



}
