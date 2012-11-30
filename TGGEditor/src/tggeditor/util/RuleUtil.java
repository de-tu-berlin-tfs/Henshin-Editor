package tggeditor.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;

import tgg.EdgeLayout;
import tgg.GraphLayout;
import tgg.NodeLayout;
import tgg.TGG;
import tgg.TGGFactory;

public class RuleUtil {
	
	public static Rule copyRule(Rule ruleToCopy) {
		
		HashMap<Node,Node> _oldRhsNodes2RhsNodes = new HashMap<Node, Node>();
		HashMap<Node,Node> _oldNacNodes2LhsNodes = new HashMap<Node, Node>();
		HashMap<Node,Node> _oldLhsNodes2LhsNodes = new HashMap<Node, Node>();
		
		//Regel kreiert
		Rule _newRule = HenshinFactory.eINSTANCE.createRule();
		_newRule.setName("CR_" + ruleToCopy.getName());
		
		//TGG gesetzt
		TGG _tgg = NodeUtil.getLayoutSystem(ruleToCopy);
		
		//erzeuge neuen RHS Graph
		Graph _newRuleRHS = HenshinFactory.eINSTANCE.createGraph();
		_newRuleRHS.setName(ruleToCopy.getRhs().getName());
		_newRule.setRhs(_newRuleRHS);
		
		//erzeuge neuen LHS Graph
		Graph _newRuleLHS = HenshinFactory.eINSTANCE.createGraph();
		_newRuleLHS.setName(ruleToCopy.getLhs().getName());
		_newRule.setLhs(_newRuleLHS);
		
		ruleToCopy.getTransformationSystem().getRules().add(_newRule);
		
		setGraphLayout(ruleToCopy, _newRule);
		
		//alte Graphen
		Graph oldLHS = ruleToCopy.getLhs();
		Graph oldRHS = ruleToCopy.getRhs();

		for (Node oldRHSNode : oldRHS.getNodes()) {
			
			NodeLayout oldNodeLayout = NodeUtil.getNodeLayout(oldRHSNode);
			
			Node oldLHSNode = oldNodeLayout.getLhsnode();
			
			Node newRHSNode = copyNode(oldRHSNode, _newRuleRHS);
			Node newLHSNode = copyNode(oldLHSNode, _newRuleLHS);
			
			NodeLayout newNodeLayout = copyNodeLayout(newRHSNode, newLHSNode, oldNodeLayout);
			_tgg.getNodelayouts().add(newNodeLayout);
			
			_oldRhsNodes2RhsNodes.put(oldRHSNode, newRHSNode);
			
			if (newLHSNode != null) {
				_oldLhsNodes2LhsNodes.put(oldLHSNode, newLHSNode);
				setMapping(newLHSNode, newRHSNode, _newRule);
			}
			
		}
		
		for (Edge oldRHSEdge : oldRHS.getEdges()) {
			
			EdgeLayout oldEdgeLayout = EdgeUtil.getEdgeLayout(oldRHSEdge);
			
			Edge newRHSEdge = copyEdge(oldRHSEdge);
			Edge newLHSEdge = oldEdgeLayout.isNew() ? null : copyEdge(oldRHSEdge);
			
			Node sourceNode = oldRHSEdge.getSource();
			Node targetNode = oldRHSEdge.getTarget();
			
			Node sourceNewRHSNode = _oldRhsNodes2RhsNodes.get(sourceNode);
			Node targetNewRHSNode = _oldRhsNodes2RhsNodes.get(targetNode);
			
			setReferences(sourceNewRHSNode, targetNewRHSNode, newRHSEdge, _newRuleRHS);
			
			Node sourceNewLHSNode = findLHSNode(sourceNewRHSNode, _newRule);
			Node targetNewLHSNode = findLHSNode(targetNewRHSNode, _newRule);
			
			setReferences(sourceNewLHSNode, targetNewLHSNode, newLHSEdge, _newRuleLHS);
			
			copyEdgeLayout(newRHSEdge, newLHSEdge, oldEdgeLayout);
		}
		
		
		/*
		 * copy the NACs
		 */
		if (oldLHS.getFormula() != null) {
			TreeIterator<EObject> iter = oldLHS.getFormula().eAllContents();
			Formula oldF;
			NestedCondition newNac;
			Graph newNacGraph;
			Not newNot;
			
			while (iter.hasNext()) {
				EObject o = iter.next();
				if (o instanceof NestedCondition) {
					NestedCondition nc = (NestedCondition)o;
					EList<Mapping> nacMappings = nc.getMappings();
					
					newNacGraph = HenshinFactory.eINSTANCE.createGraph();
					newNacGraph.setName(nc.getConclusion().getName());
					
					newNac = HenshinFactory.eINSTANCE.createNestedCondition();
					newNot = HenshinFactory.eINSTANCE.createNot();
					
					newNac.setConclusion(newNacGraph);
					newNot.setChild(newNac);
					
					if(_newRuleLHS.getFormula() != null){			
						oldF = _newRuleLHS.getFormula();
						_newRule.getLhs().setFormula(null);
						And newF = HenshinFactory.eINSTANCE.createAnd();
						newF.setLeft(oldF);
						newF.setRight(newNot);
						_newRule.getLhs().setFormula(newF);
						SendNotify.sendAddFormulaNotify(_newRule, (EObject)newF);
					}
					else{
						_newRule.getLhs().setFormula(newNot);
						SendNotify.sendAddFormulaNotify(_newRule, (EObject)newNot);
					}
					
					setNACGraphLayout(nc.getConclusion(), newNacGraph, _tgg);
					
					newNacGraph = copyNACGraph(nc.getConclusion(), newNacGraph, _tgg, _oldNacNodes2LhsNodes);
					newNac.getMappings().addAll(copyNacMappings(nacMappings, _oldNacNodes2LhsNodes, _oldLhsNodes2LhsNodes));
				}
			}
		}
		
		List<Parameter> list = ruleToCopy.getParameters();
		for (Parameter pm : list) {
			Parameter newParam = HenshinFactory.eINSTANCE.createParameter();
			newParam.setName(pm.getName());
			_newRule.getParameters().add(newParam);
		}
		
		return _newRule;
	}
	
