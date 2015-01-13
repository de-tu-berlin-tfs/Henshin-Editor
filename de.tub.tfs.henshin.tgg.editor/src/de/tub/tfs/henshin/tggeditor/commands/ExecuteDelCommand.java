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
package de.tub.tfs.henshin.tggeditor.commands;

import java.util.List;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteAttributeCommand;
import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteEdgeCommand;
import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteNodeCommand;

/**
 * The Class ExecuteOpRulesCommand executes all the given Rules ({@link TRule}) on a given graph. For the
 * execution are mainly the classes from org.eclipse.emf.henshin.interpreter used. The mapping 
 * of the RuleApplication will be checked with the class {@link OpRuleConstraint}.
 * There will be also the layouts for nodes and edges created.
 */
public abstract class ExecuteDelCommand extends CompoundCommand {


	protected Graph graph;
	
	/**the constructor
	 * @param graph {@link ExecuteDelCommand#graph}
	 * @param opRuleList {@link ExecuteDelCommand#opRuleList}
	 */
	public ExecuteDelCommand(Graph graph, List<Rule> opRuleList) {
		super();
		this.graph = graph;
		//add(new ExecuteCCRulesCommand(graph, opRuleList));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return graph!=null;
	}

	@Override
	public void execute() {
		removeInconsistentElements();
		restrictMarkersToMarkedComponent();
		
		// execute sub commands only if there is at least one deletion command was added
		if(super.canExecute()) 
			super.execute();
	}

	private void restrictMarkersToMarkedComponent() {
		// fills translated maps with all given elements of the graph, initial
		// value is false = not yet translated
		// component(s) that shall be marked
		TNode tNode;
		for (Node node : graph.getNodes()) {
			if (node instanceof TNode) {
				tNode = (TNode) node;
				if (!isInTranslationComponent((TNode)node)) {
					removeMarkers(tNode);
				}
			}
		}
	}

	private void removeMarkers(TNode tNode) {
		tNode.setMarkerType(null);

		// handle attributes
		for (Attribute a : tNode.getAttributes()) {
			((TAttribute) a).setMarkerType(null);
		}

		// handle edges
		// node shall not be marked
		for (Edge e : tNode.getOutgoing()) {
			((TEdge) e).setMarkerType(null);
		}
		for (Edge e : tNode.getIncoming()) {
			((TEdge) e).setMarkerType(null);
		}
	}	

	private void removeInconsistentElements() {
		for (Node n : graph.getNodes()) {
			if (!isInTranslationComponent((TNode)n)) {
				// node is not in translation component - thus, it may have to
				// be deleted
				if (RuleUtil.Not_Translated_Graph.equals(((TNode) n)
						.getMarkerType()))
					// node is not consistent, thus delete it
					add(new DeleteNodeCommand(n));
				else // node is consistent, thus check its attributes and edges
				{
					for (Attribute a : n.getAttributes()) {
						if (RuleUtil.Not_Translated_Graph
								.equals(((TAttribute) a).getMarkerType()))
							// attribute is not consistent, thus delete it
							add(new DeleteAttributeCommand(a));
					}
					for (Edge e : n.getOutgoing()) {
						if (RuleUtil.Not_Translated_Graph.equals(((TEdge) e)
								.getMarkerType()))
							// edge is not consistent, thus delete it
							add(new DeleteEdgeCommand(e));
					}
				}

			}
			else // node is in translation component, handle the outgoing edges that may point outside the translation component
				for (Edge e : n.getOutgoing()) {
					if (!isInTranslationComponent((TNode)e.getTarget()) &&
							RuleUtil.Not_Translated_Graph.equals(((TEdge) e)
							.getMarkerType()))
						// edge point outside the translation component and edge is not consistent, thus delete it
						add(new DeleteEdgeCommand(e));
				}
		}
	}
	
	protected abstract boolean isInTranslationComponent(TNode node);
	
	


}
