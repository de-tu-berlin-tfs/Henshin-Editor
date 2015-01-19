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
package de.tub.tfs.henshin.editor.commands.graph;

import java.util.HashMap;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.SimpleAddEObjectCommand;
import de.tub.tfs.henshin.editor.commands.SimpleSetEFeatureCommand;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.editor.util.JavaUtil;

/**
 * The Class CreateEdgeCommand.
 */
public class CreateEdgeCommand extends CompoundCommand {

	/** The graph. */
	private Graph graph;

	/** The edge. */
	private Edge edge;

	/** The source. */
	private Node source;

	/** The target. */
	private Node target;

	private boolean skipCanExecuteCheck = false;
	
	/**
	 * @param e
	 * @param src
	 * @param target
	 * @param graph
	 * @param type
	 */
	public CreateEdgeCommand(Edge e, Node src, Node target, Graph graph,
			EReference type) {
		this.edge = e;
		this.edge.setType(type);
		this.graph = graph;
		this.source = src;
		this.target = target;

		init();
	}

	/**
	 * Instantiates a new creates the edge command.
	 * 
	 * @param node
	 *            the source node
	 * @param requestingObject
	 *            the requesting object
	 */
	public CreateEdgeCommand(Node node, Edge requestingObject) {
		this(requestingObject, node, null, node.getGraph(), null);
	}

	/**
	 * Instantiates a new creates the edge command.
	 * 
	 * @param graph
	 *            the graph
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * @param typeReference
	 *            the type reference
	 */
	public CreateEdgeCommand(Graph graph, Node source, Node target,
			EReference typeReference) {
		this(HenshinFactory.eINSTANCE.createEdge(), source, target, graph,
				typeReference);
	}

	/**
	 * Gets the source.
	 * 
	 * @return the source
	 */
	public Node getSource() {
		return source;
	}

	/**
	 * Sets the target.
	 * 
	 * @param target
	 *            the new target
	 */
	public void setTarget(Node target) {
		this.target = target;

		//init();
	}

	/**
	 * Sets the type reference.
	 * 
	 * @param typeReference
	 *            the new type reference
	 */
	public void setTypeReference(EReference typeReference) {
		this.edge.setType(typeReference);
		init();
	}

	/**
	 * Gets the target.
	 * 
	 * @return the target
	 */
	public Node getTarget() {
		return target;
	}

	public Edge getEdge() {
		return edge;
	}

	/**
	 * Gets the graph.
	 * 
	 * @return the graph
	 */
	public Graph getGraph() {
		return graph;
	}

	/**
     * 
     */
	private void init() {
		if (JavaUtil.notNull(edge, source, target, graph)) {

			add(new SimpleAddEObjectCommand<Graph, Edge>(edge,
					HenshinPackage.Literals.GRAPH__EDGES, graph));

			add(new SimpleSetEFeatureCommand<Edge, Node>(edge, source,
					HenshinPackage.EDGE__SOURCE));
			add(new SimpleSetEFeatureCommand<Edge, Node>(edge, target,
					HenshinPackage.EDGE__TARGET));
			
			CompoundCommand multiNodeCommands = new CompoundCommand();
			if (graph.getRule() != null && !graph.getRule().getAllMultiRules().isEmpty()){
			MappingList mappings = graph.getRule().getMultiMappings();
			
			HashMap<Graph,Node> multiSource = new HashMap<Graph, Node>();
			HashMap<Graph,Node> multiTarget = new HashMap<Graph, Node>();

			for (Mapping m : mappings) {
				if (m.getOrigin() != null && m.getOrigin().equals(source)){
					multiSource.put(m.getImage().getGraph(), m.getImage());
					
					continue;
				}
				if (m.getOrigin() != null && m.getOrigin().equals(target)){
					multiTarget.put(m.getImage().getGraph(), m.getImage());
					continue;
				}
			}
			for (Entry<Graph, Node> es : multiSource.entrySet()) {
				Node multiT = multiTarget.get(es.getKey());
				if (multiT != null){
					CreateEdgeCommand c = new CreateEdgeCommand(es.getKey(),es.getValue(),multiT, edge.getType());
					c.setSkipFlag(true);
					multiNodeCommands.add(c);
					
				}
			}
			
			add(multiNodeCommands);
			}
		}
	}
	
	@Override
	public boolean canExecute() {
		if (skipCanExecuteCheck)
			return true;
		if (HenshinLayoutUtil.INSTANCE.hasOriginInKernelRule(source) && HenshinLayoutUtil.INSTANCE.hasOriginInKernelRule(target))
			return false;
		return true;
	}
	
	private void setSkipFlag(boolean skip){
		this.skipCanExecuteCheck = skip;
	}
}