	/*
	 * NacMappings kopieren
	 */
	private static List<Mapping> copyNacMappings(EList<Mapping> nacMappings, HashMap<Node,Node> _oldNacNodes2LhsNodes, HashMap<Node,Node> _oldLhsNode2LhsNode) {
		List<Mapping> newMappings = new ArrayList<Mapping>();
		
		for (Mapping m : nacMappings) {

			Node newMappingImage = _oldNacNodes2LhsNodes.get(m.getImage());
			Node newMappingOrigin = _oldLhsNode2LhsNode.get(m.getOrigin());
			
			Mapping newM = HenshinFactory.eINSTANCE.createMapping(newMappingOrigin, newMappingImage);
			
			newMappings.add(newM);
		}
		
		return newMappings;
	}
	
	/*
	 * NAC Graph kopieren
	 * mit allen Knoten und Kanten
	 */
	private static Graph copyNACGraph(Graph graph, Graph newGraph, TGG _tgg, HashMap<Node,Node> _oldNacNodes2LhsNodes) {
		newGraph.setName(graph.getName());
		
		for (Node node : graph.getNodes()) {
			
//			boolean source = NodeUtil.isSourceNode(_tgg, node.getType());
			NodeLayout oldLayout = NodeUtil.getNodeLayout(node);
//			
//			if (source) {
//				Node tNode = copyNode(node, newGraph);
//				tNode.setGraph(newGraph);
//
//				NodeLayout newLayout = copyNodeLayout(tNode, null, oldLayout);
//				newLayout.setLhsTranslated(true);
//				newLayout.setRhsTranslated(true);
//				_tgg.getNodelayouts().add(newLayout);
//				
//				_oldNacNodes2LhsNodes.put(node, tNode);		
//			} 
//			//Dann die restlichen Knoten, die NICHT Source-Komponenten sind
//			else  {
				Node newNode = copyNode(node, newGraph);
				
				_tgg.getNodelayouts().add(copyNodeLayout(newNode, null, oldLayout));
				
				_oldNacNodes2LhsNodes.put(node, newNode);
//			}
		}
		
		for (Edge edge : graph.getEdges()) {

			Node sourceNode = edge.getSource();
			Node targetNode = edge.getTarget();
		
			EdgeLayout edgeLayout = TGGFactory.eINSTANCE.createEdgeLayout();
			
//			//nur wenn die Kante zwischen zwei Source-Knoten liegt, wird eine
//			//TEdge erzeugt
//			if (NodeUtil.isSourceNode(_tgg, sourceNode.getType()) 
//					|| NodeUtil.isSourceNode(_tgg, targetNode.getType())) {
//				
//				Edge tEdge = copyEdge(edge);
//				
//				Node sourceTNode = _oldNacNodes2LhsNodes.get(sourceNode);
//				Node targetTNode = _oldNacNodes2LhsNodes.get(targetNode);
//
//				setReferences(sourceTNode, targetTNode, tEdge, newGraph);
//				
//				//Referenzen im Edgelayout setzen
//				edgeLayout.setLhsedge(null);
//				edgeLayout.setRhsedge(tEdge);
//				edgeLayout.setNew(false);
//			} 
//			else {
				Edge newEdge = copyEdge(edge);

				Node newSourceNode = _oldNacNodes2LhsNodes.get(sourceNode);
				Node newTargetNode = _oldNacNodes2LhsNodes.get(targetNode);
				
				setReferences(newSourceNode, newTargetNode, newEdge, newGraph);
			
				edgeLayout.setRhsedge(newEdge);
				edgeLayout.setNew(false);	
//			}
			
			_tgg.getEdgelayouts().add(edgeLayout);
		}
		return newGraph;
	}
	
	
	private static void setReferences(Node sourceNode, Node targetNode, Edge edge, Graph tRuleGraph) {
		if (edge != null) {
			edge.setSource(sourceNode);
			edge.setTarget(targetNode);
			edge.setGraph(tRuleGraph);
				
			sourceNode.getOutgoing().add(edge);
			targetNode.getIncoming().add(edge);
			
			tRuleGraph.getEdges().add(edge);
		}
	}
	
