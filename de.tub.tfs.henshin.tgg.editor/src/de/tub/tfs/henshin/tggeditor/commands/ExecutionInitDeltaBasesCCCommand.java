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

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.util.GraphicalRuleUtil;

/**
 * The Class ExecutionInitCommand creates the initial marking for executing the operational rules on a given graph. 
 */
public abstract class ExecutionInitDeltaBasesCCCommand extends CompoundCommand {


	/**
	 * The graph on which all the rules will be applied.
	 */
	protected Graph graph;

	
	
	/**the constructor
	 * @param graph {@link ExecutionInitDeltaBasesCCCommand#graph}
	 * @param opRuleList {@link ExecutionInitDeltaBasesCCCommand#opRuleList}
	 */
	public ExecutionInitDeltaBasesCCCommand(Graph graph) {
		super();
		this.graph = graph;
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return (graph != null);
	}
	
	/** Mark all nodes, attributes and edges in the marked components to be untranslated
	 * and remove the markers for all other elements
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		initGraphMarkers();
	}

	protected void initGraphMarkers() {
		// fills translated maps with all given elements of the graph, initial
		// value is false = not yet translated
		// component(s) that shall be marked
		TNode tNode;
		for (Node node : graph.getNodes()) {
			if (node instanceof TNode) {
				tNode=(TNode) node;
				boolean nodeIsInDeltaComp = isInDeltaComponent(node);
				if(!(nodeIsInDeltaComp && tNode.getMarkerType()==null)){//(RuleUtil.NEW_Graph.equals(tNode.getMarkerType())))){
					tNode.setMarkerType(RuleUtil.Not_Translated_Graph);
					
					for (Attribute a : tNode.getAttributes()) {
						if(!(nodeIsInDeltaComp && ((TAttribute) a).getMarkerType()==null))//&& (RuleUtil.NEW_Graph.equals(((TAttribute) a).getMarkerType()))))
							((TAttribute) a).setMarkerType(RuleUtil.Not_Translated_Graph);
					}
					
					for (Edge e : tNode.getOutgoing()) {
						if(!(nodeIsInDeltaComp && ((TEdge) e).getMarkerType()==null)) //&& (RuleUtil.NEW_Graph.equals(((TEdge) e).getMarkerType()))))
							((TEdge) e).setMarkerType(RuleUtil.Not_Translated_Graph);
					}

					for (Edge e : tNode.getIncoming()) {
						if(!(nodeIsInDeltaComp && ((TEdge) e).getMarkerType()==null)) //&& (RuleUtil.NEW_Graph.equals(((TEdge) e).getMarkerType()))))
							((TEdge)e).setMarkerType(RuleUtil.Not_Translated_Graph);
					}
					
				}
				
			}
		}
	}


	protected abstract boolean isInDeltaComponent(Node node);

}
