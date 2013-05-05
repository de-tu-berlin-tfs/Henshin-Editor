package de.tub.tfs.henshin.tggeditor.util;

import org.eclipse.emf.henshin.interpreter.util.HenshinEGraph;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.henshin.tgg.TggFactory;

public class TggHenshinEGraph extends HenshinEGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * creates a triple graph with synchronized EMF instance
	 * @param graph
	 */
	public TggHenshinEGraph(Graph graph) {
		super(graph);
	}

	/** 
	 * creates a new node of a triple graph with layout and marker information
	 * returns the created TNode
	 */
	@Override
	protected Node createNode() {
		return TggFactory.eINSTANCE.createTNode();
	}
	
	@Override
	protected Attribute createAttribute() {
		// TODO Auto-generated method stub
		return TggFactory.eINSTANCE.createTAttribute();
	}
	
	@Override
	protected Edge createEdge() {
		// TODO Auto-generated method stub
		return TggFactory.eINSTANCE.createTEdge();
	}
}
