/**
 * CollapseNodeCommand.java
 * created on 14.07.2012 12:31:58
 */
package de.tub.tfs.henshin.tggeditor.commands.collapse;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.model.subtree.Subtree;
import de.tub.tfs.henshin.model.subtree.SubtreeFactory;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.EdgeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.GraphEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.SubtreeEdgeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.SubtreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.NodeObjectEditPart;
import de.tub.tfs.henshin.tggeditor.util.TGGCache;

/**
 * @author huuloi
 *
 */
public class CollapseNodeCommand extends CompoundCommand {
	
	private Node node;
	
	private Graph graph;
	
	private Subtree subtree;
	
	private GraphEditPart graphEditPart;
	
	private Map<?, ?> editPartRegistry;

	public CollapseNodeCommand(Node node, Map<?, ?> editPartRegistry) {
		this.node = node;
		this.graph = node.getGraph();
		
		this.editPartRegistry = editPartRegistry;
		graphEditPart = (GraphEditPart) editPartRegistry.get(graph);
		
		// create new subtrees node with new edges
		subtree = SubtreeFactory.eINSTANCE.createSubtree();
		subtree.setRoot(node);
		
		// create layout for subtree
		SubtreeEditPart subtreeEditPart = new SubtreeEditPart(subtree);
		subtreeEditPart.getLayoutModel().setX(node.getX());
		subtreeEditPart.getLayoutModel().setY(node.getY());
		graphEditPart.addChild(subtreeEditPart, 0);
		
		// remove all edgeEditParts
		removeAllEdgeEditParts(node);
		
		// remove all nodeEditParts
		removeAllNodeEditParts(node);
		
		// create new incoming edge from parent
		Node parent = getParent();
		add(new CreateSubtreeEdgeCommand(parent, subtree, getEdgeType(), editPartRegistry));
		
		// get all outgoing edges of nodes children
		Set<Edge> oldOutgoing = getOutgoingWithoutContainment(node); 
		
		// create new outgoing edges for subtree
		for (Edge edge : oldOutgoing) {
			Node targetNode = edge.getTarget();
			add(new CreateSubtreeEdgeCommand(subtree, targetNode, edge.getType(), editPartRegistry));
		}
		
		// get all subtreeOutgoing edges of node and its children
		Set<de.tub.tfs.henshin.model.subtree.Edge> oldSubtreeOutgoing = getSubtreeEdgeOutgoing(node);
		
		// create new outgoing edges for subtree
		for (de.tub.tfs.henshin.model.subtree.Edge edge : oldSubtreeOutgoing) {
			
			if (!edge.getType().isContainment()) {
				if (edge.getTarget() != null) {
					Subtree target = edge.getTarget();
					add(new CreateSubtreeEdgeCommand(subtree, target, edge.getType(), editPartRegistry));
				}
				else if (edge.getTargetNode() != null) {
					Node target = edge.getTargetNode();
					add(new CreateSubtreeEdgeCommand(subtree, target, edge.getType(), editPartRegistry));
				}
			}
		}
		
		// get all incoming edges of node and its children
		Set<Edge> oldIncoming = getIncomingWithoutContainment(node);
		
		// create new incoming edges for subtree
		for (Edge edge : oldIncoming) {
			Node sourceNode = edge.getSource();
			add(new CreateSubtreeEdgeCommand(sourceNode, subtree, edge.getType(), editPartRegistry));
		}
		
		// get all subtreeIncoming edges of node and its children
		Set<de.tub.tfs.henshin.model.subtree.Edge> oldSubtreeIncoming = getSubtreeEdgeIncoming(node);

		// create new incoming edges for subtree
		for (de.tub.tfs.henshin.model.subtree.Edge edge : oldSubtreeIncoming) {
			if (!edge.getType().isContainment()) {
				if (edge.getSource() != null) {
					Subtree source = edge.getSource();
					add(new CreateSubtreeEdgeCommand(source, subtree, edge.getType(), editPartRegistry));
				}
				else if (edge.getSourceNode() != null) {
					Node sourceNode = edge.getSourceNode();
					add(new CreateSubtreeEdgeCommand(sourceNode, subtree, edge.getType(), editPartRegistry));
				}
			}
		}
	}
	
	@Override
	public boolean canExecute() {
		return node != null /* && new ValidateGraphAction(null).validate(node.getGraph())*/;
	}
	
