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

public class OpRuleNodeConstraint implements UnaryConstraint {

	

	/**
	 * This hashmap will be filled during the execution of all the {@link TRule}s in the 
	 * {@link ExecuteOpRulesCommand}. The hashmap contains all the already translated nodes 
	 * of the graph on which the {@link TRule}s are executed.
	 */
	private HashMap<Node, Boolean> isTranslatedMap;

	/**
	 * The node which can be mapped to another node in the graph (see 
	 * {@link OpRuleConstraint#check(Node graphNode)}). The node could be a node in
	 * a {@link Rule} or in a nac.
	 */
	private Node node;


	private EGraph eGraph;

	/**
	 * the constructor
	 * @param node see {@link OpRuleConstraint#node}
	 * @param isTranslatedMap see {@link OpRuleConstraint#isTranslatedMap}
	 */
	public OpRuleNodeConstraint(Node node, 
			HashMap<Node, Boolean> isTranslatedMap,EGraph eGraph) {
		this.eGraph = eGraph;
		this.node = node;
		this.isTranslatedMap = isTranslatedMap;
	}
	

	
	
	/** 
	 * Checks if the mapping in a {@link TRule}.
	 * @see org.eclipse.emf.henshin.interpreter.matching.constraints.HenshinUserConstraint#check(org.eclipse.emf.henshin.model.Node)
	 */
	@Override
	public boolean check(DomainSlot slot) {
		String nodeMarker = ((TNode) node).getMarkerType();
		// case: no marker - e.g. element in filter NAC that has not been marked explicitly
		if(nodeMarker==null) return true;
		Node graphNode = NodeUtil.getGraphNode(slot,eGraph);
		// case: node is in marked component
		if (isTranslatedMap.containsKey(graphNode)) {
			return RuleUtil.checkNodeMarker(nodeMarker,isTranslatedMap,graphNode);
		}
		// graph node is not in marked component
		else return true;
	}

	
	



}
