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
package de.tub.tfs.henshin.tggeditor.util;

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
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.util.rule.concurrent.Test;


public class GraphicalRuleUtil {
	
	//NEW
	public static Rule getInverse(Rule rule){
		Rule inverseRule = RuleUtil.copyRule(rule);
		inverseRule.setName("Inverse_"+rule.getName());
		RuleUtil.setLhsCoordinatesAndLayout(inverseRule);
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
				//				for (Edge e : nodeR.getAllEdges()){
//					//TEdge te = (TEdge) e;
//					Node node2R = (((TEdge)e).getSource()==nodeR ? ((TEdge)e).getTarget() : ((TEdge)e).getSource());
//					Node node2L = rule.getMappings().getOrigin(node2R);
//					boolean originFound = false;
//					if (node2L!=null){
//						for (Edge edgeL : node2L.getIncoming()){
//							if (edgeL.getSource()==nodeL){
//								((TEdge)e).setMarkerType(RuleUtil.TGG_RULE);
//								originFound=true;
//								break;
//							}
//						}
//						for (Edge edgeL : node2L.getOutgoing()){
//							if (edgeL.getTarget()==nodeL){
//								((TEdge)e).setMarkerType(RuleUtil.TGG_RULE);
//								originFound=true;
//								break;
//							}
//						}
//					}
//					if (!originFound){
//						((TEdge)e).setMarkerType(RuleUtil.NEW);
//					}

		
	
	//NEW 
	/**
	public static List<ConcurrentRule> getConcurrentRules(Rule rule1, Rule rule2){
		LinkedList<ConcurrentRule> concurrentRules = new LinkedList<ConcurrentRule>();
		//0. check whether rule2 has NAC
		if (rule2.getLhs().getFormula()!=null){
			System.out.println("rule2 has NAC --> ignore");
			return concurrentRules;
		}
		//1. create inverse rule inverseRule1 : rhs1 --> lhs1
		setLhsCoordinates(rule2);
		Rule invRule1 = getInverse(rule1);
		
		CoSpanList unions = new CoSpanList(invRule1.getRhs(), rule2.getLhs());
		/**
		//1. Find intersectin nodes between lhsir1 and lhsr2
		//maps nodes from lhs1 to corresponding nodes of lhs2, this map is used later to generate intersecting subgraphs between rhs1 and lhs2
		HashMap<Node, List<Node>> node1_nodes2 = new HashMap<Node, List<Node>>();
		for (Node node1 : invRule1.getLhs().getNodes()){
			for (Node node2 : rule2.getLhs().getNodes()){//for each pair node1, node2 from lhs1 and lhs2 respectively
				if (NodeUtil.expands(node1, node2, false)){// if node1 and node2 are of same type and node1 contains all attributed of node2
					if (node1_nodes2.get(node1)!=null){//if node1 already had a match in lhs2 simply add node2 to the existing list of matches
						node1_nodes2.get(node1).add(node2);
					}else{
						LinkedList<Node> list = new LinkedList<Node>();//else create new list with node2 and add list in map
						list.add(node2);
						node1_nodes2.put(node1, list);
					}
				}
			}
		}
		/
		//2. Find intersecting graphs with previously found intersecting nodes as starting points
		// maps new copies of the rules: rule1, rule2 (which will be merged eventually) to its corresponding intersecting subgraphs between lhs1 and lhs2,
		//which are stored in two lists of nodes respectively 
		//HashMap<Graph[], LinkedList<Node>[]> g2sub = new HashMap<Graph[], LinkedList<Node>[]> ();
		HashMap<Graph[], HashMap<Node, Node>[]> gs_mappings = new HashMap<Graph[], HashMap<Node,Node>[]>();
		//int ruleIndex = 0;
		while (!node1_nodes2.isEmpty()){//while there are still nodes from lhsir1 to be processed in order to find corresponding subgraphs in graph lhs2
			Node node1 = (Node) node1_nodes2.keySet().toArray()[0];//take first available node from rhs1
			List<Node> nodes2 = node1_nodes2.get(node1); //take each of the corresponding nodes from lhs2 as starting point for intersecting subgraphs 
			//temporarily map each new rule pair for nr1 to its intersecting subgraphs
			
			HashMap<Graph[], HashMap<Node, Node>[]> n1gs_mapping = new HashMap<Graph[], HashMap<Node, Node>[]>();
			
			for (Node node2 : nodes2){ //for each node2 calculate its intersecting subgraphs
				HashMap<Node, Node> graph1_g1a = new HashMap<Node, Node>();
				HashMap<Node, Node> g1a_graph1 = new HashMap<Node, Node>();
				Graph g1a = GraphUtil.copyGraph(invRule1.getLhs(), graph1_g1a, g1a_graph1, true); //copy rule1 for merge in order to leave initial rules unchanged
				
				HashMap<Node, Node> graph2_g2a = new HashMap<Node, Node>();
				HashMap<Node, Node> g2a_graph2 = new HashMap<Node, Node>();
				Graph g2a = GraphUtil.copyGraph(rule2.getLhs(), graph2_g2a, g2a_graph2, true);
				
				HashMap<Node, Node> graph1_g1b = new HashMap<Node, Node>();
				HashMap<Node, Node> g1b_graph1 = new HashMap<Node, Node>();
				Graph g1b = GraphUtil.copyGraph(invRule1.getLhs(), graph1_g1b, g1b_graph1, true); //copy rule1 for merge in order to leave initial rules unchanged
				
				HashMap<Node, Node> graph2_g2b = new HashMap<Node, Node>();
				HashMap<Node, Node> g2b_graph2 = new HashMap<Node, Node>();
				Graph g2b = GraphUtil.copyGraph(rule2.getLhs(), graph2_g2b, g2b_graph2, true);
				
				Graph[] gs = {g1a, g2a, g1b, g2b};
				
				//Rule rule1 = copyRule(r1); //copy rule1 for merge in order to leave initial rules unchanged
				//Rule rule2 = copyRule(r2); //idem
				//rule1.setName(r1.getName()+CON_RULE_DELIM+r2.getName()+"_"+ruleIndex); //construct a combined name for the rule with a new index 
				//rule2.setName(r2.getName()+"_"+ruleIndex); //r2 will be used for merging purposes only so the name is not important is has to be distinguishable only
				
				Node intersecting_n1a = graph1_g1a.get(node1);
				Node intersecting_n2a = graph2_g2a.get(node2);
				Node intersecting_n1b = graph1_g1b.get(node1);
				Node intersecting_n2b = graph2_g2b.get(node2);
				/*
				for (Node tmp_node1r : g1.getNodes()){//find nr1 in new rule and store in node1r
					if (NodeUtil.isCopy(tmp_node1r, nr1)){
						node1r = tmp_node1r;
						break;
					}
				}
				for (Node tmp_node2l : g2.getNodes()){//find nl2 in new rule and store in node2l
					if (NodeUtil.isCopy(tmp_node2l, nl2)){
						node2l = tmp_node2l;
						break;
					}
				}*/
				/**
				HashMap<Node, Node> intersection_a = new HashMap<Node, Node>();
				HashMap<Node, Node> intersection_b = new HashMap<Node, Node>();
				//HashMap<Node, Node> nsr1_nsl2_map = new HashMap<Node, Node>(); //mapping of intersecting subgraphs will be stored in hashmap 
				getIntersection(intersecting_n1a, intersecting_n2a, intersection_a); //recursively fills the hashmap with node mappings of intersection starting with node1r and node2r
				getIntersection(intersecting_n1b, intersecting_n2b, intersection_b); //recursively fills the hashmap with node mappings of intersection starting with node1r and node2r
				//addSubGraphMapping(g1, g2, node1r, nsr1_nsl2_map, tmp_g2sub); //adds new pair of rules and corresponding subgraph from hashmap to temporary hashmap rules 2 intersecting s.g.
				HashMap<?, ?>[] mappings = {intersection_a, graph1_g1a, g1a_graph1, graph2_g2a, g2a_graph2, 
											intersection_b, graph1_g1b, g1b_graph1, graph2_g2b, g2b_graph2};
				n1gs_mapping.put(gs, (HashMap<Node, Node>[]) mappings);
				//ruleIndex++;
			}
			gs_mappings.putAll(n1gs_mapping);//update rules map with potential rules corresponding to matches between nr1 and nsl2
			//all subgraphs containing n1 should have been processed and can be removed
			node1_nodes2.remove(node1);
			//removed all mappings from nr1_nsl2_map between nodes that have already been mapped to each other in one of the previously found intersections
			cleanNodeMapping(node1_nodes2, n1gs_mapping);
		}
		
		
		LinkedList<Graph[]> keygs = new LinkedList<Graph[]>();
		keygs.addAll(gs_mappings.keySet());
		HashMap<Graph[], HashMap<Node,Node>[]> gs_mappings2 = new HashMap<Graph[], HashMap<Node,Node>[]> ();
		for (int i = 0; i<= keygs.size()/2; i++){
			HashMap<Node, Node>[] map = gs_mappings.remove(keygs.get(i));
			gs_mappings2.put(keygs.get(i), map);
		}
		generateAllIntersections(invRule1.getLhs(), rule2.getLhs(), gs_mappings, gs_mappings2);**/
		
		//HashMap<Graph[], HashMap<Node, Node>[]> rule2mappings = new HashMap<Graph[], HashMap<Node, Node>[]>();
		/**
		int ruleIndex = 0;
		for (Graph[] gs: gs_mappings.keySet()){
			Graph g1a = gs[0];
			Graph g2a = gs[1];
			Graph g1b = gs[2];
			Graph g2b = gs[3];
			HashMap<Node,Node>[] mappings = gs_mappings.get(gs);
			HashMap<Node, Node> intersection_a= mappings[0];
			HashMap<Node, Node> intersection_b= mappings[5];
			mergeGraphs(g1a, g2a, intersection_a);
			
			RuleApplication invRule1Appl = applyRule(invRule1, g1a);
			if (invRule1Appl!=null){
				mergeGraphs(g1b, g2b, intersection_b);
				RuleApplication rule2Appl = applyRule(rule2, g1b);
				if (rule2Appl==null){
					throw new IllegalArgumentException("Problem");
				};
				
				Match matchInvRule1 = invRule1Appl.getResultMatch();
				TggHenshinEGraph henshinGInvRule1 = (TggHenshinEGraph)(invRule1Appl.getEGraph());
				Match matchRule2 = rule2Appl.getResultMatch();
				TggHenshinEGraph henshinGRule2 = (TggHenshinEGraph)(rule2Appl.getEGraph());
				
				RuleImpl concurrentRule = new RuleImpl();
				concurrentRule.setName("Concurrent_"+rule1.getName()+RuleUtil.CON_RULE_DELIM+rule2.getName()+"_"+ruleIndex);
				ruleIndex++;
				concurrentRule.setLhs(g1a);
				concurrentRule.setRhs(g1b);
				
				
				for (Node node2 : rule2.getRhs().getNodes()){//for each node of the Rhs of invRule1 we have to get the corresponding node of the rule applicaiton result to map it
					EObject n2r = matchRule2.getNodeTarget(node2); //get resulting nodeObject of rule applicatoin
					TNode concurrentRuleNodeR = (TNode)henshinGRule2.getObject2NodeMap().get(n2r); //get resulting nodeObject
					concurrentRuleNodeR.setX(((TNode)node2).getX());
					concurrentRuleNodeR.setY(((TNode)node2).getY());
				}
				
				HashSet<Node> processedConcurrentRuleNodesLhs = new HashSet<Node>();
				for (Node node1 : invRule1.getRhs().getNodes()){//for each node of the Rhs of invRule1 we have to get the corresponding node of the rule applicaiton result to map it
					EObject n1r = matchInvRule1.getNodeTarget(node1); //get resulting nodeObject of rule applicatoin
					TNode concurrentRuleNodeL = (TNode)henshinGInvRule1.getObject2NodeMap().get(n1r); //get resulting nodeObject
					concurrentRuleNodeL.setX(((TNode)node1).getX());
					concurrentRuleNodeL.setY(((TNode)node1).getY());
					Node node1Lhs = invRule1.getMappings().getOrigin(node1);//corresponding Lhs node of invRule1
					HashMap<Node, Node> graph1_g1b =  mappings[6]; //g1b_2_copy
					Node n1b = graph1_g1b.get(node1Lhs); //node1 copy
					Node n2b = intersection_b.get(n1b);
					
					TNode concurrentRuleNodeR;
					
					if (n2b!=null){//if node1copy is part of intersection 
						HashMap<Node, Node> g2b_graph2 = mappings[9];
						Node node2l = g2b_graph2.get(n2b);
						Node node2r = rule2.getMappings().getImage(node2l, rule2.getRhs());
						EObject n2r = matchRule2.getNodeTarget(node2r);
						concurrentRuleNodeR = (TNode)henshinGRule2.getObject2NodeMap().get(n2r);
						concurrentRuleNodeR.setX(((TNode)node2r).getX());
						concurrentRuleNodeR.setY(((TNode)node2r).getY());
						System.out.println("XR "+((TNode)node2r).getX());
						System.out.println("YR "+((TNode)node2r).getY());
						
					}else{//if node1copy is not part of intersection 
						concurrentRuleNodeR = (TNode)n1b;
					}
					
					Mapping concurrentRuleMapping = new MappingImpl();
					concurrentRuleMapping.setOrigin(concurrentRuleNodeL);
					concurrentRuleMapping.setImage(concurrentRuleNodeR);
					concurrentRule.getAllMappings().add(concurrentRuleMapping);
					
					processedConcurrentRuleNodesLhs.add(concurrentRuleNodeL);
						//TODO attribute mapping?
						/*
						for (Attribute al : ruleNodeL.getAttributes()){
							boolean found = false;
							for (Attribute ar : ruleNodeR.getAttributes()){
								if (AttributeUtil.equivalent(al, ar)){
									Mapping m = new MappingImpl();
									m.setOrigin(al);
									m.setImage(ar);
									rule.getAllMappings().add()
								}
							}
							if (!found) throw new IllegalArgumentException("Attribute not found");
						}*//**
				}
				
				for (Node n2a : concurrentRule.getLhs().getNodes()){
					if (!processedConcurrentRuleNodesLhs.contains(n2a)){
						Node concurrentRuleNodeL = n2a;
						HashMap<Node, Node> g2a_graph2 = mappings[4];
						Node node2l = g2a_graph2.get(n2a);
						Node node2r = rule2.getAllMappings().getImage(node2l, rule2.getRhs());
						EObject n2r = matchRule2.getNodeTarget(node2r);
						Node concurrentRuleNodeR = henshinGRule2.getObject2NodeMap().get(n2r);
						Mapping concurrentRuleMapping = new MappingImpl();
						concurrentRuleMapping.setOrigin(concurrentRuleNodeL);
						concurrentRuleMapping.setImage(concurrentRuleNodeR);
						concurrentRule.getAllMappings().add(concurrentRuleMapping);
						
						processedConcurrentRuleNodesLhs.add(concurrentRuleNodeL);
					}
				}
				
				concurrentRules.add(concurrentRule);
				//match1.getNodeTarget(node);
				//henshinGraph.getObject2NodeMap().get(arg0)
			}
		}
		
		
		for (Rule cr : concurrentRules){
			removeDoubleEdges(cr.getLhs());
			removeDoubleEdges(cr.getRhs());
			
		}
		return concurrentRules;**/
		/**
		//System.out.println("intersections generated");
		Graph graph = null;
		Rule rule = null;
		
		final TggHenshinEGraph henshinGraph = new TggHenshinEGraph(graph);
		TGGEngineImpl emfEngine = new TGGEngineImpl(henshinGraph);
		RuleApplicationImpl ruleApplication = new RuleApplicationImpl(emfEngine);
		
		ruleApplication.setRule(rule);
		ruleApplication.setEGraph(henshinGraph);
		try {
			Iterator<Match> matchesIterator = emfEngine
					.findMatches(rule, henshinGraph,
							new MatchImpl(rule)).iterator();
			if (!matchesIterator.hasNext())
				System.out.println("Problem, match should be found!");
			while (matchesIterator.hasNext()) {
				ruleApplication.setPartialMatch(matchesIterator
						.next());
				ruleApplication.execute(null);
				Match m = ruleApplication.getResultMatch();
				//m.getNodeTarget(node)
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorDialog.openError(
					Display.getDefault().getActiveShell(),
					"Execute Failure",
					"The rule [" + ruleApplication.getRule().getName()
							+ "] couldn't be applied.",
					new Status(IStatus.ERROR, MuvitorActivator.PLUGIN_ID, ex
							.getMessage(), ex.getCause()));
		}
		
		
		//3. For each intersecting subgraphs between rhs1 and lhs2 merge two rule copies together in order to create new rule
		for (Rule[] rules : grs2mappings.keySet()){
			Rule rule1 = rules[0];//new rule 
			Rule rule2 = rules[1];
			LinkedList<Node>[] sub_nodes =  grs2mappings.get(rules);
			LinkedList<Node> sub_nodes1r = sub_nodes[0];
			LinkedList<Node> sub_nodes2l = sub_nodes[1];
			
			//3.1 find intersecting nodes and add attributes if missing in left and right hand side
			int index =-1;
			for (Node node1l : rule1.getLhs().getNodes()){ //iterate over lefthandside nodes of rule 1
				Node node1r = rule1.getAllMappings().getImage(node1l, rule1.getRhs()); //get corresonding image node in rhs of rule 1 
				if (node1r==null) throw new IllegalArgumentException("no deleting rules allowed");; 
				index = sub_nodes1r.indexOf(node1r);
				if (index < 0) continue; // if node1r not part of intersection go to next node
				Node node2l = sub_nodes2l.get(index);
				for (Attribute att2l : node2l.getAttributes()){//for each attribute in node2l
					boolean found = false;
					for (Attribute att1l : node1l.getAttributes()){//check whether it is present in corresponding node1l
						if (AttributeUtil.equivalent(att1l, att2l)){
							found = true;
							break;
						}
					}
					if (!found){ //if attribute not in node1l copy attribute from node2l
						Attribute n_att1l = AttributeUtil.copyAttribute(att2l);
						n_att1l.setNode(node1l);
						node1l.getAttributes().add(n_att1l);
						//copy image of attribute onto node1r
						Attribute n_att1r = AttributeUtil.copyAttribute(rule2.getAllMappings().getImage(att2l, rule2.getRhs()));
						n_att1r.setNode(node1r);
						node1r.getAttributes().add(n_att1r);
						rule1.getAllMappings().add(n_att1l, n_att1r);
					}
				}
				Node node2r = rule2.getAllMappings().getImage(node2l, rule2.getRhs());
				for (Attribute att2r : node2r.getAttributes()){
					boolean found = false;
					for (Attribute att1r : node1r.getAttributes()){
						if (AttributeUtil.equivalent(att1r, att2r)){
							found = true;
							break;
						}
					}
					if (!found){
						Attribute n_att1r = AttributeUtil.copyAttribute(att2r);
						n_att1r.setNode(node1r);
						node1r.getAttributes().add(n_att1r);
					}
				}
			}
			
			//3.2 glue the lhs2 onto the lhs1
			
			Iterator<Node> iterNodes2l = rule2.getLhs().getNodes().iterator();
			while (iterNodes2l.hasNext()){
				Node node2l = iterNodes2l.next();
				iterNodes2l.remove();
				Node node2r = rule2.getAllMappings().getImage(node2l, rule2.getRhs());
				node2l.setGraph(rule1.getLhs());
				node2r.setGraph(rule1.getRhs());
				//nodesls.add(nl2);
				if (!rule1.getLhs().getNodes().contains(node2l)){
					System.out.println("setGraph does not add node to graph");
					rule1.getLhs().getNodes().add(node2l);
				}else System.out.println("setGraph does add node to graph");
				
				if (!rule1.getRhs().getNodes().contains(node2r)){
					System.out.println("setGraph does not add node to graph");
					rule1.getRhs().getNodes().add(node2r);
				}else System.out.println("setGraph does add node to graph");
				
				rule1.getAllMappings().add(node2l, node2r);
			}
			
			Iterator<Edge> iterEdges2l = rule2.getLhs().getEdges().iterator();
			while (iterEdges2l.hasNext()){
				Edge edge2l = iterEdges2l.next();
				iterEdges2l.remove();
				Edge edge2r = rule2.getAllMappings().getImage(edge2l, rule2.getRhs());
				edge2l.setGraph(rule1.getLhs());
				edge2r.setGraph(rule1.getRhs());
				
				if (!rule1.getLhs().getEdges().contains(edge2l)){
					System.out.println("setGraph does not add edge to graph");
					rule1.getLhs().getEdges().add(edge2l);
				}else System.out.println("setGraph does add edge to graph");
				
				if (!rule1.getRhs().getEdges().contains(edge2r)){
					System.out.println("setGraph does not add edge to graph");
					rule1.getRhs().getEdges().add(edge2r);
				}else System.out.println("setGraph does add edge to graph");
				
				rule1.getAllMappings().add(edge2l, edge2r);
			}
			
			
			for (NestedCondition nc : rule2.getLhs().getNestedConditions()){
				//nc.setConclusion(rule1.getLhs());
				NestedCondition nnc = null;
				if (nc.isNAC()) {
					nnc = rule1.getLhs().createNAC(nc.toString());
				}else{
					nnc = rule1.getLhs().createPAC(nc.toString());
				}
				copyNestedCondition(nc, nnc);
			}
			
			// glue the rhs2 onto the rhs1
			Iterator<Edge> iterEdge2r = rule2.getRhs().getEdges().iterator();
			while (iterEdge2r.hasNext()){
				Edge edge2r = iterEdge2r.next();
				iterEdge2r.remove();
				if (edge2r.getGraph()!=rule1.getRhs()){
					edge2r.setGraph(rule1.getRhs());
				}else{
					System.out.println("edge2r graph has already been set");
				}
				if (!rule1.getRhs().getEdges().contains(edge2r)){
					rule1.getRhs().getEdges().add(edge2r);
				}else{
					System.out.println("Edge already present");
				}
			}
			
			Iterator<Node> iterNode2r = rule2.getRhs().getNodes().iterator();
			while (iterNode2r.hasNext()){
				Node node2r = iterNode2r.next();
				iterNode2r.remove();
				if (node2r.getGraph()!=rule1.getRhs()){
					node2r.setGraph(rule1.getRhs());
				}else{
					System.out.println("Node graph has already been set" );
				}
				if (!rule1.getRhs().getNodes().contains(node2r)){
					rule1.getRhs().getNodes().add(node2r);
				}else System.out.println("Node already present");
			}
			
			for (NestedCondition nc : rule2.getRhs().getNestedConditions()){
				//nc.setConclusion(rule1.getRhs());
				NestedCondition nnc = null;
				if (nc.isNAC()) {
					nnc = rule1.getRhs().createNAC(nc.toString());
				}else{
					nnc = rule1.getRhs().createPAC(nc.toString());
				}
				copyNestedCondition(nc, nnc);
			}
			
			//TODO update mappings?
			//replace intersecting nodes
			Iterator<Node> iterNodes1l = rule1.getLhs().getNodes().iterator();
			HashSet<Node> mergedinodes2l = new HashSet<Node>();
			HashSet<Node> mergedinodes2r = new HashSet<Node>();
			HashSet<Node> mergedinodes1r = new HashSet<Node>();
			while (iterNodes1l.hasNext()){
				Node node1l = iterNodes1l.next();
				iterNodes1l.remove();
				Node node1r = rule1.getAllMappings().getImage(node1l, rule1.getRhs());
				//in case node is part of intersection get corresponding lhs node 2
				if (sub_nodes1r.contains(node1r)){
					mergedinodes1r.add(node1r);
					int ind = sub_nodes1r.indexOf(node1r);
					Node node2l = sub_nodes2l.get(ind);
					Node node2r = rule1.getAllMappings().getImage(node2l, rule1.getRhs());
					
					Iterator<Edge> iterNodeIncom2l= node2l.getIncoming().iterator();
					while (iterNodeIncom2l.hasNext()){
						Edge incomingEdge2l = iterNodeIncom2l.next();
						iterNodeIncom2l.remove();
						incomingEdge2l.setTarget(node1l);
						node1l.getIncoming().add(incomingEdge2l);
					}
					Iterator<Edge> iterNodeOutgo2l= node2l.getOutgoing().iterator();
					while (iterNodeOutgo2l.hasNext()){
						Edge outgoEdge2l = iterNodeOutgo2l.next();
						iterNodeOutgo2l.remove();
						outgoEdge2l.setSource(node1l);
						node1l.getOutgoing().add(outgoEdge2l);
					}
					
					Iterator<Edge> iterNodeIncom2r= node2r.getIncoming().iterator();
					while (iterNodeIncom2r.hasNext()){
						Edge incomingEdge2r = iterNodeIncom2r.next();
						iterNodeIncom2r.remove();
						incomingEdge2r.setTarget(node1r);
						node1r.getIncoming().add(incomingEdge2r);
					}
					Iterator<Edge> iterNodeOutgo2r= node2r.getOutgoing().iterator();
					while (iterNodeOutgo2r.hasNext()){
						Edge outgoEdge2r = iterNodeOutgo2r.next();
						iterNodeOutgo2r.remove();
						outgoEdge2r.setSource(node1r);
						node1r.getOutgoing().add(outgoEdge2r);
					}
					
					boolean removed = rule1.getAllMappings().remove(node2l);
					if (!removed) System.out.println("node2l has not been removed from rule1 mappings");
					mergedinodes2l.add(node2l);
					mergedinodes2r.add(node2r);
				}
			}
			boolean reml = rule1.getLhs().getNodes().removeAll(mergedinodes2l);
			boolean remr = rule1.getRhs().getNodes().removeAll(mergedinodes2r);
			if (!reml) System.out.println("no l nodes removed");
			if (!remr) System.out.println("no r nodes removed");
			
			Iterator<Node> iterNodes1r = rule1.getRhs().getNodes().iterator();
			while (iterNodes1r.hasNext()){
				Node node1r = iterNodes1r.next();
				iterNodes1r.remove();
				if (!mergedinodes1r.contains(node1r) && sub_nodes1r.contains(node1r)){
					//TODO
					
				}
			}
			
			//rule1.getAllMappings().addAll(rule2.getAllMappings());
			result.add(rule1);
		}
		
		//return result;
	}
	
**/
	
/**	private static void mergeGraphs(Graph graph1, Graph graph2, HashMap<Node, Node> intersection){
		//Iterator<Node> g2NodeIterator = graph2.getNodes().iterator();
		//while (g2NodeIterator.hasNext()){//(Node node2 : graph2.getNodes()){
		//int s2 = graph2.getNodes().size();
		//for (int i=0; i<s2; i++){
		while(graph2.getNodes().size()!=0){
			Node node2 = graph2.getNodes().get(0);
			//Node node2 = g2NodeIterator.next();
			//g2NodeIterator.remove();
			node2.setGraph(graph1);
			/*
			if (!graph1.getNodes().add(node2)){
				System.out.println("remove line 1026");
			}else{
				System.out.println("dont remove line 1026!");
			}*/
/**
		}
		//Iterator<Edge> g2EdgeIterator = graph2.getEdges().iterator();
		//for (Edge edge2 : graph2.getEdges()){
		//while (g2EdgeIterator.hasNext()){
		//int es2 = graph2.getEdges().size();
		//for (int i=0; i<es2; i++){
		while(graph2.getEdges().size()!=0){
			Edge edge2 = graph2.getEdges().get(0);
			//Edge edge2 = g2EdgeIterator.next();
			//g2EdgeIterator.remove();
			edge2.setGraph(graph1);
			/*
			if (graph1.getEdges().add(edge2)){
				System.out.println("Great edge is added");
			}else{
				System.out.println("ok edge is already added");
			};*//**
		}
		//Iterator<Node> interNode1Iterator = intersection.keySet().iterator();
		LinkedList<Node> inters = new LinkedList<Node>(intersection.keySet());
		int s = inters.size();
		for (int i=0; i<s; i++){
			//Edge edge2 = graph2.getEdges().get(i);
			Node node1 = inters.get(i);
		//while (interNode1Iterator.hasNext()){//(Node node2 : graph2.getNodes()){
		//	Node node1 = interNode1Iterator.next();
			
		//for (Node node1 : intersection.keySet()){
			Node node2 = intersection.get(node1);
			//interNode1Iterator.remove();
			//Iterator<Edge> incomingEdge2Iterator = node2.getIncoming().iterator();
			//for (Edge edge2 : graph2.getEdges()){
			int ics = node2.getIncoming().size();
			//for (int j=0; j<ics; j++){
			while(node2.getIncoming().size()!=0){
			//	Edge edge2 = graph2.getEdges().get(i);
			//while (incomingEdge2Iterator.hasNext()){
				//Edge ie2 = incomingEdge2Iterator.next();
				Edge ie2 = node2.getIncoming().get(0);
				//incomingEdge2Iterator.remove();
				ie2.setTarget(node1);
			}
			//Iterator<Edge> outgoingEdge2Iterator = node2.getIncoming().iterator();
			//for (Edge edge2 : graph2.getEdges()){
			//while (outgoingEdge2Iterator.hasNext()){
			int ogs = node2.getOutgoing().size();
			//for (int k=0;k<ogs;k++){
			while(node2.getOutgoing().size()!=0){
				Edge oe2 = node2.getOutgoing().get(0);
				//Edge oe2 = outgoingEdge2Iterator.next();
				//outgoingEdge2Iterator.remove();
				oe2.setSource(node1);
				/*
				if (!node1.getOutgoing().add(oe2)){
					System.out.println("outgoing edges updated with setSource");
				}else System.out.println("remove this output");;*//**
			}
			graph1.removeNode(node2);
		}
	}
	
	
	
	
	private static void generateAllIntersections(Graph g1, Graph g2, HashMap<Graph[], HashMap<Node,Node>[]> oldgraphs2mappings, HashMap<Graph[], HashMap<Node, Node>[]> newgraphs2mappings){
		HashMap<Graph[], HashMap<Node, Node>[]> res_new_graphs2mappings = new HashMap<Graph[], HashMap<Node,Node>[]>();
		for (Graph[] graphs : oldgraphs2mappings.keySet()){
			HashMap<Node,Node>[] old_mappings = oldgraphs2mappings.get(graphs);
			HashMap<Node, Node> old_inters = old_mappings[0];
			//LinkedList<Node> old_sub_nodes2l = old_sub_nodes[1];
			for (Graph[] newgraphs : newgraphs2mappings.keySet()){
				HashMap<Node, Node>[] new_mappings = newgraphs2mappings.get(newgraphs);
				if (!intersects(old_mappings, new_mappings)){
					HashMap<Node, Node> new_inters = new_mappings[0];
					
					//LinkedList<Node> new_sub_nodes2l = new_mappings[1];
					//LinkedList<Node> resulting_sub_nodes1 = new LinkedList<Node>();
					//LinkedList<Node> resulting_sub_nodes2 = new LinkedList<Node>();
					//Rule resulting_rule1 = RuleUtil.copyRule(newgraphs[0]);
					//Rule resulting_rule2 = RuleUtil.copyRule(newgraphs[1]);
					//Rule[] resulting_rls = {resulting_rule1, resulting_rule2};
					
					HashMap<Node, Node> res_g1a_2_copy = new HashMap<Node, Node>();
					HashMap<Node, Node> res_copy_2_g1a = new HashMap<Node, Node>();
					Graph res_graph1a = GraphUtil.copyGraph(g1, res_g1a_2_copy, res_copy_2_g1a, true);
					
					HashMap<Node, Node> res_g1b_2_copy = new HashMap<Node, Node>();
					HashMap<Node, Node> res_copy_2_g1b = new HashMap<Node, Node>();
					Graph res_graph1b = GraphUtil.copyGraph(g1, res_g1b_2_copy, res_copy_2_g1b, true);
					
					HashMap<Node, Node> res_g2a_2_copy = new HashMap<Node, Node>();
					HashMap<Node, Node> res_copy_2_g2a = new HashMap<Node, Node>();
					Graph res_graph2a = GraphUtil.copyGraph(g2, res_g2a_2_copy, res_copy_2_g2a, true);
					
					HashMap<Node, Node> res_g2b_2_copy = new HashMap<Node, Node>();
					HashMap<Node, Node> res_copy_2_g2b = new HashMap<Node, Node>();
					Graph res_graph2b = GraphUtil.copyGraph(g2, res_g2b_2_copy, res_copy_2_g2b, true);
					
					Graph[] res_graphs = {res_graph1a, res_graph2a, res_graph1b, res_graph2b};
					
					HashMap<Node, Node> res_inters_a= new HashMap<Node, Node>();
					HashMap<Node, Node> res_inters_b= new HashMap<Node, Node>();
					
					for (Node old_node1 : old_inters.keySet()){
						Node old_node2 = old_inters.get(old_node1);
						//Node n2 = old_sub_nodes2l.get(i);
						Node resulting_node1a = null;
						for (Node r_node1a : res_graph1a.getNodes()){
							if (NodeUtil.isCopy(r_node1a, old_node1)){
								resulting_node1a = r_node1a;
								break;
							}
						}
						Node resulting_node2a = null;
						for (Node r_node2a : res_graph2a.getNodes()){
							if (NodeUtil.isCopy(r_node2a, old_node2)){
								resulting_node2a = r_node2a;
								break;
							}
						}
						Node resulting_node1b = null;
						for (Node r_node1b : res_graph1b.getNodes()){
							if (NodeUtil.isCopy(r_node1b, old_node1)){
								resulting_node1b = r_node1b;
								break;
							}
						}
						Node resulting_node2b = null;
						for (Node r_node2b : res_graph2b.getNodes()){
							if (NodeUtil.isCopy(r_node2b, old_node2)){
								resulting_node2b = r_node2b;
								break;
							}
						}
						res_inters_a.put(resulting_node1a, resulting_node2a);
						res_inters_b.put(resulting_node1b, resulting_node2b);
					}
					
					for (Node new_node1 : new_inters.keySet()){
						Node new_node2 = new_inters.get(new_node1);
						//Node n2 = old_sub_nodes2l.get(i);
						Node resulting_node1a = null;
						for (Node r_node1a : res_graph1a.getNodes()){
							if (NodeUtil.isCopy(r_node1a, new_node1)){
								resulting_node1a = r_node1a;
								break;
							}
						}
						Node resulting_node2a = null;
						for (Node r_node2a : res_graph2a.getNodes()){
							if (NodeUtil.isCopy(r_node2a, new_node2)){
								resulting_node2a = r_node2a;
							}
						}
						
						Node resulting_node1b = null;
						for (Node r_node1b : res_graph1b.getNodes()){
							if (NodeUtil.isCopy(r_node1b, new_node1)){
								resulting_node1b = r_node1b;
								break;
							}
						}
						Node resulting_node2b = null;
						for (Node r_node2b : res_graph2b.getNodes()){
							if (NodeUtil.isCopy(r_node2b, new_node2)){
								resulting_node2b = r_node2b;
							}
						}
						res_inters_a.put(resulting_node1a, resulting_node2a);
						res_inters_b.put(resulting_node1b, resulting_node2b);
					}
					
					HashMap<?,?>[] resulting_mappings = {res_inters_a, res_g1a_2_copy, res_copy_2_g1a, res_g2a_2_copy, res_copy_2_g2a,
														 res_inters_b, res_g1b_2_copy, res_copy_2_g1b, res_g2b_2_copy, res_copy_2_g2b};
					res_new_graphs2mappings.put(res_graphs, (HashMap<Node,Node>[]) resulting_mappings);
				}
			}
		}
		oldgraphs2mappings.putAll(newgraphs2mappings);
		if (!res_new_graphs2mappings.isEmpty()) generateAllIntersections(g1, g2, oldgraphs2mappings, res_new_graphs2mappings);
	}
	
	
	private static boolean intersects(HashMap<Node, Node>[] mappings1, HashMap<Node, Node>[] mappings2){
		if (mappings1.length<1) throw new IllegalArgumentException("mappings has to be nonempty");
		if (mappings2.length<1) throw new IllegalArgumentException("mappings has to be nonempty");
		HashMap<Node,Node> l11 = mappings1[0];
		HashMap<Node, Node> l21 = mappings2[0];
		for (Node n1 : l11.keySet()){
			for(Node n2 : l21.keySet()){
				if (NodeUtil.isCopy(n1, n2)) return true;
			}
		}
		
		for (Node n1 : l11.values()){
			for(Node n2 : l21.values()){
				if (NodeUtil.isCopy(n1, n2)) return true;
			}
		}
		return false;
	}
	
	private static void cleanNodeMapping(HashMap<Node, List<Node>> n1_2_ns2, HashMap<Graph[], HashMap<Node, Node>[]> grs2mappings){
		if (n1_2_ns2 == null || grs2mappings==null )throw new IllegalArgumentException("null is not a valid argument");
		for (Graph[] graphs : grs2mappings.keySet()){//for each newly created rule pair
			HashMap<Node,Node>[] mappings = grs2mappings.get(graphs);//get corresponding intersection
			HashMap<Node, Node> intersection = mappings[0];//get rhs subgraph of intersection
			//HashMap<Node, Node> sub_nodes2l = mappings[1];//get lhs subgraph of intersection
			for (Node node1 : intersection.keySet()){//iterate over node mappings
				Node node2 = intersection.get(node1);
				Node n1 = null;//store copy of parameter from initial rule r1
				List<Node> ns2 = null; //store copy of potential images from inital rule r2
				for (Node tmp_n1 : n1_2_ns2.keySet()){
					if (NodeUtil.isCopy(tmp_n1, node1)){
						n1 = tmp_n1;
						ns2 = n1_2_ns2.get(tmp_n1);
						break;
					}
				}
				
				if (ns2!=null){ //if potential images have been found
					Node n2 = null; //store copy of image corresponding to sub_node2l from r2
					for (Node tmp_n2 : ns2){
						if (NodeUtil.isCopy(tmp_n2, node2)){
							n2 = tmp_n2;
							break;
						}
					}
					if (ns2.remove(n2) && ns2.isEmpty()){//remove nl2 from mapping and remove nr1 if no other mapping is left
						n1_2_ns2.remove(n1);
					}
				}
			}
		}
	}
	
	private static void addSubGraphMapping(Rule rule1, Rule rule2, Node node1, HashMap<Node, Node> map, HashMap<Rule[], LinkedList<Node>[]> r2isg){
		if (node1==null || map == null || r2isg == null) throw new IllegalArgumentException("null is not a valid argument");
		Node node2 = map.remove(node1);
		if (node2 == null) throw new IllegalArgumentException("map should contain argument node");
		LinkedList<Node> l1 = new LinkedList<Node>();
		LinkedList<Node> l2 = new LinkedList<Node>();
		l1.add(node1);
		l2.add(node2);
		for (Node n : map.keySet()){
			l1.add(n);
			l2.add(map.get(n));
		}
		Rule[] key = {rule1, rule2};
		LinkedList<?>[] image = (LinkedList<?>[]) new LinkedList<?>[2];// = {l1, l2};
		image[0] = l1;
		image[1] = l2;
		r2isg.put(key, (LinkedList<Node>[]) image);
	}
	
	
	private static Node[] getValidCorrespondence(Edge edge1, TNode corrNode1, Edge edge2, TNode corrNode2){
		if (!NodeUtil.guessTC(corrNode1).equals(TripleComponent.CORRESPONDENCE)){
			throw new IllegalArgumentException("correspondencenode should be of type correspondence");
		}
		if (!NodeUtil.guessTC(corrNode2).equals(TripleComponent.CORRESPONDENCE)){
			throw new IllegalArgumentException("correspondencenode should be of type correspondence");
		}
		if (corrNode1.getOutgoing().size()>2 || corrNode1.getIncoming().size()>2){
			throw new IllegalArgumentException("correspondencenode should have exactly one in and one out going edge");
		}
		if (corrNode2.getOutgoing().size()>2 || corrNode2.getIncoming().size()>2){
			throw new IllegalArgumentException("correspondencenode should have exactly one in and one out going edge");
		}
		Node node1 = null;
		Node node2 = null;
		
		boolean outgoing = false;
		Edge oie1 = null;
		for (Edge e1 : corrNode1.getOutgoing()){
			if (e1 == edge1){
				outgoing = true;
			}else{
				oie1 = e1;
			}
		}
		if (outgoing){
			Edge oe2 = null;
			for (Edge e2 : corrNode2.getOutgoing()){
				if (e2 == edge2){
					outgoing = false;
				}else{
					oe2 = e2;
				}
			}
			if (outgoing) throw new IllegalArgumentException("Wrong arguments");
			node1 = oie1.getTarget();
			node2 = oe2.getTarget();
			if (!NodeUtil.expands(node1, node2, false)){
				return null;
			}
		}else{
			for (Edge e1 : corrNode1.getIncoming()){
				if (e1 == edge1){
					outgoing = true;
				}else{
					oie1 = e1;
				}
			}
			if (!outgoing) throw new IllegalArgumentException("Wrong arguments");
			Edge ie2 = null;
			for (Edge e2 : corrNode2.getIncoming()){
				if (e2 == edge2){
					outgoing = false;
				}else{
					ie2 = e2;
				}
			}
			if (outgoing) throw new IllegalArgumentException("Wrong arguments");
			node1 = oie1.getSource();
			node2 = ie2.getSource();
			if (!NodeUtil.expands(node1, node2, false)){
				return null;
			}
		}
		Node[] result = {node1, node2};
		return result;
	}
	//frecursive function that checks whether corresponding nodes are valid within context of map and if yes extends map with new mapping from startNode1 to startNode2  
	private static boolean getIntersection(Node node1, Node node2, HashMap<Node, Node> map) throws IllegalArgumentException{
		if (node1 == null || node2 == null || map == null){
			throw new IllegalArgumentException("null is not a valid argument: node1: "+node1+" node2: "+node2+" map: "+map);
		}
		if (!NodeUtil.expands(node1, node2, true)){
			throw new IllegalArgumentException("types of starting nodes should be equal");
		}
		
		//check whether 
		for (Edge e1 : node1.getOutgoing()){
			for (Edge e2 : node2.getOutgoing()){ //for each pair e1 and e2 of outgoing edges from n1 and n2
				Node t1 = e1.getTarget();
				Node t2 = e2.getTarget();
				if (NodeUtil.expands(t1, t2, true) ){//&& e1.getType().equals(e2.getType())){
					if (map.containsKey(t1) && map.containsValue(t2)){
						if (!map.get(t1).equals(t2)){
							return false;
						}
					}else if (map.containsKey(t1) || map.containsValue(t2)){
							return false;
					}
				}
			}
		}
		
		for (Edge e1 : node1.getIncoming()){
			for (Edge e2 : node2.getIncoming()){ //for each pair e1 and e2 of outgoing edges from n1 and n2
				Node s1 = e1.getSource();
				Node s2 = e2.getSource();
				if (NodeUtil.expands(s1, s2, true)){ //&& e1.getType().equals(e2.getType())){
					if (map.containsKey(s1) && map.containsValue(s2)){
						if (!map.get(s1).equals(s2)){
							return false;
						}
					}else if (map.containsKey(s1) || map.containsValue(s2)){
							return false;
					}
				}
			}
		}
		
		Node corrN1 = null;
		Edge edge1 = null;
		for (Edge edge : node1.getOutgoing()){
			Node corr = edge.getTarget();
			if (NodeUtil.guessTC((TNode)corr).equals(TripleComponent.CORRESPONDENCE)){
				corrN1 = corr;
				edge1 = edge;
			}
		}
		
		Node corrN2 = null;
		Edge edge2 = null;
		for (Edge edge : node2.getOutgoing()){
			Node corr = edge.getTarget();
			if (NodeUtil.guessTC((TNode)corr).equals(TripleComponent.CORRESPONDENCE)){
				corrN2 = corr;
				edge2 = edge;
			}
		}
		
		if (corrN2==null || corrN1==null){
			for (Edge edge : node1.getIncoming()){
				Node corr = edge.getSource();
				if (NodeUtil.guessTC((TNode)corr).equals(TripleComponent.CORRESPONDENCE)){
					corrN1 = corr;
					edge1 = edge;
				}
			}
			for (Edge edge : node2.getIncoming()){
				Node corr = edge.getSource();
				if (NodeUtil.guessTC((TNode)corr).equals(TripleComponent.CORRESPONDENCE)){
					corrN2 = corr;
					edge2 = edge;
				}
			}
		}
		Node[] correspondence = {null, null};
		if (corrN1!=null && edge1!=null && corrN2!=null && edge2!=null){
			correspondence = getValidCorrespondence(edge1, (TNode)corrN1, edge2, (TNode)corrN2);
			if (correspondence==null){
				return false;
			}
			map.put(corrN1, corrN2);
			map.put(correspondence[0], correspondence[1]);
		}
		
		map.put(node1, node2);
		
		int i=0;
		while(node1!=null && node2!=null && i<2){
			i++;
			for (Edge e1 : node1.getOutgoing()){
				for (Edge e2 : node2.getOutgoing()){ //for each pair e1 and e2 of outgoing edges from n1 and n2
					Node t1 = e1.getTarget();
					Node t2 = e2.getTarget();
					//only allows non correspondence nodes
					if (NodeUtil.expands(t1, t2, false)){// && e1.getType().equals(e2.getType())){
						if (map.containsKey(t1) && map.containsValue(t2)){
							if (!map.get(t1).equals(t2)){
								throw new IllegalArgumentException("Algorithm is not correct ;)");
							}
						}else if (map.containsKey(t1) || map.containsValue(t2)){
								throw new IllegalArgumentException("Algorithm is not correct ;)");
						}else{
							getIntersection(t1, t2, map);
						}
					}
				}
			}
			
			for (Edge e1 : node1.getIncoming()){
				for (Edge e2 : node2.getIncoming()){ //for each pair e1 and e2 of outgoing edges from n1 and n2
					Node s1 = e1.getSource();
					Node s2 = e2.getSource();
					if (NodeUtil.expands(s1, s2, false)){// && e1.getType().equals(e2.getType())){
						if (map.containsKey(s1) && map.containsValue(s2)){
							if (!map.get(s1).equals(s2)){
								throw new IllegalArgumentException("Algorithm is not correct ;)");
							}
						}else if (map.containsKey(s1) || map.containsValue(s2)){
							throw new IllegalArgumentException("Algorithm is not correct ;)");
						}else{
							getIntersection(s1, s2, map);
						}
					}
				}
			}
			node1 = correspondence[0];
			node2 = correspondence[1];
		}
		return true;
	}**/
	
}
