package de.tub.tfs.henshin.tggeditor.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.henshin.tgg.GraphLayout;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.GraphEditPart;
import de.tub.tfs.henshin.tggeditor.util.NodeTypes.NodeGraphType;


public class GraphUtil {
	
	static public int center = 350;
	static public int correstpondenceWidth = 100;
	
	/**
	 * calculates the NodeGraphType for specific x coordinate in graph
	 * @param x is the given x coordinate
	 * @param graphEditPart where to calculate the type
	 * @return type
	 */
	public static NodeGraphType getNodeGraphTypeForXCoordinate(GraphEditPart graphEditPart, int x) {
		if(graphEditPart != null) {
			int SCx = graphEditPart.getCastedModel().getDividerSC_X();
			int CTx = graphEditPart.getCastedModel().getDividerCT_X();
			correstpondenceWidth = CTx-SCx;
			center = SCx + correstpondenceWidth/2;
		}

		if(x < center - correstpondenceWidth/2) return NodeGraphType.SOURCE;
		if(x < center + correstpondenceWidth/2) return NodeGraphType.CORRESPONDENCE;
		if(x >= center + correstpondenceWidth/2) return NodeGraphType.TARGET;
		return NodeGraphType.SOURCE;
	}

	/**
	 * calculates the triple component for specific x coordinate in graph
	 * @param x is the given x coordinate
	 * @param graphEditPart where to calculate the type
	 * @return type
	 */
	public static TripleComponent getTripleComponentForXCoordinate(GraphEditPart graphEditPart, int x) {
		if(graphEditPart != null) {
			int SCx = graphEditPart.getCastedModel().getDividerSC_X();
			int CTx = graphEditPart.getCastedModel().getDividerCT_X();
			correstpondenceWidth = CTx-SCx;
			center = SCx + correstpondenceWidth/2;
		}

		if(x < center - correstpondenceWidth/2) return TripleComponent.SOURCE;
		if(x < center + correstpondenceWidth/2) return TripleComponent.CORRESPONDENCE;
		if(x >= center + correstpondenceWidth/2) return TripleComponent.TARGET;
		return TripleComponent.SOURCE;
	}

	public static int getMinXCoordinateForNodeGraphType(TripleComponent type){
		switch (type) {
		case SOURCE : return 0;
		case CORRESPONDENCE: return center-correstpondenceWidth/2;
		case TARGET: return center+correstpondenceWidth /2;
		}
		return 0;
	}

	// old version, used for critical pairs
	public static int getMinXCoordinateForNodeGraphType(NodeTypes.NodeGraphType type){
		switch (type) {
		case SOURCE : return 0;
		case CORRESPONDENCE: return center-correstpondenceWidth/2;
		case TARGET: return center+correstpondenceWidth /2;
		}
		return 0;
	}

	
	/**
	 * Searches the graph layout of divider. If dividerSC is true it searches for 
	 * source-correspondence divider, if its false it searches for the correspondence-target divider.
	 * @param graph which is linked to its dividers
	 * @param dividerSC (search SC or CT divider?)
	 * @return graph layout of searched divider
	 */
	public static GraphLayout getGraphLayout(Graph graph, boolean dividerSC) {
		TGG layoutSystem = NodeUtil.getLayoutSystem(graph);
		if (layoutSystem != null) {
			for (GraphLayout graphLayout : layoutSystem.getGraphlayouts()) {
				if (graphLayout.getGraph() == graph) {
					if (dividerSC == graphLayout.isIsSC())
						return graphLayout;
				}
			}
		}
		return null;
	}
	/**
	 * Searches the graph layout for the dividers and returns them.
	 * @param graph which is linked to its dividers
	 * @return graph layouts [dividerSC,dividerCT] 
	 */
	public static GraphLayout[] getGraphLayouts(Graph graph) {
		TGG layoutSystem = NodeUtil.getLayoutSystem(graph);
		GraphLayout[] layouts = new GraphLayout[2];
		if (layoutSystem != null) {
			for (GraphLayout graphLayout : layoutSystem.getGraphlayouts()) {
				if (graphLayout.getGraph() == graph) {
					if (graphLayout.isIsSC()) layouts[0]=graphLayout;
					else layouts[1]=graphLayout;
				}
			}
		}
		return layouts;
	}
	
