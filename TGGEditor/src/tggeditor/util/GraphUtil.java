package tggeditor.util;

import java.util.HashMap;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import tgg.EdgeLayout;
import tgg.GraphLayout;
import tgg.NodeLayout;
import tgg.TGG;
import tgg.TGGFactory;
import tggeditor.editparts.graphical.GraphEditPart;
import tggeditor.util.NodeTypes.NodeGraphType;

public class GraphUtil {
	
	static public int center = 350;
	static public int correstpondenceWidth = 100;
	
	
//	private HashMap<Node,Node> oldRhsNodes2TRhsNodes;
	static private HashMap<Node,Node> oldNacNodes2TLhsNodes;
//	private HashMap<Node,Node> oldLhsNode2TLhsNode;
	
	/**
	 * calculates the NodeGraphType for specific x cooredinate in graph
	 * @param x is the given x coordinate
	 * @param graphEditPart where to calculate the type
	 * @return type
	 */
	public static NodeGraphType getNodeGraphTypeForXCoordinate(GraphEditPart graphEditPart, int x) {
		if(graphEditPart != null) {
			int SCx = graphEditPart.getDividerSCpart().getCastedModel().getDividerX();
			int CTx = graphEditPart.getDividerCTpart().getCastedModel().getDividerX();;
			correstpondenceWidth = CTx-SCx;
			center = SCx + correstpondenceWidth/2;
		}

		if(x < center - correstpondenceWidth/2) return NodeGraphType.SOURCE;
		if(x < center + correstpondenceWidth/2) return NodeGraphType.CORRESPONDENCE;
		if(x >= center + correstpondenceWidth/2) return NodeGraphType.TARGET;
		return NodeGraphType.DEFAULT;
	}
	
	public static int getMinXCoordinateForNodeGraphType(NodeGraphType type){
		switch (type) {
		case DEFAULT: return 0;
		case SOURCE : return 0;
		case RULE : return 0;
		case CORRESPONDENCE: return center;
		case TARGET: return center+correstpondenceWidth /2;
		}
		return 0;
	}

	
	/**
	 * Searches the graph layout of divider. If dividerSC is true it searches for 
	 * source-correspondence divider, if its false it searches for the correspondence-target divider.
	 * @param graph which is linked to its dividers
	 * @param dividerSC (search SC or CT divider?)
	 * @return graph layout of searched divider
	 */
	public static GraphLayout getGraphLayout(Graph graph, boolean dividerSC) {
		TGG layoutSystem = NodeUtil.getLayoutSystem(graph);
		if (layoutSystem != null) {
			for (GraphLayout graphLayout : layoutSystem.getGraphlayouts()) {
				if (graphLayout.getGraph() == graph) {
					if (dividerSC == graphLayout.isIsSC())
						return graphLayout;
				}
			}
		}
		return null;
	}
	
	public static Graph copyGraph(Graph graph, Graph newGraph, TGG tgg) {
		
		
//		oldRhsNodes2TRhsNodes = new HashMap<Node, Node>();
		oldNacNodes2TLhsNodes = new HashMap<Node, Node>();
//		oldLhsNode2TLhsNode = new HashMap<Node, Node>();
		
//		Graph newGraph = HenshinFactory.eINSTANCE.createGraph();
		newGraph.setName(graph.getName());
		
		for (Node node : graph.getNodes()) {
			
			boolean source = NodeUtil.isSourceNode(tgg, node.getType());
			NodeLayout oldLayout = NodeUtil.getNodeLayout(node);
			
			if (source) {
				Node tNode = copyNode(node, newGraph);
				tNode.setGraph(newGraph);

				NodeLayout newLayout = setNodeLayout(tNode, null, oldLayout);
				newLayout.setLhsTranslated(true);
				newLayout.setRhsTranslated(true);
				tgg.getNodelayouts().add(newLayout);
				
				oldNacNodes2TLhsNodes.put(node, tNode);		
			} 
			//Dann die restlichen Knoten, die NICHT Source-Komponenten sind
			else  {
				Node newNode = copyNode(node, newGraph);
				
				tgg.getNodelayouts().add(setNodeLayout(newNode, null, oldLayout));
				
				oldNacNodes2TLhsNodes.put(node, newNode);
			}
		}
		
		for (Edge edge : graph.getEdges()) {

			Node sourceNode = edge.getSource();
			Node targetNode = edge.getTarget();
		
			EdgeLayout edgeLayout = TGGFactory.eINSTANCE.createEdgeLayout();
			
			//nur wenn die Kante zwischen zwei Source-Knoten liegt, wird eine
			//TEdge erzeugt
			if (NodeUtil.isSourceNode(tgg, sourceNode.getType()) 
					|| NodeUtil.isSourceNode(tgg, targetNode.getType())) {
				
				Edge tEdge = copyEdge(edge, newGraph);
				
				Node sourceTNode = oldNacNodes2TLhsNodes.get(sourceNode);
				Node targetTNode = oldNacNodes2TLhsNodes.get(targetNode);

				setReferences(sourceTNode, targetTNode, tEdge, newGraph);
				
				//Referenzen im Edgelayout setzen
				edgeLayout.setLhsedge(null);
				edgeLayout.setRhsedge(tEdge);
				edgeLayout.setNew(false);
			} 
			else {
				Edge newEdge = copyEdge(edge, newGraph);

				Node newSourceNode = oldNacNodes2TLhsNodes.get(sourceNode);
				Node newTargetNode = oldNacNodes2TLhsNodes.get(targetNode);
				
				setReferences(newSourceNode, newTargetNode, newEdge, newGraph);
			
				edgeLayout.setRhsedge(newEdge);
				edgeLayout.setNew(false);	
			}
			
			tgg.getEdgelayouts().add(edgeLayout);
		}
		return newGraph;
	}

