package de.tub.tfs.henshin.tggeditor.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;


public class RuleUtil {
	
	/** description of an original triple rule of the TGG  */
	public final static String TGG_RULE = "tgg";
	/** description of a derived forward translation rule of the TGG  */
	public final static String TGG_FT_RULE = "ft";
	public final static String TGG_BT_RULE = "bt";
	public final static String TGG_CC_RULE = "cc";

	public final static String NEW = "<++>";
	public final static String Translated = "<tr>";
	public final static String Translated_Graph = "[tr]";
	public final static String Not_Translated_Graph = "[!tr]";
	public final static String TR_UNSPECIFIED = "[tr=?]";
	public static final String ErrorMarker = "[unknown]";

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

		return (Rule) result;
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
	
	

}
