/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */

package de.tub.tfs.henshin.tgg.interpreter.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleGraph;


public class RuleUtil {
	
	/** description of an original triple rule of the TGG  */
	public final static String TGG_RULE = "tgg";
	/** description of a derived forward translation rule of the TGG  */
	public final static String TGG_FT_RULE = "ft";
	public final static String TGG_BT_RULE = "bt";
	public final static String TGG_CC_RULE = "cc";
	//NEW
	public final static String TGG_CON_RULE = "con";
	//NEW
	/** description of a derived integration translation rule of the TGG */
	public final static String TGG_IT_RULE = "it";

	public final static String NEW = "<++>";
	public final static String Translated = "<tr>";
	public final static String Translated_Graph = "[tr]";
	public final static String Not_Translated_Graph = "[!tr]";
	public final static String TR_UNSPECIFIED = "[tr=?]";
	//NEW GERARD
	public final static String NEW_Graph = "New";
	public static final String ErrorMarker = "[unknown]";

	//NEW
	//public final static String INTERSECTING = "Intersecting";
	/**
	 * retrieves a list of possible markers for rule elements (in NAC, LHS, RHS) 
	 * @return
	 */
	public static HashSet<String> getRuleMarkerTypes(){ 
		HashSet<String> ruleMarkerTypes = new HashSet<String>();
		ruleMarkerTypes.addAll(Arrays.asList(new String[]{null,NEW,Not_Translated_Graph,TR_UNSPECIFIED,Translated,Translated_Graph}));
		return ruleMarkerTypes;
	};

		/**
	 * get the mapping in rule of given node of rhs
	 * @param rhsNode
	 * @return
	 */
	public static Mapping getRHSNodeMapping(Node rhsNode) {
		if(rhsNode!=null && rhsNode.getGraph() != null && rhsNode.getGraph().getRule() != null){
			EList<Mapping> mappingList = rhsNode.getGraph().getRule().getMappings();

			for (Mapping m : mappingList) {
				if (m.getImage() == rhsNode) {
					return m;
				}
			}
		}
		return null;
	}

	/**
	 * get all mappings in rule of given node of rhs
	 * @param rhsNode
	 * @return
	 */
	public static ArrayList<Mapping> getAllRHSNodeMappings(Node rhsNode) {
		if (rhsNode.getGraph().getRule() == null)
			return new ArrayList<Mapping>();
		EList<Mapping> mappingList = rhsNode.getGraph().getRule().getMappings();
		ArrayList<Mapping> result = new ArrayList<Mapping>();
		for (Mapping m : mappingList) {
			if (m.getImage() == rhsNode) {
				result.add(m);
			}
		}
		return result;
	}

	/**
	 * get the mapping in rule of given node of rhs
	 * @param lhsNode
	 * @return
	 */
	public static Mapping getLHSNodeMapping(Node lhsNode) {
		EList<Mapping> mappingList = lhsNode.getGraph().getRule().getMappings();

		for (Mapping m : mappingList) {
			if (m.getOrigin() == lhsNode) {
				return m;
			}
		}
		return null;
	}

	
	/**
	 * Gets the corresponding LHS node of a RHS node, if it exists
	 * @param node of the RHS
	 * @return corresponding node of the LHS
	 */
	public static Node getLHSNode(Node rhsNode) {
		Node lhsNode = null;
		Mapping nodeMapping = getRHSNodeMapping(rhsNode);

		if (nodeMapping!=null)
			lhsNode = nodeMapping.getOrigin();
	
		return lhsNode;
	}

	public static ArrayList<Node> getAllLHSNodes(
			Node rhsNode) {
		ArrayList<Node> result = new ArrayList<Node>();
		// scan all nodes in LHS node
		ArrayList<Mapping> nodeMappings = getAllRHSNodeMappings(rhsNode);
		for (Mapping m: nodeMappings){
			result.add(m.getOrigin());
		}
		return result;
	}

	
	public static Node getRHSNode(Node lhsNode) {
		Node rhsNode = null;
		Mapping nodeMapping=null;
		EList<Mapping> mappingList = lhsNode.getGraph().getRule().getMappings();
		for (Mapping m : mappingList) {
			if (m.getOrigin() == lhsNode) {
				nodeMapping=m;
			}
		}
		if (nodeMapping!=null)
			rhsNode = nodeMapping.getImage();
		return rhsNode;
	}


