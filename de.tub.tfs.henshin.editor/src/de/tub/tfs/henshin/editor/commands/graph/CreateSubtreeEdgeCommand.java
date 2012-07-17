/**
 * CreateSubtreeEdgeCommand.java
 * created on 14.07.2012 14:08:06
 */
package de.tub.tfs.henshin.editor.commands.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.SimpleSetEFeatureCommand;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.NodeEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.SubtreeEdgeEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.SubtreeEditPart;
import de.tub.tfs.henshin.editor.util.HenshinCache;
import de.tub.tfs.henshin.editor.util.HenshinSelectionUtil;
import de.tub.tfs.henshin.model.subtree.Edge;
import de.tub.tfs.henshin.model.subtree.Subtree;
import de.tub.tfs.henshin.model.subtree.SubtreeFactory;
import de.tub.tfs.henshin.model.subtree.SubtreePackage;

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

	public CreateSubtreeEdgeCommand(Subtree source, Node targetNode, EReference type) {
		edge = SubtreeFactory.eINSTANCE.createEdge();
		edge.setType(type);
		this.source = source;
		this.targetNode = targetNode;
		
		
		List<Edge> edgeList = HenshinCache.getInstance().getIncomingEdgeMap().get(targetNode);
		if (edgeList == null) {
			edgeList = new ArrayList<Edge>();
			HenshinCache.getInstance().getIncomingEdgeMap().put(targetNode, edgeList);
		}
		edgeList.add(edge);
		
		add(new SimpleSetEFeatureCommand<Edge, Subtree>(edge, source, SubtreePackage.EDGE__SOURCE));
		add(new SimpleSetEFeatureCommand<Edge, Node>(edge, targetNode, SubtreePackage.EDGE__TARGET_NODE));
		
		SubtreeEdgeEditPart subtreeEdgeEditPart = new SubtreeEdgeEditPart(edge);
		PolylineConnection figure = (PolylineConnection) subtreeEdgeEditPart.getFigure();
		
		Map<?, ?> editPartRegistry = HenshinSelectionUtil.getInstance().getEditPartRegistry(targetNode.getGraph());
		
		SubtreeEditPart sourceEditPart = (SubtreeEditPart) editPartRegistry.get(source);
		figure.setSourceAnchor(new ChopboxAnchor(sourceEditPart.getFigure()));
		
		subtreeEdgeEditPart.setSource(sourceEditPart);
		
		NodeEditPart targetEditPart = (NodeEditPart) editPartRegistry.get(targetNode);
		figure.setTargetAnchor(new ChopboxAnchor(targetEditPart.getFigure()));
		
		subtreeEdgeEditPart.setTarget(targetEditPart);
	}

	@SuppressWarnings("unchecked")
	public CreateSubtreeEdgeCommand(Node sourceNode, Subtree target, EReference type) {
		edge = SubtreeFactory.eINSTANCE.createEdge();
		edge.setType(type);
		this.sourceNode = sourceNode;
		this.target = target;
		
		List<Edge> edgeList = HenshinCache.getInstance().getOutgoingEdgeMap().get(sourceNode);
		if (edgeList == null) {
			edgeList = new ArrayList<Edge>();
			HenshinCache.getInstance().getOutgoingEdgeMap().put(sourceNode, edgeList);
		}
		edgeList.add(edge);
		
		add(new SimpleSetEFeatureCommand<Edge, Node>(edge, sourceNode, SubtreePackage.EDGE__SOURCE_NODE));
		add(new SimpleSetEFeatureCommand<Edge, Subtree>(edge, target, SubtreePackage.EDGE__TARGET));
		
		SubtreeEdgeEditPart subtreeEdgeEditPart = new SubtreeEdgeEditPart(edge);
		PolylineConnection figure = (PolylineConnection) subtreeEdgeEditPart.getFigure();
		
		Map<?, ?> editPartRegistry = HenshinSelectionUtil.getInstance().getEditPartRegistry(sourceNode.getGraph());
		
		NodeEditPart sourceEditPart = (NodeEditPart) editPartRegistry.get(sourceNode);
		figure.setSourceAnchor(new ChopboxAnchor(sourceEditPart.getFigure()));
		List<EditPart> sourceConnections = sourceEditPart.getSourceConnections();
		sourceConnections.add(subtreeEdgeEditPart);
		subtreeEdgeEditPart.setSource(sourceEditPart);
		
		SubtreeEditPart targetEditPart = (SubtreeEditPart) editPartRegistry.get(target);
		figure.setTargetAnchor(new ChopboxAnchor(targetEditPart.getFigure()));
		
		subtreeEdgeEditPart.setTarget(targetEditPart);
	}

	public CreateSubtreeEdgeCommand(Subtree source, Subtree target, EReference type) {
		edge = SubtreeFactory.eINSTANCE.createEdge();
		edge.setType(type);
		this.source = source;
		this.target = target;
		
		add(new SimpleSetEFeatureCommand<Edge, Subtree>(edge, source, SubtreePackage.EDGE__SOURCE));
		add(new SimpleSetEFeatureCommand<Edge, Subtree>(edge, target, SubtreePackage.EDGE__TARGET));
		
		SubtreeEdgeEditPart subtreeEdgeEditPart = new SubtreeEdgeEditPart(edge);
		PolylineConnection figure = (PolylineConnection) subtreeEdgeEditPart.getFigure();
		
		Map<?, ?> editPartRegistry = HenshinSelectionUtil.getInstance().getEditPartRegistry(source.getRoot().getGraph());
		
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