	private static Node findLHSNode(Node sourceTNode, Rule _newRule) {
		for (Mapping m : _newRule.getMappings()) {
			if (m.getImage() == sourceTNode) { 
				return m.getOrigin();				
			}
		}
		return null;
	}

	private static Node copyNode(Node oldNode, Graph graph) {
		if (oldNode == null) return null;
		Node newNode = HenshinFactory.eINSTANCE.createNode();
		newNode.setName(oldNode.getName());
		newNode.setType(oldNode.getType());
		
		for (Attribute att : oldNode.getAttributes()) {
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
	
	/*
	 * kreiert ein Mapping, setzt Image und Origin und fügt das Mapping der tRule hinzu
	 */
	private static void setMapping(Node nodeLHS, Node nodeRHS, Rule _newRule) {
		Mapping mapping = HenshinFactory.eINSTANCE.createMapping();
		mapping.setImage(nodeRHS);
		mapping.setOrigin(nodeLHS);
		
		_newRule.getMappings().add(mapping);
		
	}

	private static NodeLayout copyNodeLayout(Node rhsNode, Node lhsNode, NodeLayout oldLayout) {
		NodeLayout layout = NodeUtil.getNodeLayout(rhsNode);
		layout.setNode(rhsNode);
		layout.setLhsnode(lhsNode);
		layout.setX(oldLayout.getX());
		layout.setY(oldLayout.getY());
		layout.setNew(oldLayout.isNew());
		layout.setLhsTranslated(oldLayout.getLhsTranslated());
		layout.setRhsTranslated(oldLayout.getRhsTranslated());
		return layout;
	}
	
	private static EdgeLayout copyEdgeLayout(Edge rhsEdge, Edge lhsEdge, EdgeLayout oldLayout) {
		EdgeLayout layout = EdgeUtil.getEdgeLayout(rhsEdge);
		layout.setRhsedge(rhsEdge);
		layout.setLhsedge(lhsEdge);
		layout.setNew(oldLayout.isNew());
		layout.setLhsTranslated(oldLayout.getLhsTranslated());
		layout.setRhsTranslated(oldLayout.getRhsTranslated());
		return layout;				
	}

	private static Edge copyEdge(Edge edge) {
		Edge tEdge = HenshinFactory.eINSTANCE.createEdge();
		tEdge.setType(edge.getType());
		return tEdge;
	}
	
	private static void setGraphLayout(Rule _ruleToCopy, Rule _newRule) {
		GraphLayout olddivSC = GraphUtil.getGraphLayout(_ruleToCopy.getRhs(), true);
		GraphLayout olddivCT = GraphUtil.getGraphLayout(_ruleToCopy.getRhs(), false);
		
		if (olddivCT!=null && olddivSC!=null) {
			GraphLayout divSC = GraphUtil.getGraphLayout(_newRule.getRhs(), true);
			if (divSC == null) divSC = TGGFactory.eINSTANCE.createGraphLayout();
			divSC.setDividerX(olddivSC.getDividerX());
			divSC.setMaxY(olddivSC.getMaxY());
			GraphLayout divCT = GraphUtil.getGraphLayout(_newRule.getRhs(), false);
			if (divCT == null) divCT = TGGFactory.eINSTANCE.createGraphLayout();
			divCT.setDividerX(olddivCT.getDividerX());
			divCT.setMaxY(olddivCT.getMaxY());
		}
	}
	
	private static void setNACGraphLayout(Graph oldNAC, Graph newNAC, TGG _tgg) {
		
		// TODO Layouts wie bei Graphen nur suchen nicht neu erzeugen
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
		
		_tgg.getGraphlayouts().add(divSC);
		_tgg.getGraphlayouts().add(divCT);			
	}
}