	public static Attribute getLHSAttribute(Attribute rhsAttribute) {
		Node lhsNode = getLHSNode(rhsAttribute.getNode());
		if(lhsNode!=null){
			// scan all attributes in LHS node
			for (Attribute att : lhsNode.getAttributes()){
				if (att.getType() == rhsAttribute.getType())
					// attribute was found - return it
					return att;
			}			
		}
		// attribute was not found
		return null;
	}

	public static ArrayList<Attribute> getAllLHSAttributes(
			Attribute rhsAttribute) {
		ArrayList<Attribute> result = new ArrayList<Attribute>();

		Node lhsNode = getLHSNode(rhsAttribute.getNode());		
		// scan all attributes in LHS node
		if (lhsNode != null) {
			for (Attribute att : lhsNode.getAttributes()) {
				if (att.getType() == rhsAttribute.getType())
					// attribute was found - return it
					result.add(att);
			}
		}
		return result;
	}

	/**
	 * Gets the corresponding LHS node of a RHS node, if it exists
	 * @param node of the RHS
	 * @return corresponding node of the LHS
	 */
	public static Edge getRHSEdge(Edge lhsEdge) {
		Node lhsSourceNode = lhsEdge.getSource();
		Node lhsTargetNode = lhsEdge.getTarget();

		Mapping sourceNodeMapping =getLHSNodeMapping(lhsSourceNode);
		Mapping targetNodeMapping =getLHSNodeMapping(lhsTargetNode);

		if(sourceNodeMapping !=null && targetNodeMapping !=null)
		{
			Node rhsSourceNode = sourceNodeMapping.getImage(); 

			for(Edge e: rhsSourceNode.getOutgoing())
			{
				if(e.getType()==lhsEdge.getType())
					return e;
			}
		}
		return null;
	}

	
	/**
	 * Gets the corresponding LHS node of a RHS node, if it exists
	 * @param node of the RHS
	 * @return corresponding node of the LHS
	 */
	public static Edge getLHSEdge(Edge rhsEdge) {
		Node rhsSourceNode = rhsEdge.getSource();
		Node rhsTargetNode = rhsEdge.getTarget();
		
		
		Mapping sourceNodeMapping =getRHSNodeMapping(rhsSourceNode);
		Mapping targetNodeMapping =getRHSNodeMapping(rhsTargetNode);

		if(sourceNodeMapping !=null && targetNodeMapping !=null)
		{
			Node lhsSourceNode = sourceNodeMapping.getOrigin(); 
			
			for(Edge e: lhsSourceNode.getOutgoing())
			{
				if(e.getTarget().equals(targetNodeMapping.getOrigin())){
					if (rhsEdge.getType().equals(e.getType()))
						return e;
				}
			}
		}
		return null;
	}

	
	
	
	public static ArrayList<Edge> getAllLHSEdges(
			Edge rhsEdge) {
		ArrayList<Edge> result = new ArrayList<Edge>();
		// scan all nodes in LHS node
		Node rhsSourceNode = rhsEdge.getSource();
		Node rhsTargetNode = rhsEdge.getTarget();

		if (rhsSourceNode!=null && rhsTargetNode!=null){
			Mapping sourceNodeMapping =getRHSNodeMapping(rhsSourceNode);
			Mapping targetNodeMapping =getRHSNodeMapping(rhsTargetNode);

			if(sourceNodeMapping !=null && targetNodeMapping !=null)
			{
				Node lhsSourceNode = sourceNodeMapping.getOrigin(); 
				Node lhsTargetNode = sourceNodeMapping.getImage(); 

				for(Edge e: lhsSourceNode.getOutgoing())
				{
					// check that the type and target node match
					if(e.getType()==rhsEdge.getType()
							&& e.getTarget()==lhsTargetNode)
						result.add(e);
				}
			}
		}
		return result;
	}


	
	public static Rule copyRule(Rule oldRule) {
		Copier copier = new Copier();
		EObject result = copier.copy(oldRule);
		copier.copyReferences();
		Rule r = (Rule)result;
		RuleUtil.setLhsCoordinatesAndLayout(r);
		return r;
	}
	
	public static EList<Graph> getNACGraphs(Rule rule){
		EList<Graph> nacGraphs = new BasicEList <Graph>();
		for (NestedCondition ac : rule.getLhs().getNestedConditions()) {
			if (ac.getConclusion() != null) {
				nacGraphs.add(ac.getConclusion());
			}
		}
		return nacGraphs;
	}
	
