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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The Class DeleteEdgeCommand.
 */
public class DeleteEdgeCommand extends CompoundCommand {

	/** The edge. */
	private Edge edge;

	/** The graph. */
	private Graph graph;

	/** The source. */
	private Node source;

	/** The target. */
	private Node target;

	/**
	 * Instantiates a new delete edge command.
	 * 
	 * @param edge
	 *            the edge
	 */
	public DeleteEdgeCommand(Edge edge) {
		if (edge != null) {
			this.edge = edge;
			this.graph = edge.getGraph();
			this.source = edge.getSource();
			this.target = edge.getTarget();

			add(new SimpleDeleteEObjectCommand(edge));
			
			removeEdge(edge, graph.getRule());
			
		}
	}

	
private boolean removeEdge(Edge edge,Rule rule) {
		if (rule == null)
			return false;
		// Must be invoked from the root kernel rule:
		if (rule.getRootRule()!=rule) {
			return removeEdge(edge, rule.getRootRule());
		}
		
		// Edges to be removed:
		Set<Edge> edges = new HashSet<Edge>();
		edges.add(edge);
		
		// Collect mapped edges if necessary:
			// Collect a list of ALL mappings:
			MappingList mappings = rule.getAllMappings();
			// Now collect edges to be removed:
			boolean changed;
			do {
				changed = false;
				TreeIterator<EObject> it = rule.eAllContents();
				while (it.hasNext()) {
					EObject obj = it.next();
					if (obj instanceof Edge) {
						Edge e = (Edge) obj;
						if (e.getType()!=edge.getType() || edges.contains(e)) {
							continue;
						}
						if ((mappings.get(edge.getSource(), e.getSource())!=null &&
							 mappings.get(edge.getTarget(), e.getTarget())!=null) ||
							(mappings.get(e.getSource(), edge.getSource())!=null &&
							 mappings.get(e.getTarget(), edge.getTarget())!=null)) {
							edges.add(e);
							changed = true;
						}
					}
				}
			} while (changed);
		
		
		// Now remove the collected edges:
		for (Edge e : edges) {
			add(new SimpleDeleteEObjectCommand(e));
		}
		return true;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		graph.getEdges().remove(edge);
		source.getOutgoing().remove(edge);
		target.getIncoming().remove(edge);
		super.execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		if (HenshinLayoutUtil.INSTANCE.hasOriginInKernelRule(source) && HenshinLayoutUtil.INSTANCE.hasOriginInKernelRule(target)){
			return false;
		}
		
		return graph != null && edge != null && source != null
				&& target != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#undo()
	 */
	@Override
	public void undo() {
		graph.getEdges().add(edge);
		edge.setSource(source);
		edge.setTarget(target);
		super.undo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#redo()
	 */
	@Override
	public void redo() {
		execute();
		super.redo();
	}
}
