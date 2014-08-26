/**
 * CreateSubtreeEdgeCommand.java
 * created on 14.07.2012 14:08:06
 */
package de.tub.tfs.henshin.tggeditor.commands.collapse;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.model.subtree.Edge;
import de.tub.tfs.henshin.model.subtree.Subtree;
import de.tub.tfs.henshin.model.subtree.SubtreeFactory;
import de.tub.tfs.henshin.model.subtree.SubtreePackage;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.SubtreeEdgeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.SubtreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.NodeObjectEditPart;
import de.tub.tfs.henshin.tggeditor.util.TGGCache;

/**
 * @author huuloi
 *
 */
public class CreateSubtreeEdgeCommand extends CompoundCommand {

	private Node sourceNode;
	
	private Node targetNode;
	
	private Subtree source;
	
	private Subtree target;
	
	private Edge edge;
	

	public CreateSubtreeEdgeCommand(Subtree source, Node targetNode, EReference type, Map<?, ?> editPartRegistry) {
		edge = SubtreeFactory.eINSTANCE.createEdge();
		edge.setType(type);
		this.source = source;
		this.targetNode = targetNode;
		
		
		Set<Edge> incomingEdgeMap = TGGCache.getInstance().getIncomingEdgeMap().get(targetNode);
		if (incomingEdgeMap == null) {
			incomingEdgeMap = new HashSet<Edge>();
			TGGCache.getInstance().getIncomingEdgeMap().put(targetNode, incomingEdgeMap);
		}
		incomingEdgeMap.add(edge);
		
		Set<Edge> incomingSubtreeEdgeMap = TGGCache.getInstance().getOutgoingSubtreeEdgeMap().get(source);
		if (incomingSubtreeEdgeMap == null) {
			incomingSubtreeEdgeMap = new HashSet<Edge>();
			TGGCache.getInstance().getOutgoingSubtreeEdgeMap().put(source, incomingSubtreeEdgeMap);
		}
		incomingSubtreeEdgeMap.add(edge);
		
		add(new SimpleSetEFeatureCommand<Edge, Subtree>(edge, source, SubtreePackage.EDGE__SOURCE));
		add(new SimpleSetEFeatureCommand<Edge, Node>(edge, targetNode, SubtreePackage.EDGE__TARGET_NODE));
		
		SubtreeEdgeEditPart subtreeEdgeEditPart = new SubtreeEdgeEditPart(edge);
		PolylineConnection figure = (PolylineConnection) subtreeEdgeEditPart.getFigure();
		
		SubtreeEditPart sourceEditPart = (SubtreeEditPart) editPartRegistry.get(source);
		figure.setSourceAnchor(new ChopboxAnchor(sourceEditPart.getFigure()));
		
		subtreeEdgeEditPart.setSource(sourceEditPart);
		
		NodeObjectEditPart targetEditPart = (NodeObjectEditPart) editPartRegistry.get(targetNode);
		figure.setTargetAnchor(new ChopboxAnchor(targetEditPart.getFigure()));
		
		subtreeEdgeEditPart.setTarget(targetEditPart);
	}
	

	@SuppressWarnings("unchecked")
	public CreateSubtreeEdgeCommand(Node sourceNode, Subtree target, EReference type, Map<?, ?> editPartRegistry) {
		edge = SubtreeFactory.eINSTANCE.createEdge();
		edge.setType(type);
		this.sourceNode = sourceNode;
		this.target = target;
		
		Set<Edge> outgoingEdgeMap = TGGCache.getInstance().getOutgoingEdgeMap().get(sourceNode);
		if (outgoingEdgeMap == null) {
			outgoingEdgeMap = new HashSet<Edge>();
			TGGCache.getInstance().getOutgoingEdgeMap().put(sourceNode, outgoingEdgeMap);
		}
		outgoingEdgeMap.add(edge);
		
		Set<Edge> incomingSubtreeMap = TGGCache.getInstance().getIncomingSubtreeEdgeMap().get(target);
		if (incomingSubtreeMap == null) {
			incomingSubtreeMap = new HashSet<Edge>();
			TGGCache.getInstance().getIncomingSubtreeEdgeMap().put(target, incomingSubtreeMap);
		}
		incomingSubtreeMap.add(edge);
		
		add(new SimpleSetEFeatureCommand<Edge, Node>(edge, sourceNode, SubtreePackage.EDGE__SOURCE_NODE));
		add(new SimpleSetEFeatureCommand<Edge, Subtree>(edge, target, SubtreePackage.EDGE__TARGET));
		
		SubtreeEdgeEditPart subtreeEdgeEditPart = new SubtreeEdgeEditPart(edge);
		PolylineConnection figure = (PolylineConnection) subtreeEdgeEditPart.getFigure();
		
		NodeObjectEditPart sourceEditPart = (NodeObjectEditPart) editPartRegistry.get(sourceNode);
		figure.setSourceAnchor(new ChopboxAnchor(sourceEditPart.getFigure()));
		List<EditPart> sourceConnections = sourceEditPart.getSourceConnections();
		sourceConnections.add(subtreeEdgeEditPart);
		subtreeEdgeEditPart.setSource(sourceEditPart);
		
		SubtreeEditPart targetEditPart = (SubtreeEditPart) editPartRegistry.get(target);
		figure.setTargetAnchor(new ChopboxAnchor(targetEditPart.getFigure()));
		
		subtreeEdgeEditPart.setTarget(targetEditPart);
	}
	

	public CreateSubtreeEdgeCommand(Subtree source, Subtree target, EReference type, Map<?, ?> editPartRegistry) {
		edge = SubtreeFactory.eINSTANCE.createEdge();
		edge.setType(type);
		this.source = source;
		this.target = target;
		
		add(new SimpleSetEFeatureCommand<Edge, Subtree>(edge, source, SubtreePackage.EDGE__SOURCE));
		add(new SimpleSetEFeatureCommand<Edge, Subtree>(edge, target, SubtreePackage.EDGE__TARGET));
		
		SubtreeEdgeEditPart subtreeEdgeEditPart = new SubtreeEdgeEditPart(edge);
		PolylineConnection figure = (PolylineConnection) subtreeEdgeEditPart.getFigure();
		
		SubtreeEditPart sourceEditPart = (SubtreeEditPart) editPartRegistry.get(source);
		figure.setSourceAnchor(new ChopboxAnchor(sourceEditPart.getFigure()));
		
		subtreeEdgeEditPart.setSource(sourceEditPart);
		
		SubtreeEditPart targetEditPart = (SubtreeEditPart) editPartRegistry.get(target);
		figure.setTargetAnchor(new ChopboxAnchor(targetEditPart.getFigure()));
		
		subtreeEdgeEditPart.setTarget(targetEditPart);
	}
	
	
	@Override
	public boolean canExecute() {
		return (source != null && targetNode != null) ||
			   (sourceNode != null && target != null) ||
			   (source != null && target != null);
	}
	

	public Node getSourceNode() {
		return sourceNode;
	}

	
	public void setSourceNode(Node sourceNode) {
		this.sourceNode = sourceNode;
	}

	
	public Node getTargetNode() {
		return targetNode;
	}

	
	public void setTargetNode(Node targetNode) {
		this.targetNode = targetNode;
	}

	
	public Subtree getSource() {
		return source;
	}

	
	public void setSource(Subtree source) {
		this.source = source;
	}

	
	public Subtree getTarget() {
		return target;
	}

	
	public void setTarget(Subtree target) {
		this.target = target;
	}

	
	public Edge getEdge() {
		return edge;
	}

	
	public void setEdge(Edge edge) {
		this.edge = edge;
	}

}
