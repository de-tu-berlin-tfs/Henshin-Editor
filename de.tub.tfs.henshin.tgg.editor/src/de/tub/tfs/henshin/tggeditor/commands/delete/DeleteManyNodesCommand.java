package de.tub.tfs.henshin.tggeditor.commands.delete;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

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
		// remove edges, avoid notification after each single edge
		graph.getEdges().removeAll(edgesToDelete);
		// remove nodes, avoid notification after each single node
		graph.getNodes().removeAll(nodesToDelete);
	}

	@Override
	public void undo() {
		// remove edges, avoid notification after each single edge
		graph.getEdges().addAll(edgesToDelete);
		// remove nodes, avoid notification after each single node
		graph.getNodes().addAll(nodesToDelete);
	}

}
