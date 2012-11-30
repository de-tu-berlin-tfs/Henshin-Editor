package tggeditor.commands.create;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.Command;

import tgg.EdgeLayout;
import tgg.TGG;
import tgg.TRule;
import tggeditor.util.EdgeReferences;
import tggeditor.util.EdgeUtil;
import tggeditor.util.NodeUtil;


/**
 * The class CreateEdgeCommand creates an edge from a source to a target node in a graph.
 */
public class CreateEdgeCommand extends Command {
	/** The graph. */
	protected Graph graph;
	
	/** The edge. */
	protected Edge edge;
	
	/** The sourcenode. */
	protected Node sourceNode;
	
	/** The targetnode. */
	protected Node targetNode;
	
	/** The type reference. */
	protected EReference typeReference;
	
	protected EdgeLayout edgeLayout;
	
	protected TGG layout;
	
	/**
	 * Instantiates a new creates the edge command.
	 *
	 * @param sourceNode the source node
	 * @param requestingObject the requesting object
	 */
	public CreateEdgeCommand(Node sourceNode, Edge requestingObject) {
		this.graph = sourceNode.getGraph();
		this.sourceNode = sourceNode;
		this.edge = requestingObject;
		this.typeReference = null;
		
		this.layout = NodeUtil.getLayoutSystem(sourceNode.getGraph()); 
	}
	
	/**
	 * Instantiates a new creates the edge command.
	 *
	 * @param graph the graph
	 * @param source the source
	 * @param target the target
	 * @param eReference the type reference
	 */
	public CreateEdgeCommand(Graph graph, Node source, Node target,
			EReference eReference) {
		this.edge = HenshinFactory.eINSTANCE.createEdge();
		this.graph = graph;
		this.sourceNode = source;
		this.targetNode = target;
		this.typeReference = eReference;

		this.layout = NodeUtil.getLayoutSystem(source.getGraph()); 
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
			
			edgeLayout = EdgeUtil.getEdgeLayout(edge, layout);
			if (edgeLayout != null) {
				edgeLayout.setRhsedge(edge);
			}
			
			edge.setSource(sourceNode);
			edge.setTarget(targetNode);
		}
		edge.setGraph(graph);
		graph.getEdges().add(edge);
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
		if(edgeComplete() || (graph != null && targetNode != null && sourceNode != null)) {
			List<EReference> eReferences = EdgeReferences.getSourceToTargetFreeReferences(sourceNode, targetNode);
		
			List<Rule> ftrules = new ArrayList<Rule>();
			for (TRule ft : layout.getTRules()) {
				ftrules.add(ft.getRule());
			}
			
			return ftrules.contains(graph.getContainerRule()) ? graph.eContainer() instanceof NestedCondition : !eReferences.isEmpty();
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