	/**
	 * Computes the sets of nodes in each triple component.
	 * @param graph the graph that contains the nodes
	 * @return the distinguished node sets for each triple component
	 */
	public static HashMap<TripleComponent, List<Node>> getDistinguishedNodeSets(
			Graph graph) {
		if (graph == null) return null;
		HashMap<TripleComponent, List<Node>> nodeSets= new HashMap<TripleComponent, List<Node>>();
		EList<Node> nodes = graph.getNodes();
		List<Node> sourceNodes = new Vector<Node>();
		List<Node> corrNodes = new Vector<Node>();
		List<Node> targetNodes = new Vector<Node>();
		Iterator<Node> iter = nodes.iterator();
		Node node;
		// iterate over all nodes and put them in the respective lists for each component
		while(iter.hasNext()){
			node= iter.next();
			if(NodeUtil.isSourceNode(node))
				sourceNodes.add(node);
			else if(NodeUtil.isCorrespondenceNode(node))
				corrNodes.add(node);
			else 
				targetNodes.add(node);
		}
		// add the lists to the hash map
		nodeSets.put(TripleComponent.SOURCE, sourceNodes);
		nodeSets.put(TripleComponent.CORRESPONDENCE, corrNodes);
		nodeSets.put(TripleComponent.TARGET, targetNodes);
		return nodeSets;
	}
	
	/**
	 * Computes the sets of edges in each triple component.
	 * @param graph the graph that contains the edges
	 * @return the distinguished edge sets for each triple component
	 */
	public static HashMap<TripleComponent, List<Edge>> getDistinguishedEdgeSets(
			Graph graph) {
		if (graph == null) return null;
		HashMap<TripleComponent, List<Edge>> edgeSets= new HashMap<TripleComponent, List<Edge>>();
		EList<Edge> edges = graph.getEdges();
		List<Edge> sourceEdges = new Vector<Edge>();
		List<Edge> corrEdges = new Vector<Edge>();
		List<Edge> targetEdges = new Vector<Edge>();
		Iterator<Edge> iter = edges.iterator();
		Edge edge;
		// iterate over all nodes and put them in the respective lists for each component
		while(iter.hasNext()){
			edge= iter.next();
			if(NodeUtil.isSourceNode(edge.getSource())
					&& NodeUtil.isSourceNode(edge.getTarget()))
				sourceEdges.add(edge);
			else if(NodeUtil.isTargetNode(edge.getSource())
					&& NodeUtil.isTargetNode(edge.getTarget()))
				targetEdges.add(edge);
			else 
				corrEdges.add(edge);
		}
		// add the lists to the hash map
		edgeSets.put(TripleComponent.SOURCE, sourceEdges);
		edgeSets.put(TripleComponent.CORRESPONDENCE, corrEdges);
		edgeSets.put(TripleComponent.TARGET, targetEdges);
		return edgeSets;
	}

	/**
	 * Creates a triple graph with contents of the given graph and clears the contents of the given graph
	 * @param graph
	 * @return
	 */
	public static TripleGraph graphToTripleGraph(Graph graph) {
		// create empty triple graph
		TripleGraph tripleGraph = TggFactory.eINSTANCE.createTripleGraph();
		tripleGraph.setName(graph.getName());
		// copy nodes and remove them from the given graph
		tripleGraph.getNodes().addAll(graph.getNodes());
		graph.getNodes().clear();
		// copy edges and remove them from the given graph
		tripleGraph.getEdges().addAll(graph.getEdges());
		graph.getEdges().clear();
		return tripleGraph;
	}
}