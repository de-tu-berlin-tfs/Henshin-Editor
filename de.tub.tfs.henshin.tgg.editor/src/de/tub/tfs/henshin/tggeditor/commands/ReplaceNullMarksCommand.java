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
//NEW GERARD
/**
 * The Class ExecutionInitCommand creates the initial marking for executing the operational rules on a given graph. 
 */
public class ReplaceNullMarksCommand extends CompoundCommand {


	/**
	 * The graph on which all the rules will be applied.
	 */
	protected Graph graph;
	protected String mark;

	
	
	/**the constructor
	 * @param graph {@link ReplaceNullMarksCommand#graph}
	 * @param opRuleList {@link ReplaceNullMarksCommand#opRuleList}
	 */
	public ReplaceNullMarksCommand(Graph graph, String mark) {
		super();
		this.graph = graph;
		this.mark = mark;
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
		TNode tNode;
		for (Node node : graph.getNodes()) {
			if (node instanceof TNode) {
				tNode=(TNode) node;
				if(tNode.getMarkerType()==null)
					tNode.setMarkerType(mark);
					
				for (Attribute a : tNode.getAttributes()) {
					if(null==(((TAttribute) a).getMarkerType()))
						((TAttribute) a).setMarkerType(mark);
				}
				
				for (Edge e : tNode.getOutgoing()) {
					if(null==(((TEdge) e).getMarkerType()))
						((TEdge) e).setMarkerType(mark);
				}
			}
		}
	}



}
