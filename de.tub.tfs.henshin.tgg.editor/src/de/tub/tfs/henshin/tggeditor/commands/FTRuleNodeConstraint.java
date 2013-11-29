package de.tub.tfs.henshin.tggeditor.commands;

import java.util.HashMap;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.interpreter.matching.constraints.UnaryConstraint;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TRule;
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
	}
	

	
	
	/** 
	 * Checks if the mapping in a {@link TRule}.
	 * @see org.eclipse.emf.henshin.interpreter.matching.constraints.HenshinUserConstraint#check(org.eclipse.emf.henshin.model.Node)
	 */
	@Override
	public boolean check(DomainSlot slot) {
		Node graphNode = getGraphNode(slot);
		if (NodeUtil.isSourceNode(graphNode)) {
			if (this.node.eContainer().eContainer() instanceof Rule) {
				// case: node is context node, then graph node has to be translated already
				if (nodeIsTranslated && isTranslatedMap.get(graphNode)) {
					return true;
				}				
				// case: node is context node, then graph node has to be translated already
				else if (!nodeIsTranslated && isTranslatedMap.get(graphNode)!=null && isTranslatedMap.get(graphNode).equals(false)) {
					return true;
				}
			}
			// case: node is not marked (e.g. for NAC nodes) (evntl only not mapped from LHS to NAC graph ???)
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
	
	



}