	public static NodeLayout setNodeLayout(Node rhs, Node lhs, NodeLayout oldLayout) {
		NodeLayout layout = TGGFactory.eINSTANCE.createNodeLayout();
		layout.setNode(rhs);
		layout.setLhsnode(lhs);
		layout.setX(oldLayout.getX());
		layout.setY(oldLayout.getY());
		layout.setNew(oldLayout.isNew());
		return layout;
	}
	
	public static Edge copyEdge(Edge edge, Graph graph) {
		Edge tEdge = HenshinFactory.eINSTANCE.createEdge();
		tEdge.setType(edge.getType());
		return tEdge;
	}
	
	public static void setReferences(Node sourceNode, Node targetNode,
			Edge edge, Graph tRuleGraph) {
		
		edge.setSource(sourceNode);
		edge.setTarget(targetNode);
		edge.setGraph(tRuleGraph);
			
		sourceNode.getOutgoing().add(edge);
		targetNode.getIncoming().add(edge);
		
		tRuleGraph.getEdges().add(edge);
	}
	
	public static Node copyNode(Node node, Graph graph) {
		Node newNode = HenshinFactory.eINSTANCE.createNode();
		newNode.setName(node.getName());
		newNode.setType(node.getType());
		
		for (Attribute att : node.getAttributes()) {
			Attribute newAtt = HenshinFactory.eINSTANCE.createAttribute();
			newAtt.setType(att.getType());
			newAtt.setValue(att.getValue());
			newAtt.setNode(newNode);
			newNode.getAttributes().add(newAtt);
		}
		
		newNode.setGraph(graph);
		graph.getNodes().add(newNode);
		
		return newNode;
	}
	
	public static void setGraphLayout(Graph oldRhs, Graph newRhs, TGG tgg) {
		GraphLayout olddivSC = GraphUtil.getGraphLayout(oldRhs, true);
		GraphLayout olddivCT = GraphUtil.getGraphLayout(oldRhs, false);
		
		if (olddivCT!=null && olddivSC!=null) {
			GraphLayout divSC = TGGFactory.eINSTANCE.createGraphLayout();
			divSC.setIsSC(true);
			divSC.setDividerX(olddivSC.getDividerX());
			divSC.setMaxY(olddivSC.getMaxY());
			divSC.setGraph(newRhs);
			GraphLayout divCT = TGGFactory.eINSTANCE.createGraphLayout();
			divCT.setIsSC(false);
			divCT.setDividerX(olddivCT.getDividerX());
			divCT.setMaxY(olddivCT.getMaxY());
			divCT.setGraph(newRhs);
			
			tgg.getGraphlayouts().add(divSC);
			tgg.getGraphlayouts().add(divCT);		
		}
	}
	
	public static void setNACGraphLayout(Graph oldNAC, Graph newNAC, TGG tgg) {
		GraphLayout olddivSC = GraphUtil.getGraphLayout(oldNAC, true);
		GraphLayout olddivCT = GraphUtil.getGraphLayout(oldNAC, false);
		
		GraphLayout divSC = TGGFactory.eINSTANCE.createGraphLayout();
		divSC.setIsSC(true);
		divSC.setDividerX(olddivSC.getDividerX());
		divSC.setMaxY(olddivSC.getMaxY());
		divSC.setGraph(newNAC);
		GraphLayout divCT = TGGFactory.eINSTANCE.createGraphLayout();
		divCT.setIsSC(false);
		divCT.setDividerX(olddivCT.getDividerX());
		divCT.setMaxY(olddivCT.getMaxY());
		divCT.setGraph(newNAC);
		
		tgg.getGraphlayouts().add(divSC);
		tgg.getGraphlayouts().add(divCT);			
	}
}
