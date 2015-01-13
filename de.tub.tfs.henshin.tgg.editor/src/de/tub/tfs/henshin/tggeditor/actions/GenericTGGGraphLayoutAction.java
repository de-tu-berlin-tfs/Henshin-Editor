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
package de.tub.tfs.henshin.tggeditor.actions;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.graph.CompoundDirectedGraph;
import org.eclipse.draw2d.graph.CompoundDirectedGraphLayout;
import org.eclipse.draw2d.graph.DirectedGraph;
import org.eclipse.draw2d.graph.DirectedGraphLayout;
import org.eclipse.draw2d.graph.Edge;
import org.eclipse.draw2d.graph.Node;
import org.eclipse.draw2d.graph.Subgraph;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.commands.move.MoveDividerCommand;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.Divider;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.GraphEditPart;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;
import de.tub.tfs.muvitor.ui.MuvitorActivator;
import de.tub.tfs.muvitor.ui.MuvitorConstants;

/**
 * This action applies the Draw2d graph layouter to the EditPartViewer
 * containing some selected GraphicalEditPart.
 * 
 * @author "Tony Modica"
 */
public class GenericTGGGraphLayoutAction extends SelectionAction {
	
	public static final String ID = "GenericTGGTreeGraphLayoutAction";
	
	/** Default padding for nodes in the graph (fixed spacing around nodes). */
	private static final int DEFAULT_PADDING = 25;
	
	private static final String DESC = "Automatic TGG Tree layout";
	
	private static final String LABEL = "Redistribute nodes (Containment Trees)";
	
	/**
	 * The viewer containing the currently selected GraphicalEditPart
	 */
	private EditPartViewer viewer;
	
	
	/**
	 * The graphEditpart containing the currently selected GraphicalEditPart
	 */
	private GraphEditPart graphEditPart = null;
	
	/**
	 * @param part
	 *            the workbench part
	 */
	public GenericTGGGraphLayoutAction(final IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText(LABEL);
		setDescription(DESC);
		setToolTipText(DESC);
		setImageDescriptor(MuvitorActivator
				.getImageDescriptor(MuvitorConstants.ICON_GRAPHLAYOUT_16));
	}
	
