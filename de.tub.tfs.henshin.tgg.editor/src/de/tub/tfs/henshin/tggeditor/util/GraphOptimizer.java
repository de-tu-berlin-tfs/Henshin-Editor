package de.tub.tfs.henshin.tggeditor.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.interpreter.util.NodeUtil;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;

public class GraphOptimizer implements Comparator<Node>{

	
	public static void optimize(Graph graph){
		if (((TGGRule)graph.getRule()).isManualMatchingOrder()){
			ArrayList<Node> nodes = new ArrayList<Node>();
			for (Node n : graph.getRule().getRhs().getNodes()) {
				Node lhs = RuleUtil.getLHSNode(n);
				if (lhs != null)
					nodes.add(lhs);
			}
			graph.getNodes().clear();
			graph.getNodes().addAll(nodes);
		} else {
			ArrayList<Node> nodes = new ArrayList<Node>(graph.getNodes());
			Collections.sort(nodes,new GraphOptimizer((TGGRule) graph.getRule()));


			optimizeConnectivity(nodes,new LinkedHashSet<Node>());

			graph.getNodes().clear();
			graph.getNodes().addAll(nodes);		
		}
	}

	private static void optimizeConnectivity(ArrayList<Node> nodes,LinkedHashSet<Node> visited){
		Node current = nodes.remove(0);
		visited.add(current);
		boolean added = false;
		do {
			added = false;
			outer: for (Node node : nodes) {
				if (node.getIncoming().isEmpty()){
					if (!visited.contains(node)){
						visited.add(node);
						added = true;
					}
				} else {
					for (Edge e : node.getIncoming()) {
						if (visited.contains(e.getSource())){
							if (!visited.contains(node)){
								visited.add(node);
								added = true;
								continue outer;
							}
						}
						if (e.getType().isContainment() && visited.contains(e.getSource())){
							if (!visited.contains(node)){
								visited.add(node);
								added = true;
								continue outer;
							}
						}
					}
				}		
			}
		} while (added);
		if (nodes.size() == 0){
			nodes.addAll(visited);
			return;
		}
		optimizeConnectivity(nodes, visited);
	}
	
	private TGGRule rule;

	public GraphOptimizer(TGGRule rule) {
		this.rule = rule;
	}
	
	@Override
	public int compare(Node arg0, Node arg1) {
			TripleComponent node0 = NodeUtil.guessTripleComponent((TNode) arg0);
			TripleComponent node1 = NodeUtil.guessTripleComponent((TNode) arg1);
			TripleComponent target = null;
			String ruleMarkerType=rule.getMarkerType();
			if(ruleMarkerType.equals(RuleUtil.TGG_FT_RULE))
				target=TripleComponent.SOURCE;
			if (ruleMarkerType.equals(RuleUtil.TGG_CC_RULE)){
				target=TripleComponent.SOURCE;
			}
			if (ruleMarkerType.equals(RuleUtil.TGG_IT_RULE)){
				target=TripleComponent.SOURCE;
			}
			if (ruleMarkerType.equals(RuleUtil.TGG_BT_RULE))
				target=TripleComponent.TARGET;
			int cmp = 0;
			if (target==null){
				System.out.println("Target id NULL!");
				return cmp;
			}
			if (!node0.equals(node1)){
				if (target.equals(node0))
					cmp = -1;
				if (target.equals(node1))
					cmp = 1;
				if (node0.equals(TripleComponent.CORRESPONDENCE)){
					if (node1.equals(TripleComponent.CORRESPONDENCE)){
						cmp = 0;
					} else
					if (!target.equals(node1)){
						cmp = 1;
					}
				}
				if (node1.equals(TripleComponent.CORRESPONDENCE)){
					if (node0.equals(TripleComponent.CORRESPONDENCE)){
						cmp = 0;
					} else
					if (!target.equals(node0)){
						cmp = -1;
					}
				}
			}


			if (cmp == 0)
				cmp = getMajorConstraintCount(arg1) - getMajorConstraintCount(arg0);
			if (cmp == 0)
				cmp = getHasContainerConstraint(arg1) - getHasContainerConstraint(arg0);
			if (cmp == 0)
				cmp = getAttributeConstraintCount(arg1) - getAttributeConstraintCount(arg0);
			return cmp; 
	}
	
	private int getMajorConstraintCount(Node node){
		TripleComponent target = null;
		if (rule.getMarkerType().equals(RuleUtil.TGG_FT_RULE))
			target = TripleComponent.SOURCE;
		if (rule.getMarkerType().equals(RuleUtil.TGG_BT_RULE))
			target = TripleComponent.TARGET;
		if (rule.getMarkerType().equals(RuleUtil.TGG_CC_RULE))
			target = TripleComponent.SOURCE;
		//NEW
		if (rule.getMarkerType().equals(RuleUtil.TGG_IT_RULE))
			target = TripleComponent.SOURCE;
		
		if (TripleComponent.CORRESPONDENCE.equals(NodeUtil.guessTripleComponent((TNode) node))){
			int amt = 0;
			for (Edge e : node.getAllEdges()) {
					if (target.equals(NodeUtil.guessTripleComponent((TNode) e.getSource()))){
						amt += 1;
					}
					if (target.equals(NodeUtil.guessTripleComponent((TNode) e.getTarget()))){
						amt += 1;
					}				
			}
			return amt;
		}
		return node.getOutgoing().size();
	}
	
	private int getHasContainerConstraint(Node node){
		for (Edge e : node.getIncoming()) {
			if (e.getType().isContainment()){
				return 1;
			}
		}
		return 0;
	}
	
	private int getAttributeConstraintCount(Node node){
		return node.getAttributes().size();
	}
}
