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
package de.tub.tfs.henshin.tggeditor.util.rule.concurrent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.impl.MappingImpl;
import org.eclipse.emf.henshin.model.impl.MappingListImpl;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;

public class ConcurrentRuleUtil {

	public static boolean isConcurrent(Rule rule1, Rule rule2){
		setLhsCoordinatesAndLayout(rule2);
		setLhsCoordinatesAndLayout(rule1);
		for (Node nr1 : rule1.getRhs().getNodes()){
			for (Node nl2 : rule2.getLhs().getNodes()){
				if (sameNodeType(nr1, nl2)){ //if nr1 has equal type and all attributes of nl2 then there is at least one intersecting node for creation of concurrent rule
					return true;
				}
			}
		}
		return false;
	}
	
	public static String getConcurrentRuleName(Rule ruleL, Rule ruleR, int index){
		return "("+ruleL.getName()+"|"+index+"|"+ruleR.getName()+")";
	}
	
	public static List<String> getAtomicRuleNames(String cRuleName){
		List<String> names = new LinkedList<String>();
		String delims = "[|()]";
		if (cRuleName!=null){
			String[] tokens = cRuleName.split(delims);
			for (String token : tokens){
				try{
					Integer.valueOf(token);
				}catch (NumberFormatException e){
					if (!token.equals(""))
						names.add(token);
				}
			}
		}
		return names;
	}
	
	
	public static Rule copyRule(Rule oldRule) {
		Copier copier = new Copier();
		EObject result = copier.copy(oldRule);
		copier.copyReferences();
		Rule r = (Rule)result;
		setLhsCoordinatesAndLayout(r);
		return r;
	}
	
	//NEW
	public static Rule getInverse(Rule rule){
		Rule inverseRule = copyRule(rule);
		inverseRule.setName("Inverse_"+rule.getName());
		setLhsCoordinatesAndLayout(inverseRule);
		inverseRule.setCheckDangling(true);
		inverseRule.setInjectiveMatching(true);
		//switch Rhs and Lhs
		Graph tmpLhs = inverseRule.getLhs();
		inverseRule.setLhs(inverseRule.getRhs());
		inverseRule.getLhs().setName("lhs");
		inverseRule.setRhs(tmpLhs);
		inverseRule.getRhs().setName("rhs");
		//update mapping
		MappingList ruleMappings = inverseRule.getMappings();
		Test.check(ruleMappings.isEmpty()==rule.getMappings().isEmpty());
		MappingList inverseRuleMappings = new MappingListImpl();
		if (!ruleMappings.isEmpty()){
			Test.check(!rule.getLhs().getNodes().contains(ruleMappings.get(0).getOrigin()));
		}
		
		for (Mapping ruleMapping : ruleMappings){
			Node nodeLRule = ruleMapping.getOrigin();
			Node nodeRRule = ruleMapping.getImage();
			/*
			Node nodeLInverseRule = null;
			Node nodeRInverseRule = null;
			
			boolean found = false;
			for (Node nodeLInvRule : inverseRule.getLhs().getNodes()){
				if (Graph2GraphCopyMappingList.isCopy(nodeLInvRule, nodeRRule)){
					nodeLInverseRule = nodeLInvRule;
					found =true;
					break;
				}
			}
			if (!found) throw new IllegalArgumentException("node not found");
			found = false;
			for (Node nodeRInvRule : inverseRule.getRhs().getNodes()){
				if (Graph2GraphCopyMappingList.isCopy(nodeRInvRule, nodeLRule)){
					nodeRInverseRule = nodeRInvRule;
					found = true;
					break;
				}
			}
			if (!found) throw new IllegalArgumentException("node not found");*/
			Mapping inverseRuleMapping = new MappingImpl(); 
			//inverseRuleMapping.setOrigin(nodeLInverseRule);
			//inverseRuleMapping.setImage(nodeRInverseRule);
			inverseRuleMapping.setOrigin(ruleMapping.getImage());
			inverseRuleMapping.setImage(ruleMapping.getOrigin());
			inverseRuleMappings.add(inverseRuleMapping);
		}
		inverseRule.getMappings().clear();
		inverseRule.getMappings().addAll(inverseRuleMappings);
		return inverseRule;
	}
	
	
	