	/**
	 * Gets the Graph Layout Command and executes it.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		// when invoking directly, a viewer must be set manually!
		if (viewer == null) {
			return;
		}
		
		if(viewer.getContents() instanceof GraphEditPart)
			graphEditPart=(GraphEditPart) viewer.getContents();
		
		// compute layout graph
		CompoundDirectedGraph srcGraph = new CompoundDirectedGraph();
		DirectedGraph corGraph = new DirectedGraph();
		DirectedGraph tarGraph = new DirectedGraph();
		
		srcGraph.setDefaultPadding(new Insets(DEFAULT_PADDING));
		corGraph.setDefaultPadding(new Insets(DEFAULT_PADDING));
		tarGraph.setDefaultPadding(new Insets(DEFAULT_PADDING));
		
		// list to store the connection edit parts found between the nodes
		final Set<ConnectionEditPart> srcConn = new HashSet<ConnectionEditPart>();
		final Set<ConnectionEditPart> corConn = new HashSet<ConnectionEditPart>();
		final Set<ConnectionEditPart> tarConn = new HashSet<ConnectionEditPart>();
		final Set<ConnectionEditPart> otherConn = new HashSet<ConnectionEditPart>();
		
		
		// a map to get the right source and target nodes for the edges in the
		// final graph
		final Map<NodeEditPart, Node> nodeEditPartToNodeMap = new HashMap<NodeEditPart, Node>();
		
		// list of all nodeEditParts within selection, contains all nodes if graph is selected
		List<EditPart> list = Collections.EMPTY_LIST;

		if (!getSelectedObjects().isEmpty()){
			if (getSelectedObjects().size() == 1 && getSelectedObjects().get(0) instanceof GraphEditPart)
				list = viewer.getContents().getChildren();
			else 
				list = getSelectedObjects();
		}
			
	
		for (final EditPart editPart : (Collection<EditPart>)list ) {
			if (editPart instanceof NodeEditPart) {
				final NodeEditPart nodeEditPart = (NodeEditPart) editPart;
				final Rectangle bounds = ((GraphicalEditPart) editPart).getFigure().getBounds();
				// ignore figures without bounds
				if (bounds == null) {
					continue;
				}
				final Node node = new Node(nodeEditPart,null);
				node.x = 0;
				node.y = bounds.y;
				node.height = bounds.height;
				node.width = bounds.width;
				nodeEditPartToNodeMap.put(nodeEditPart, node);
				if (NodeUtil.isSourceNode((TNode) nodeEditPart.getModel())){
					srcGraph.nodes.add(node);
					for (Object obj : nodeEditPart.getSourceConnections()) {
						ConnectionEditPart conn = (ConnectionEditPart) obj;
						if (NodeUtil.isSourceNode((TNode) conn.getTarget().getModel())){
							srcConn.add(conn);
						} else {
							otherConn.add(conn);
						}
						
					}
					for (Object obj : nodeEditPart.getTargetConnections()) {
						ConnectionEditPart conn = (ConnectionEditPart) obj;
						if (NodeUtil.isSourceNode((TNode) conn.getSource().getModel())){
							srcConn.add(conn);
						} else {
							otherConn.add(conn);
						}
						
					}
					
				} else if (NodeUtil.isCorrespondenceNode((TNode) nodeEditPart.getModel())){
					corGraph.nodes.add(node);
					for (Object obj : nodeEditPart.getSourceConnections()) {
						ConnectionEditPart conn = (ConnectionEditPart) obj;
						if (NodeUtil.isCorrespondenceNode((TNode) conn.getTarget().getModel())){
							corConn.add(conn);
							
						} else {
							otherConn.add(conn);
						}
						
						
					}
					for (Object obj : nodeEditPart.getTargetConnections()) {
						ConnectionEditPart conn = (ConnectionEditPart) obj;
						if (NodeUtil.isCorrespondenceNode((TNode) conn.getSource().getModel())){
							corConn.add(conn);
						} else {
							otherConn.add(conn);
						}
						
					}
				} else if (NodeUtil.isTargetNode((TNode) nodeEditPart.getModel())){
					tarGraph.nodes.add(node);
					for (Object obj : nodeEditPart.getSourceConnections()) {
						ConnectionEditPart conn = (ConnectionEditPart) obj;
						if (NodeUtil.isTargetNode((TNode) conn.getTarget().getModel())){
							tarConn.add(conn);
						} else {
							otherConn.add(conn);
						}
						
					}
					for (Object obj : nodeEditPart.getTargetConnections()) {
						ConnectionEditPart conn = (ConnectionEditPart) obj;
						if (NodeUtil.isTargetNode((TNode) conn.getSource().getModel())){
							tarConn.add(conn);
						} else {
							otherConn.add(conn);
						}
						
					}
				}
				
			}
		}
		/*for (ConnectionEditPart connectionEditPart : otherConn) {
			final Node snode = new Node(connectionEditPart.getSource());
			snode.x = 0;
			final Rectangle sbounds = ((GraphicalEditPart) connectionEditPart.getSource()).getFigure().getBounds();
			// ignore figures without bounds
			if (sbounds == null) {
				continue;
			}
			snode.y = sbounds.y;
			snode.height = sbounds.height;
			snode.width = sbounds.width;
			
			final Node tnode = new Node(connectionEditPart.getTarget());
			tnode.x = 0;
			final Rectangle tbounds = ((GraphicalEditPart) connectionEditPart.getTarget()).getFigure().getBounds();
			// ignore figures without bounds
			if (tbounds == null) {
				continue;
			}
			tnode.y = tbounds.y;
			tnode.height = tbounds.height;
			tnode.width = tbounds.width;
			
			nodeEditPartToNodeMap.put((NodeEditPart) connectionEditPart.getSource(),snode);
			nodeEditPartToNodeMap.put((NodeEditPart) connectionEditPart.getTarget(),tnode);
		}*/
		// Convert connections to (Draw2d) Edges
		for (final ConnectionEditPart connection : srcConn) {
			Node sourceNode=nodeEditPartToNodeMap.get(connection.getSource());
			Node targetNode=nodeEditPartToNodeMap.get(connection.getTarget());
			
			if( // store only containment edges for layouting the tree structure 
					(((org.eclipse.emf.henshin.model.Edge)connection.getModel()).getType().isContainment())
					&&
					// source and target of edge are in selection
					sourceNode != null && targetNode != null
					&&
					// Graphs must not contain unresolvable cycles
					(connection.getSource() != connection.getTarget())   ) {
				srcGraph.edges.add(new Edge(connection, sourceNode, targetNode));
			}
		}
		
		// Convert connections to (Draw2d) Edges
		for (final ConnectionEditPart connection : tarConn) {
						
			if( // store only containment edges for layouting the tree structure 
					(((org.eclipse.emf.henshin.model.Edge)connection.getModel()).getType().isContainment())
				&&
				// Graphs must not contain unresolvable cycles
			(connection.getSource() != connection.getTarget())   ) {
				tarGraph.edges.add(new Edge(connection, nodeEditPartToNodeMap.get(connection
						.getSource()), nodeEditPartToNodeMap.get(connection.getTarget())));
			}
		}
		
