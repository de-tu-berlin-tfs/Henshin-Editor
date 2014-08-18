/*******************************************************************************
 * Copyright (c) 2012, 2014 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.GraphLayout;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.impl.NodeTypes;
import de.tub.tfs.henshin.tgg.interpreter.util.ExceptionUtil;
import de.tub.tfs.henshin.tgg.interpreter.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.GraphEditPart;


public class GraphUtil {
	
	static public int center = 350;
	static public int correstpondenceWidth = 100;
	


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


	
	/**
	 * Searches the graph layout of divider. If dividerSC is true it searches for 
	 * source-correspondence divider, if its false it searches for the correspondence-target divider.
	 * @param graph which is linked to its dividers
	 * @param dividerSC (search SC or CT divider?)
	 * @return graph layout of searched divider
	 */
	public static GraphLayout getGraphLayout(Graph graph, boolean dividerSC) {
		TGG layoutSystem = GraphicalNodeUtil.getLayoutSystem(graph);
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
		TGG layoutSystem = GraphicalNodeUtil.getLayoutSystem(graph);
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
	public static HashMap<TripleComponent, List<TNode>> getDistinguishedNodeSets(
			Graph graph) {
		if (graph == null) {ExceptionUtil.error("Graph is missing for computing distinguished node sets."); return null;}
		HashMap<TripleComponent, List<TNode>> nodeSets= new HashMap<TripleComponent, List<TNode>>();
		EList<Node> nodes = graph.getNodes();
		List<TNode> sourceNodes = new Vector<TNode>();
		List<TNode> corrNodes = new Vector<TNode>();
		List<TNode> targetNodes = new Vector<TNode>();
		Iterator<TNode> iter = (Iterator)nodes.iterator();
		TNode node;
		// iterate over all nodes and put them in the respective lists for each component
		while(iter.hasNext()){
			node= iter.next();
			if(NodeUtil.isSourceNode(node))
				sourceNodes.add((TNode) node);
			else if(NodeUtil.isCorrespondenceNode(node))
				corrNodes.add((TNode) node);
			else 
				targetNodes.add((TNode) node);
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
		if (graph == null) {ExceptionUtil.error("Graph is missing for computing distinguished edge sets."); return null;}
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
			if(NodeUtil.isSourceNode((TNode) edge.getSource())
					&& NodeUtil.isSourceNode((TNode) edge.getTarget()))
				sourceEdges.add(edge);
			else if(NodeUtil.isTargetNode((TNode) edge.getSource())
					&& NodeUtil.isTargetNode((TNode) edge.getTarget()))
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
	
	
	//NEW
	public static void removeDoubleEdges(Graph graph){
		HashSet<Edge> removed = new HashSet<Edge>();
		HashSet<Edge> stays = new HashSet<Edge>();
		for (Edge edge : graph.getEdges()){
			for (Edge edge2 : graph.getEdges()){
				if (edge!=edge2 && edge.getSource()==edge2.getSource() && edge.getTarget()==edge2.getTarget() && (! (stays.contains(edge) || removed.contains(edge)) ) ){
					removed.add(edge2);
					//((TEdge)edge).setMarkerType(null);
					stays.add(edge);
				}
			} 
		}
		
		
		graph.getEdges().removeAll(removed);
		for (Edge edge : removed){
			edge.getSource().getOutgoing().remove(edge);
			edge.getTarget().getIncoming().remove(edge);
		}
	}
	
	

	
	
	
	
	//NEW 
	/*
	public static Graph merge(Graph g1, Graph g2) {
		
		Graph result = copyGraph(g1, null, null, false);
		Iterator<Node> nodes1 =  g1.getNodes().iterator();
		while (nodes1.hasNext()){
			Node n1 = nodes1.next();
			if (g2.getNode(n1.getName())!=null){
				Node n2 = g2.getNode(n1.getName());
				for (Attribute a : n2.getAttributes()){
					if (!n1.getAttributes().contains(a)){
						result.getNode(n1.getName()).getAttributes().add(a);
					}
				}
				for (Edge edg : n2.getIncoming()){
					edg.getSource().getName();
				}
			}
		}
		return null;
	}*/
}
