package de.tub.tfs.henshin.tggeditor.commands.create;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.RuleUtil;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;



/**
 * The class CreateEdgeCommand creates an edge from a source to a target node in a graph.
 */
public class CreateEdgeCommand extends Command {
	/** The graph. */
	protected TripleGraph graph;
	
	/** The edge. */
	protected Edge edge;
	
	/** The sourcenode. */
	protected Node sourceNode;
	
	/** The targetnode. */
	protected Node targetNode;
	
	/** The type reference. */
	protected EReference typeReference;
	
//	protected EdgeLayout edgeLayout;
	
	protected TGG layout;
	
	/**
	 * Instantiates a new creates the edge command.
	 *
	 * @param sourceNode the source node
	 * @param requestingObject the requesting object
	 */
	public CreateEdgeCommand(Node sourceNode, Edge requestingObject) {
		this.graph = (TripleGraph) sourceNode.getGraph();
		this.sourceNode = sourceNode;
		this.edge = requestingObject;
		this.typeReference = null;
		
		this.layout = GraphicalNodeUtil.getLayoutSystem(sourceNode.getGraph()); 
	}
	
	/**
	 * Instantiates a new creates the edge command.
	 *
	 * @param graph the graph
	 * @param source the source
	 * @param target the target
	 * @param eReference the type reference
	 */
	public CreateEdgeCommand(TripleGraph graph, Node source, Node target,
			EReference eReference) {
		this.edge = TggFactory.eINSTANCE.createTEdge();
		this.graph = graph;
		this.sourceNode = source;
		this.targetNode = target;
		this.typeReference = eReference;

		this.layout = GraphicalNodeUtil.getLayoutSystem(source.getGraph()); 
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		
		if (! edgeComplete()) {
			if (edge.getType() == null) {
				edge.setType(typeReference);
			}
			
//			edgeLayout = EdgeUtil.getEdgeLayout(edge, layout);
//			if (edgeLayout != null) {
//				edgeLayout.setRhsedge(edge);
//			}
			
			edge.setSource(sourceNode);
			edge.setTarget(targetNode);
		}
		edge.setGraph(graph);
		// graph.getEdges().add(edge); // automatically handled in line above
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		graph.getEdges().remove(edge);
		edge.getSource().getOutgoing().remove(edge);
		edge.getTarget().getIncoming().remove(edge);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		if(RuleUtil.graphIsOpRuleRHS(graph))
			return false;
		if(edgeComplete() || (graph != null && targetNode != null && sourceNode != null)) {
			return true;
		}
		return false;
	}
	
	protected boolean edgeComplete() {
		return edge.getSource() != null
				&& edge.getTarget() != null
				&& edge.getType() != null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return edgeComplete()
				||
				(graph != null && edge != null && sourceNode != null
				&& targetNode != null);
	}
	
	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public Node getSource() {
		return sourceNode;
	}

	/**
	 * Sets the target.
	 *
	 * @param target the new target
	 */
	public void setTarget(Node target) {
		this.targetNode = target;
	}

	/**
	 * Checks if is source place.
	 *
	 * @return true, if is source place
	 */
	public boolean isSourcePlace() {
		return sourceNode instanceof Node;
	}


	/**
	 * Gets the target.
	 *
	 * @return the target
	 */
	public Node getTarget() {
		return targetNode;
	}

	/**
	 * Sets the type reference.
	 *
	 * @param typeReference the new type reference
	 */
	public void setTypeReference(EReference typeReference) {
		this.typeReference = typeReference;
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
}