		// Convert connections to (Draw2d) Edges
		for (final ConnectionEditPart connection : corConn) {
			
			if( // store only containment edges for layouting the tree structure 
					(((org.eclipse.emf.henshin.model.Edge)connection.getModel()).getType().isContainment())
				&&
				// Graphs must not contain unresolvable cycles
			(connection.getSource() != connection.getTarget())   ) {
				corGraph.edges.add(new Edge(connection, nodeEditPartToNodeMap.get(connection
						.getSource()), nodeEditPartToNodeMap.get(connection.getTarget())));
			}
		}
		
		// split the graph into its unconnected subgraphs
		addComponentSubgraphs(srcGraph);
		// perform layout
		new CompoundDirectedGraphLayout().visit(srcGraph);
		// int sourceWidth=srcGraph.getLayoutSize().width();
		// workaround, as srcGraph.getLayoutSize().width() delivers 0
		int sourceWidth=computeWidth(srcGraph);		
		new DirectedGraphLayout().visit(tarGraph);
		new DirectedGraphLayout().visit(corGraph);

		
		
		// combine commands that will apply the new node location values
		final CompoundCommand compCommand = new CompoundCommand();
		
		
		// move dividers to maximum of source and correspondence width
		if(graphEditPart!=null){
			

			final TripleGraph graph = graphEditPart.getCastedModel();
			final int newXDivSC = Math.max(sourceWidth,computeSrcWidth(graphEditPart.getCastedModel()));

			// correspondence component has at least a width of 40
			final int corWidth = Math.max(corGraph.getLayoutSize().width(), computeCorWidth(graphEditPart.getCastedModel()));
			final int newXDivCT = newXDivSC +  corWidth;
			
			final Divider divSc = graphEditPart.getDividerSCpart().getCastedModel();
			final Divider divCt = graphEditPart.getDividerCTpart().getCastedModel();
			
			// move first the divider between C and T to provide space for the move of divider between S and C
			boolean dividerCTwasMoved = false;
			MoveDividerCommand cmdCT = null;
			if (graph.getDividerCT_X() != newXDivCT) {
				cmdCT = new MoveDividerCommand(divCt,
						newXDivCT, graph.getDividerMaxY());
				if (cmdCT != null && cmdCT.canExecute()) {
					cmdCT.execute();
					dividerCTwasMoved = true;
				}
			}
			if (graph.getDividerSC_X() != newXDivSC) {
				MoveDividerCommand cmdSC = new MoveDividerCommand(divSc,
						newXDivSC, graph.getDividerMaxY());
				if (cmdSC != null && cmdSC.canExecute()) {
					cmdSC.execute();
				}
			}
			// if move divider CT command was delayed by move divider SC command (moving to left), then execute the command
			if (cmdCT != null && !dividerCTwasMoved && cmdCT.canExecute()) {
				cmdCT.execute();
				dividerCTwasMoved = true;
			}
		}
		
		
		
