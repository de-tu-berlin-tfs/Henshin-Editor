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

import java.util.HashMap;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleGraph;

public class TripleGraphMigration {

	public HashMap<Node, TNode> getNodes2TNodes() {
		return nodes2TNodes;
	}

	public TripleGraph getTGraph() {
		return tGraph;
	}

	public Graph getGraph() {
		return graph;
	}

	private HashMap<Node,TNode> nodes2TNodes = new HashMap<Node,TNode>();
	private TripleGraph tGraph = null;
	private Graph graph = null;
	
	public TripleGraphMigration() {
		// TODO Auto-generated constructor stub
	}

	public TripleGraphMigration(Graph g) {
		graph = g;
		graphToTripleGraph();
	}


	
	/**
	 * Creates a triple graph with contents of the given graph 
	 * @param graph
	 * @return
	 */
	private void graphToTripleGraph() {
		// create empty triple graph
		tGraph = TggFactory.eINSTANCE.createTripleGraph();
		tGraph.setName(graph.getName());
		// copy nodes and edges 
		for (Node n: graph.getNodes()){
			tGraph.getNodes().add(nodeToTNode(n));
		}
		for (Edge e: graph.getEdges()){
			tGraph.getEdges().add(edgeToTEdge(e));
		}
	}

	
	/**
	 * Creates a TNode with adjacent edges and attributes of the node
	 * @param node
	 * @return
	 */
	private TNode nodeToTNode(Node node) {
		// create empty TNode
		TNode tNode = TggFactory.eINSTANCE.createTNode();
		nodes2TNodes.put(node, tNode);
		tNode.setName(node.getName());
		tNode.setType(node.getType());
		// copy attributes and remove them from the given node
		for (Attribute a: node.getAttributes()){
			tNode.getAttributes().add(attributeToTAttribute(a));
		}
		return tNode;
	}

	/**
	 * Creates a TEdge 
	 * @param edge
	 * @return
	 */
	private TEdge edgeToTEdge(Edge edge) {
		// create empty TNode
		TEdge tEdge = TggFactory.eINSTANCE.createTEdge();
		tEdge.setSource(nodes2TNodes.get(edge.getSource()));
		tEdge.setTarget(nodes2TNodes.get(edge.getTarget()));
		tEdge.setType(edge.getType());
		return tEdge;
	}
	
	/**
	 * Creates a TAttribute 
	 * @param attribute
	 * @return
	 */
	private TAttribute attributeToTAttribute(Attribute attribute) {
		// create empty TNode
		TAttribute tAttribute = TggFactory.eINSTANCE.createTAttribute();
		tAttribute.setType(attribute.getType());
		tAttribute.setValue(attribute.getValue());
		return tAttribute;
	}

}
