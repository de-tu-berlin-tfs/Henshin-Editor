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
package de.tub.tfs.henshin.tggeditor.commands.delete;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.analysis.AnalysisPackage;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;





/**
 * DeleteNodeCommand deletes a node.
 */
public class DeleteManyNodesCommand extends CompoundCommand {


	/**
	 * graph that contains the nodes to be deleted
	 */
	Graph graph = null; 

	
	/**
	 * list of nodes to be deleted
	 */
	List<Node> nodesToDelete = null; 


	/**
	 * list of edges to be deleted
	 */
	List<Edge> edgesToDelete = null; 

	
	/**
	 * The constructor creates a compound command in all the deletion operations
	 * Be included, which are needed to the nodes of the graph to
	 * Remove. In this all incoming and outgoing edges, and
	 * The attributes individually deleted.
	 *
	 * @param node the node to delete
	 */
	public DeleteManyNodesCommand(List<Node> nodesToDelete) {
		
		this.nodesToDelete= nodesToDelete;
		if(nodesToDelete==null) return;
		
		this.edgesToDelete = new Vector<Edge>();

		for(Node node: nodesToDelete){
			for(Edge edge: node.getIncoming()){
				edgesToDelete.add(edge);
			}
			for(Edge edge: node.getOutgoing()){
				edgesToDelete.add(edge);
			}
		}
		if(nodesToDelete.size()<1) return;
		graph = nodesToDelete.get(0).getGraph();
		for(Edge edge:edgesToDelete){
			add(new DeleteEdgeCommand(edge));
		}



	}
	



	@Override
	public boolean canExecute() {
		return (nodesToDelete != null && edgesToDelete != null && graph!=null);
	}




	@Override
	public boolean canUndo() {
		return (nodesToDelete != null && edgesToDelete != null && graph!=null);
	}




	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		if (nodesToDelete.size() > 0) {
			// remove edges, avoid notification after each single edge
			graph.eSetDeliver(false);
			super.execute();
			// remove nodes, avoid notification after each single node
			
			graph.getNodes().removeAll(nodesToDelete);
			graph.eSetDeliver(true);
			graph.eNotify(new ENotificationImpl((InternalEObject) nodesToDelete.get(0), Notification.REMOVE, HenshinPackage.GRAPH__NODES, 
					nodesToDelete.get(0), null));
		}
	}

	@Override
	public void undo() {
		if (nodesToDelete.size() > 0) {
			// undo removal of edges, avoid notification after each single edge
			graph.eSetDeliver(false);
			super.undo();
			graph.eSetDeliver(true);
			// add nodes, avoid notification after each single node
			graph.getNodes().addAll(nodesToDelete);
		}
	}

}