	private Set<de.tub.tfs.henshin.model.subtree.Edge> getSubtreeEdgeOutgoing(Node node) {
		Set<de.tub.tfs.henshin.model.subtree.Edge> edges = new HashSet<de.tub.tfs.henshin.model.subtree.Edge>();
		
		Set<de.tub.tfs.henshin.model.subtree.Edge> outgoings = TGGCache.getInstance().getOutgoingEdgeMap().get(node);
		if (outgoings != null && !outgoings.isEmpty()) {
			for (de.tub.tfs.henshin.model.subtree.Edge edge : outgoings) {
				edges.add(edge);
				if (edge.getType().isContainment()) {
					Subtree subtree = edge.getTarget();
					edges.addAll(subtree.getOutgoing());
				}
			}
			edges.addAll(outgoings);
		}
		
		EList<Edge> outgoing = node.getOutgoing();
		for (Edge edge : outgoing) {
			if (edge.getType().isContainment()) {
				Set<de.tub.tfs.henshin.model.subtree.Edge> outgoingList = TGGCache.getInstance().getOutgoingEdgeMap().get(edge.getTarget());
				if (outgoingList != null && !outgoingList.isEmpty()) {
					edges.addAll(outgoingList);
				}
				
			}
		}
		
		return edges;
	}
	
	private Set<de.tub.tfs.henshin.model.subtree.Edge> getSubtreeEdgeIncoming(Node node) {
		Set<de.tub.tfs.henshin.model.subtree.Edge> edges = new HashSet<de.tub.tfs.henshin.model.subtree.Edge>();
		
		Set<de.tub.tfs.henshin.model.subtree.Edge> incomings = TGGCache.getInstance().getIncomingEdgeMap().get(node);
		if (incomings != null && !incomings.isEmpty()) {
			edges.addAll(incomings);
		}
		
		
		EList<Edge> outgoing = node.getOutgoing();
		for (Edge edge : outgoing) {
			if (edge.getType().isContainment()) {
				edges.addAll(getSubtreeEdgeIncoming(edge.getTarget()));
			}
		}
		
		Set<de.tub.tfs.henshin.model.subtree.Edge> subtreeEdgeOutgoing = TGGCache.getInstance().getOutgoingEdgeMap().get(node);
		if (subtreeEdgeOutgoing != null) {
			for (de.tub.tfs.henshin.model.subtree.Edge edge : subtreeEdgeOutgoing) {
				if (edge.getType().isContainment()) {
					edges.addAll(TGGCache.getInstance().getIncomingSubtreeEdgeMap().get(edge.getTarget()));
				}
			}
		}
		
		return edges;
	}
	
	private Set<Edge> getOutgoingWithoutContainment(Node node) {
		Set<Edge> edges = new HashSet<Edge>();
		
		EList<Edge> outgoing = node.getOutgoing();
		for (Edge edge : outgoing) {
			if (!edge.getType().isContainment() && !TGGCache.getInstance().getCollapsedEdges().contains(edge)) {
				TGGCache.getInstance().getCollapsedEdges().add(edge);
				edges.add(edge);
			}
			else {
				edges.addAll(getOutgoingWithoutContainment(edge.getTarget()));
			}
		}
		
		return edges;
	}
	
	private Set<Edge> getIncomingWithoutContainment(Node node) {
		Set<Edge> edges = new HashSet<Edge>();
		
		EList<Edge> incoming = node.getIncoming();
		for (Edge edge : incoming) {
			if (!edge.getType().isContainment() && !TGGCache.getInstance().getCollapsedEdges().contains(edge)) {
				TGGCache.getInstance().getCollapsedEdges().add(edge);
				edges.add(edge);
			}
		}

		EList<Edge> outgoing = node.getOutgoing();
		for (Edge edge : outgoing) {
			if (edge.getType().isContainment()) {
				edges.addAll(getIncomingWithoutContainment(edge.getTarget()));
			}
		}
		
		return edges;
	}
	