	public static boolean checkNodeMarker(String objectMarker,
			HashMap<Node, Boolean> isTranslatedMap, EObject graphObject) {

		if (
				(RuleUtil.Translated_Graph.equals(objectMarker) && isTranslatedMap
				.get(graphObject))
				// case: object is context element, then graph node has to be
				// translated already
				|| (RuleUtil.Not_Translated_Graph.equals(objectMarker) && !isTranslatedMap
						.get(graphObject))
				// case: object is effective element, then graph node has to be
				// translated already
				|| (RuleUtil.TR_UNSPECIFIED.equals(objectMarker) && isTranslatedMap
						.containsKey(graphObject))
				// case: object marker is not specified (e.g. for NAC objects)
				// (maybe only required for unmapped (from LHS to NAC graph) )
		) {
			return true;
		}
		return false;
	}

	public static boolean checkAttributeMarker(String objectMarker,
			HashMap<Attribute, Boolean> isTranslatedMap, EObject graphObject) {
		// case: graph does not explicitly contain this attribute. Therefore, it is set to the default value and the pattern matcher was successful.
		// TODO: during init: extend the graph with all default values.
		if(graphObject==null) return false;

		if (	(RuleUtil.Translated_Graph.equals(objectMarker) && isTranslatedMap
				.get(graphObject))
				// case: object is context element, then graph node has to be
				// translated already
				|| (RuleUtil.Not_Translated_Graph.equals(objectMarker) && !isTranslatedMap
						.get(graphObject))
				// case: object is effective element, then graph node has to be
				// translated already
				|| (RuleUtil.TR_UNSPECIFIED.equals(objectMarker) && isTranslatedMap
						.containsKey(graphObject))
				// case: object marker is not specified (e.g. for NAC objects)
				// (maybe only required for unmapped (from LHS to NAC graph) )
		) {
			return true;
		}
		return false;
	}
	
	public static boolean checkAttributeMarkerEMF(String objectMarker,
			HashMap<EObject, HashMap<EAttribute,Boolean>> isTranslatedMap, EObject graphNodeObject, EAttribute graphObject) {

		if (	(RuleUtil.Translated_Graph.equals(objectMarker) && isTranslatedMap
				.get(graphNodeObject).get(graphObject))
				// case: object is context element, then graph node has to be
				// translated already
				|| (RuleUtil.Not_Translated_Graph.equals(objectMarker) && !isTranslatedMap
						.get(graphNodeObject).get(graphObject))
				// case: object is effective element, then graph node has to be
				// translated already
				|| (RuleUtil.TR_UNSPECIFIED.equals(objectMarker) && isTranslatedMap
						.get(graphNodeObject).containsKey(graphObject))
				// case: object marker is not specified (e.g. for NAC objects)
				// (maybe only required for unmapped (from LHS to NAC graph) )
		) {
			return true;
		}
		return false;
	}

	
	public static boolean checkEdgeMarker(String objectMarker,
			HashMap<Edge, Boolean> isTranslatedMap, EObject graphObject) {

		if (
				(RuleUtil.Translated_Graph.equals(objectMarker) && isTranslatedMap
				.get(graphObject))
				// case: object is context element, then graph node has to be
				// translated already
				|| (RuleUtil.Not_Translated_Graph.equals(objectMarker) && !isTranslatedMap
						.get(graphObject))
				// case: object is effective element, then graph node has to be
				// translated already
				|| (RuleUtil.TR_UNSPECIFIED.equals(objectMarker) && isTranslatedMap
						.containsKey(graphObject))
				// case: object marker is not specified (e.g. for NAC objects)
				// (maybe only required for unmapped (from LHS to NAC graph) )
		) {
			return true;
		}
		return false;
	}




	// compute whether the node is contained in an LHS of a rule
	public static boolean isLHSNode(Node n) {
		Rule r = n.getGraph().getRule();
		if(r!=null && r.getLhs()==n.getGraph())
			return true;
		else
			return false;
	}



	// check whether the graph is an LHS graph
	public static boolean isLHSGraph(Graph graph) {
		return (graph.getRule() != null && graph.getRule().getLhs() == graph);
	}




	public static boolean graphIsOpRuleRHS(Graph graph) {

		TGGRule rule = null;
		Rule r = graph.getRule();
		if (r instanceof TGGRule)
			rule = (TGGRule) r;
		if (rule==null || RuleUtil.TGG_RULE.equals(rule.getMarkerType()))
			return false;
		return true;

	}	
	

	
	public static boolean setLhsCoordinatesAndLayout(Rule rule){
		if (rule.getLhs().getNodes().size()==0) return false;
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
	
	

}