	public static boolean setLhsCoordinatesAndLayout(Rule rule){
		if (rule.getLhs().getNodes().size() == 0) return false;
		//if (((TNode)rule.getLhs().getNodes().get(0)).getX()!=0) return false;
		for (Node n : rule.getLhs().getNodes()){
			TNode tn = (TNode) n;
			TNode tni = (TNode) rule.getAllMappings().getImage(n, rule.getRhs());
			tn.setX(tni.getX());
			tn.setY(tni.getY());
		}
		
		if (rule.getLhs() instanceof TripleGraph && rule.getRhs() instanceof TripleGraph){
			TripleGraph tg = (TripleGraph) rule.getRhs();
			((TripleGraph)rule.getLhs()).setDividerCT_X(tg.getDividerCT_X());
			((TripleGraph)rule.getLhs()).setDividerSC_X(tg.getDividerSC_X());
			((TripleGraph)rule.getLhs()).setDividerMaxY(tg.getDividerMaxY());
			((TripleGraph)rule.getLhs()).setDividerYOffset(tg.getDividerYOffset());
		}else{
			throw new IllegalArgumentException("Lhs has to be of Type TripleGraph");
		}
		return true;
	}
	
	public static boolean strictGreater(Rule rule1, Rule rule2) {
		List<String> atomNamesR2 = getAtomicRuleNames(rule2.getName());
		Set<String> atomNamesR1 =  new HashSet<String>(getAtomicRuleNames(rule1.getName()));
		if (atomNamesR1.size() <= atomNamesR2.size()) {
			return false;
		}
		for (String atomNameR2 : atomNamesR2) {
			if (!atomNamesR1.contains(atomNameR2)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean intersectingAtomicNames(Rule ruleL, Rule ruleR) {
		List<String> ruleLNames = getAtomicRuleNames(ruleL.getName());
		List<String> ruleRNames = getAtomicRuleNames(ruleR.getName());
		for (String nL : ruleLNames) {
			for (String nR : ruleRNames) {
				if (nL.equals(nR)) return true;
			}
		}
		return false;
	}
	
//	public static boolean concatOfRuleNamesEquivalentToConcurrentRuleName(Rule ruleL, Rule ruleR, Rule cRule) {
//		List<String> cRuleName = getAtomicRuleNames(cRule.getName());
//		String concatName = getConcurrentRuleName(ruleL, ruleR, 0);
//		List<String> conName = getAtomicRuleNames(concatName);
//		if (conName.size()!=cRuleName.size()) return false;
//		for (int i=0; i<conName.size();i++) {
//			if (!conName.get(i).equals(cRuleName.get(i))) return false;
//		}
//		return true;
//	}
//	
	public static boolean equivalent(Attribute a1, Attribute a2) {
		if (a1 == null && a2 == null) {
			return true;
		}else if (a1 == null || a2 == null) {
			return false;
		}
		return (a1.getValue() == a2.getValue() 
					|| (a1.getValue() != null
						&& a2.getValue() != null
						&& a1.getValue().equals(a2.getValue()))) 
				&& (a1.getType() == a2.getType() 
					|| (a1.getType() != null
						&& a2.getType() != null
						&& a1.getType().getName().equals(a2.getType().getName())));
	}
	
	public static boolean sameNodeType(Node nodeL, Node nodeR) {
		return (GraphicalNodeUtil.isCorrNode(nodeL) && GraphicalNodeUtil
				.isCorrNode(nodeR))
				|| !(nodeL == null
						|| nodeR == null
						|| nodeL.toString() != null
						    && nodeR.toString() != null
							&& !nodeL.toString().equals(nodeR.toString())
						|| nodeL.getType() != null
							&& nodeR.getType() != null
							&& nodeL.getType().getEPackage() != null
							&& nodeR.getType().getEPackage() != null
							&& !nodeL.getType().getEPackage()
								.equals(nodeR.getType().getEPackage())
						|| nodeL.getName() != null
							&& nodeR.getName() != null
							&& !nodeL.getName().equals(nodeR.getName()) 
						|| !GraphicalNodeUtil
						.guessTC(nodeR).equals(
								GraphicalNodeUtil.guessTC(nodeL)));
	}
	
	private static boolean equivalent(Node nodeR1, Node nodeR2){
		if (nodeR1 == nodeR2) return true;
		if (nodeR1 == null || nodeR2 == null) return false;
			if (!sameNodeType(nodeR1, nodeR2)
					|| nodeR1.getIncoming().size() != nodeR2.getIncoming().size() 
					|| nodeR1.getOutgoing().size() != nodeR2.getOutgoing().size()
					|| nodeR1.getAttributes().size() != nodeR2.getAttributes().size()) {
				return false;
			}
			
			boolean found = false;
			boolean flag = false;
			do {
				for (Attribute a1 : nodeR1.getAttributes()) {
					found = false;
					for (Attribute a2 : nodeR2.getAttributes()) {
						if (equivalent(a1, a2)){
							found = true;
							break;
						}
					}
					if (!found) {
						return false;
					}
				}
			Node tmp = nodeR1;
			nodeR1 = nodeR2;
			nodeR2 = tmp;
			flag = !flag;
			} while(flag);
		return true;
	}
	
//	public static boolean equivalentLHSNode(Node nodeR1, Node nodeR2) {
//		if (!equivalent(nodeR1, nodeR2)) {
//			return false;
//		}
//		Graph lhs1;
//		Graph lhs2;
//		Rule rule1;
//		Rule rule2;
//		
//		if (nodeR1.getGraph() != null 
//				&& nodeR2.getGraph() != null
//				&& nodeR1.getGraph().getRule() != null
//				&& nodeR2.getGraph().getRule() != null
//				){
//			lhs1 = nodeR1.getGraph();
//			lhs2 = nodeR2.getGraph();
//			rule1 = lhs1.getRule();
//			rule2 = lhs2.getRule();
//			
//			return equivalent(nodeL1, nodeL2);
//		}else {
//			return false;
//		}
//	}
	
	public static Set<Node> identicalRHSGraphs(Node nodeR1, Node nodeR2,  Map<Node, Node> mapR1ToR2, Map<Node, Node> mapR2ToR1){
		if (nodeR1 == null || nodeR2 == null) throw new IllegalArgumentException("node can not be null");
		if (nodeR1.getGraph() == null || nodeR2.getGraph() == null) throw new IllegalArgumentException("graph of node can not be null");
		if (nodeR1.getGraph().getRule() == null || nodeR2.getGraph().getRule() == null) throw new IllegalArgumentException("rule of node can not be null");
		if (!equivalent(nodeR1, nodeR2)) {
			return null;
		}
		
		
		Node nodeL1 = RuleUtil.getLHSNode(nodeR1);
		Node nodeL2 = RuleUtil.getLHSNode(nodeR2);
		Set<Node> allAddedNodesR1 = new HashSet<Node>();
		Set<Node> addedNodesR1 = null;
		
		Node aNodeR1;
		Node aNodeR2;
		Node aNodeL1;
		Node aNodeL2;
		Node matchANodeR1;
		Node matchANodeR2;
		
		if (!equivalent(nodeL1, nodeL2)) {
			return null;
		}
		
		
		mapR1ToR2.put(nodeR1, nodeR2);
		mapR2ToR1.put(nodeR2, nodeR1);
		allAddedNodesR1.add(nodeR1);
		

		if (mapR1ToR2.keySet().size()==nodeR1.getGraph().getNodes().size()) {
			return allAddedNodesR1;
		}
		
		boolean found = false;
		boolean flag1 = false;
		Node tmpNode = null;
		do {
			for (Edge edgeR1 : (flag1 ? nodeR1.getIncoming() : nodeR1.getOutgoing())) {
				aNodeR1 = (flag1 ? edgeR1.getSource() : edgeR1.getTarget());
				aNodeL1 = RuleUtil.getLHSNode(aNodeR1);
				found = false;
				matchANodeR1 = mapR1ToR2.get(aNodeR1);
				for (Edge edgeR2 : (flag1 ? nodeR2.getIncoming() : nodeR2.getOutgoing())) {
					aNodeR2 = (flag1 ? edgeR2.getSource() : edgeR2.getTarget());
					matchANodeR2 = mapR2ToR1.get(aNodeR2);
					aNodeL2 = RuleUtil.getLHSNode(aNodeR2);
					if (matchANodeR1 == aNodeR2 && matchANodeR2 == aNodeR1) {
						found = true;
						break;
					} else if (matchANodeR1 == null && matchANodeR2 == null && equivalent(aNodeR1, aNodeR2)) {
						
						if (hasEdgeFrom(nodeR1, aNodeR1) != hasEdgeFrom(nodeR2, aNodeR2)
								|| hasEdgeTo(nodeR1, aNodeR1) != hasEdgeTo(nodeR2, aNodeR2)
								||	(aNodeL1 != null && aNodeL2 != null 
									&& (!equivalent(aNodeL1, aNodeL2)
										|| (nodeL1 != null && nodeL2 != null &&
											(hasEdgeFrom(nodeL1, aNodeL1) != hasEdgeFrom(nodeL2, aNodeL2)
												|| hasEdgeTo(nodeL1, aNodeL1) != hasEdgeTo(nodeL2, aNodeL2)))))) {
								continue;
						}
						addedNodesR1 = identicalRHSGraphs(aNodeR1, aNodeR2, mapR1ToR2, mapR2ToR1);
						if (addedNodesR1 != null) {
							allAddedNodesR1.addAll(addedNodesR1);
							found = true;
							break;
						}
					}
				}
				if (!found) {
					for (Node addedNodeR1 : allAddedNodesR1) {
						//Node addedNodeL1 = RuleUtil.getLHSNode(addedNodeR1);
						Node removedNodeR2 = mapR1ToR2.remove(addedNodeR1);
						Node removedNodeR1 = mapR2ToR1.remove(removedNodeR2);
						if (removedNodeR1 != addedNodeR1) throw new IllegalArgumentException("Problem");
					}
					return null;
				}
			}
			flag1 = !flag1;
		} while (flag1);
		
		return allAddedNodesR1;
	}
	
	public static boolean hasEdgeFrom(Node node, Node node2){
		Graph graph = node.getGraph();
		if (graph == null) return false;
		for (Edge edge : node.getIncoming()) {
			if (edge.getSource()==node2) return true;
		}
		return false;
	}
	
	public static boolean hasEdgeTo(Node node, Node node2){
		Graph graph = node.getGraph();
		if (graph == null) return false;
		for (Edge edge : node.getOutgoing()) {
			if (edge.getTarget()==node2) return true;
		}
		return false;
	}
	
	public static boolean equivalent(Rule rule1, Rule rule2){
		if (rule1 == rule2) return true;
		if (rule1 == null && rule2 == null) {
			return true;
		} else if (rule1 == null || rule2 == null){
			return false;
		}
		
		Graph rhs1 = rule1.getRhs();
		Graph rhs2 = rule2.getRhs();
		Graph lhs1 = rule1.getLhs();
		Graph lhs2 = rule2.getLhs();
		if (rhs1 == null || rhs2 == null || lhs1 == null || lhs2 == null
				|| rhs1.getNodes().size() != rhs2.getNodes().size()
				|| lhs1.getNodes().size() != lhs2.getNodes().size()
				|| rhs1.getEdges().size() != rhs2.getEdges().size()
				|| lhs1.getEdges().size() != lhs2.getEdges().size()) {
			return false;
		}

		Map<Node, Node> mapR1ToR2 = new HashMap<Node, Node>();
		Map<Node, Node> mapR2ToR1 = new HashMap<Node, Node>();
		
		for (Node nodeR1 : rhs1.getNodes()){
			for (Node nodeR2 : rhs2.getNodes()){
				System.out.println(nodeR1.getGraph().getRule()+ "  "+nodeR2.getGraph().getRule());
				if (null != identicalRHSGraphs(nodeR1, nodeR2, mapR1ToR2, mapR2ToR1)){
					System.out.println("Equivalent rules : "+rule1.getName()+" : "+rule2.getName());
					return true;
				} else if (!mapR1ToR2.isEmpty() || !mapR2ToR1.isEmpty()){
					throw new IllegalArgumentException("Problem");
				};
			}
		}
		
//		boolean found = false;
//		Node uniqueNode1 = null;
//		Node uniqueNode2 = null;
//		//find unique node
//		for (Node node1a : rhs1.getNodes()) {
//			found = false;
//			for (Node node1b : rhs1.getNodes()){
//				if (node1a==node1b) {
//					continue;
//				}
//				if (equivalent(node1a, node1b)) {
//					found = true;
//					break;
//				}
//			}
//			if (!found) {
//				uniqueNode1 = node1a;
//				break;
//			}
//		}
//		if (uniqueNode1!=null) {
//			for (Node node2 : rhs2.getNodes()) {
//				if (equivalent(uniqueNode1, node2)) {
//					uniqueNode2 = node2;
//					break;
//				}
//			}
//			if (uniqueNode2 == null) {
//				return false;
//			}
//		}
		
	return false;
	}
	
	public static MappingList getInverseMappingList(MappingList mlist){
		MappingList invMappings = new MappingListImpl();
		for (Mapping ruleLMapping : mlist){
			Node image = ruleLMapping.getImage();
			Node origin = ruleLMapping.getOrigin();
			invMappings.add(image, origin);
		}
		return invMappings;
	}
	
	// NEW
		public static void mark(TGGRule rule) {
			for (Node nr : rule.getRhs().getNodes()) {
				TNode nodeR = (TNode) nr;
				Test.check(nr.getGraph() ==rule.getRhs() && nr.getGraph().getRule() == rule);
				Node nodeL = RuleUtil.getLHSNode(nr);
				Node nL = rule.getMappings().getOrigin(nodeR);
				Test.check(nL==nodeL);
				if (nodeL == null) {
					nodeR.setMarkerType(RuleUtil.NEW);
				} else {
					nodeR.setMarkerType(null);
				}
				// handle attributes
				for (Attribute at : nodeR.getAttributes()) {
					TAttribute attR = (TAttribute) at;
					Attribute attL = RuleUtil.getLHSAttribute(attR);
					if (attL == null) {
						attR.setMarkerType(RuleUtil.NEW);
					} else {
						attR.setMarkerType(null);
					}
				}
			}
			for (Edge ed : rule.getRhs().getEdges()) {
				TEdge edgeR = (TEdge) ed;
				Edge edgeL = RuleUtil.getLHSEdge(edgeR);
				if (edgeL == null) {
					edgeR.setMarkerType(RuleUtil.NEW);
				} else {
					edgeR.setMarkerType(null);
				}
			}
		}
}