	private void removeAllEdgeEditParts(Node node) {
		NodeObjectEditPart nodeEditPart = (NodeObjectEditPart) editPartRegistry.get(node);

		// remove all incoming edges
		EList<Edge> incoming = node.getIncoming();
		for (Edge edge : incoming) {
			EdgeEditPart edgeEditPart = (EdgeEditPart) editPartRegistry.get(edge);
			if (edgeEditPart != null) {
				nodeEditPart.getTargetConnections().remove(edgeEditPart);
				edgeEditPart.removeNotify();
			}
		}
		
		// remove all incoming subtreeEdges
		Set<de.tub.tfs.henshin.model.subtree.Edge> subtreeEdgeIncoming = TGGCache.getInstance().getIncomingEdgeMap().get(node);
		if (subtreeEdgeIncoming != null) {
			for (de.tub.tfs.henshin.model.subtree.Edge edge : subtreeEdgeIncoming) {
				SubtreeEdgeEditPart subtreeEdgeEditPart = (SubtreeEdgeEditPart) editPartRegistry.get(edge);
				if (subtreeEdgeEditPart != null) {
					nodeEditPart.getTargetConnections().remove(subtreeEdgeEditPart);
					subtreeEdgeEditPart.removeNotify();
				}
			}
			
		}
		
		// remove all outgoing edges
		EList<Edge> outgoing = node.getOutgoing();
		for (Edge edge : outgoing) {
			EdgeEditPart edgeEditPart = (EdgeEditPart) editPartRegistry.get(edge);
			if (edgeEditPart != null) {
				nodeEditPart.getSourceConnections().remove(edgeEditPart);
				edgeEditPart.removeNotify();
			}
			
			// call recursive by children 
			if (edge.getType().isContainment()) {
				removeAllEdgeEditParts(edge.getTarget());
			}
		}
		
		// remove all outgoing subtreeEdges
		Set<de.tub.tfs.henshin.model.subtree.Edge> subtreeEdgeOutgoing = TGGCache.getInstance().getOutgoingEdgeMap().get(node);
		if (subtreeEdgeOutgoing != null) {
			for (de.tub.tfs.henshin.model.subtree.Edge edge : subtreeEdgeOutgoing) {
				SubtreeEdgeEditPart subtreeEdgeEditPart = (SubtreeEdgeEditPart) editPartRegistry.get(edge);
				if (subtreeEdgeEditPart != null) {
					nodeEditPart.getSourceConnections().remove(subtreeEdgeEditPart);
					subtreeEdgeEditPart.removeNotify();
				}
				
				// call recursive by children
				if (edge.getType().isContainment()) {
					removeAllEdgeEditParts(edge.getTarget());
				}
			}
		}
	}
	
	private void removeAllEdgeEditParts(Subtree subtree) {
		SubtreeEditPart subtreeEditPart = (SubtreeEditPart) editPartRegistry.get(subtree);
		
		EList<de.tub.tfs.henshin.model.subtree.Edge> incoming = subtree.getIncoming();
		for (de.tub.tfs.henshin.model.subtree.Edge edge : incoming) {
			SubtreeEdgeEditPart subtreeEdgeEditPart = (SubtreeEdgeEditPart) editPartRegistry.get(edge);
			if (subtreeEdgeEditPart != null) {
				subtreeEditPart.getTargetConnections().remove(subtreeEdgeEditPart);
				subtreeEdgeEditPart.removeNotify();
			}
		}
		
		EList<de.tub.tfs.henshin.model.subtree.Edge> outgoing = subtree.getOutgoing();
		for (de.tub.tfs.henshin.model.subtree.Edge edge : outgoing) {
			SubtreeEdgeEditPart subtreeEdgeEditPart = (SubtreeEdgeEditPart) editPartRegistry.get(edge);
			if (subtreeEdgeEditPart != null) {
				subtreeEditPart.getSourceConnections().remove(subtreeEdgeEditPart);
				subtreeEdgeEditPart.removeNotify();
			}
		}
		
	}
	
	private void removeAllNodeEditParts(Node node) {
		NodeObjectEditPart nodeEditPart = (NodeObjectEditPart) editPartRegistry.get(node);
		
		if (nodeEditPart != null) {
			graphEditPart.removeChild(nodeEditPart);
			TGGCache.getInstance().getRemovedEditParts().add(node);
		}
		
		EList<Edge> outgoing = node.getOutgoing();
		for (Edge edge : outgoing) {
			if (edge.getType().isContainment()) {
				removeAllNodeEditParts(edge.getTarget());
			}
		}
		
		Set<de.tub.tfs.henshin.model.subtree.Edge> subtreeEdges = TGGCache.getInstance().getOutgoingEdgeMap().get(node);
		if (subtreeEdges != null) {
			for (de.tub.tfs.henshin.model.subtree.Edge edge : subtreeEdges) {
				if (edge.getType().isContainment()) {
					Subtree subtree = edge.getTarget();
					graphEditPart.removeChild((SubtreeEditPart) editPartRegistry.get(subtree));
				}
			}
		}
	}
	
	private Node getParent() {
		if (node != null) {
			EList<Edge> incoming = node.getIncoming();
			for (Edge edge : incoming) {
				if (edge.getType().isContainment()) {
					return edge.getSource();
				}
			}
		}
		
		return null;
	}
	
	private EReference getEdgeType() {
		if (node != null) {
			EList<Edge> incoming = node.getIncoming();
			for (Edge edge : incoming) {
				if (edge.getType().isContainment()) {
					return edge.getType();
				}
			}
		}
		
		return null;
	}

}