		for (final Entry<NodeEditPart, Node> entry : nodeEditPartToNodeMap.entrySet()) {
			final NodeEditPart editPart = entry.getKey();
			final Rectangle bounds = editPart.getFigure().getBounds();
			final Node node = entry.getValue();
			final ChangeBoundsRequest request = new ChangeBoundsRequest(RequestConstants.REQ_MOVE);
			
			int sourceY = 0;
			int targetY = 0;
			int deltaY = node.y;
			int deltaX = 0;
			
			if (NodeUtil.isCorrespondenceNode((TNode) editPart.getModel())){
				deltaX = ((TripleGraph)((TNode) editPart.getModel()).getGraph()).getDividerSC_X() + 10;
				int sAmt = 0;
				int tAmt = 0;
				for (Object obj : editPart.getSourceConnections()) {
					ConnectionEditPart e = (ConnectionEditPart) obj;
					if (NodeUtil.isTargetNode((TNode) e.getTarget().getModel())){
						tAmt++;
						if (nodeEditPartToNodeMap.get(e.getTarget()) != null)
							targetY = (int) (nodeEditPartToNodeMap.get(e.getTarget()).y );
					} else if (NodeUtil.isSourceNode((TNode) e.getTarget().getModel())){
						sAmt++;
						if (nodeEditPartToNodeMap.get(e.getTarget()) != null)
							sourceY = (int) (nodeEditPartToNodeMap.get(e.getTarget()).y );
					}
				}

				for (Object obj : editPart.getTargetConnections()) {
					ConnectionEditPart e = (ConnectionEditPart) obj;
					if (NodeUtil.isTargetNode((TNode) e.getSource().getModel())){
						tAmt++;
						if (nodeEditPartToNodeMap.get(e.getSource()) != null)
							targetY = (int) (nodeEditPartToNodeMap.get(e.getSource()).y);
					} else if (NodeUtil.isSourceNode((TNode) e.getSource().getModel())){
						sAmt++;
						if (nodeEditPartToNodeMap.get(e.getSource()) != null)
							sourceY = (int) (nodeEditPartToNodeMap.get(e.getSource()).y);
					}
				}
				
				
				if (sAmt + tAmt != 0)
					deltaY = (int) (Math.abs(sourceY + targetY) / (sAmt + tAmt));
				else
					deltaY = Math.abs(sourceY + targetY) ;
			} else if (NodeUtil.isTargetNode((TNode) editPart.getModel())){
				deltaX = ((TripleGraph)((TNode) editPart.getModel()).getGraph()).getDividerCT_X() + 10;
			} 
				
			
			request.setMoveDelta(new Point(node.x + deltaX - bounds.x, deltaY - bounds.y));
			
			final Command command = editPart.getCommand(request);
			// Some edit parts may return unexecutable commands
			if (command != null && command.canExecute()) {
				compCommand.add(editPart.getCommand(request));
			}
		}
		
		
		// this allows to use this action independently from an editor
		if (getWorkbenchPart() == null || getCommandStack() == null) {
			compCommand.execute();
		} else {
			execute(compCommand);
		}
		
	}
	private int computeSrcWidth(TripleGraph castedModel) {
		if (castedModel == null)
			return 200;
		return castedModel.getDividerSC_X();
	}
	private int computeCorWidth(TripleGraph castedModel) {
		if (castedModel == null)
			return 40;
		return castedModel.getDividerCT_X() - castedModel.getDividerSC_X();
	}
	private int computeTarWidth(TripleGraph castedModel) {
		// TODO Auto-generated method stub
		return castedModel.getDividerCT_X();
	}

	
	private int computeWidth(CompoundDirectedGraph srcGraph) {
		Node n =null;
		int maxX=0;
		for (Object o:srcGraph.nodes){
			if (o instanceof Node){
				n = (Node) o;
				maxX = Math.max(n.width+n.x,maxX);
			}
		}
		return maxX;
	}


	private HashSet<Node> visitedNodes = new HashSet<Node>();
	private void addComponentSubgraphs(CompoundDirectedGraph srcGraph) {

		Subgraph currentComponentNode=null; 
		Subgraph previousComponentNode=null;
		int amountPlainNodes = srcGraph.nodes.size();
		//Subgraph previousComponentNode = null;
		// iterate over all nodes and create a new component for each connected component
		for(int currentNodePosition = 0; currentNodePosition < amountPlainNodes;currentNodePosition++){
			Node currentNode=srcGraph.nodes.getNode(currentNodePosition);
			if(!visitedNodes.contains(currentNode)){
				previousComponentNode = currentComponentNode;
				currentComponentNode = new Subgraph("component");
				srcGraph.nodes.add(currentComponentNode);
				if(previousComponentNode!=null)
					srcGraph.edges.add(new Edge("componentEdge",previousComponentNode,currentComponentNode));
				visitAndAddToComponent(currentNode,currentComponentNode);
			}
		}

		
	}

	
	private void visitAndAddToComponent(Node currentNode, Subgraph componentNode) {
		if(!visitedNodes.contains(currentNode)){
			// process current node, if not processed before
			// node is put into the current subgraph
			componentNode.addMember(currentNode);
			currentNode.setParent(componentNode);
			visitedNodes.add(currentNode);
			
			// process all nodes from incoming edges
			Iterator<?> edgesIterator = currentNode.incoming.iterator();
			while (edgesIterator.hasNext()){
				Object o = edgesIterator.next();
				if (o instanceof Edge){
					Edge e = (Edge) o;
						visitAndAddToComponent(e.source,componentNode);
				}
			};
			
			// process all nodes from outgoing edges
			edgesIterator = currentNode.outgoing.iterator();
			while (edgesIterator.hasNext()){
				Object o = edgesIterator.next();
				if (o instanceof Edge){
					Edge e = (Edge) o;
					visitAndAddToComponent(e.target,componentNode);
				}
		
			}
		}
	}

	/**
	 * This setter allows universal usage of this action. Just call the
	 * constructor with <code>null</code> and set the viewer for layout
	 * manually.
	 * 
	 * @param viewer
	 */
	public void setViewer(final EditPartViewer viewer) {
		this.viewer = viewer;
	}
	
	/**
	 * This action is enabled if some graphical edit part is currently selected
	 * from which a viewer can be determined to be layout.
	 */
	@Override
	protected boolean calculateEnabled() {
		viewer = null;
		if (getSelection() == null) {
			return false;
		}
		if (getSelection() instanceof IStructuredSelection) {
			final IStructuredSelection selection = (IStructuredSelection) getSelection();
			for (final Object selectedObject : selection.toList()) {
				if (selectedObject instanceof GraphicalEditPart) {
					viewer = ((GraphicalEditPart) selectedObject).getViewer();
					return viewer != null;
				}
			}
		}
		return false;
	}
	
}
