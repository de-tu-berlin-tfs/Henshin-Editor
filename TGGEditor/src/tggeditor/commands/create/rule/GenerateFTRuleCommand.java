package tggeditor.commands.create.rule;

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
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.commands.Command;

import tgg.EdgeLayout;
import tgg.GraphLayout;
import tgg.NodeLayout;
import tgg.TGG;
import tgg.TGGFactory;
import tgg.TRule;
import tggeditor.util.EdgeUtil;
import tggeditor.util.GraphUtil;
import tggeditor.util.NodeUtil;
import tggeditor.util.SendNotify;

/**
 * this command generates an forward translation rule from a given triple graph grammar rule
 * 
 */
public class GenerateFTRuleCommand extends Command {
	
	private Rule oldRule;
	
	private Graph tRuleLhs;
	
	private Graph tRuleRhs;
	
	private TRule tRule;
	
	private Rule newRule;
	
	private TGG tgg;
	
	private HashMap<Node,Node> oldRhsNodes2TRhsNodes;
	
	private HashMap<Node,Node> oldNacNodes2TLhsNodes;
	
	private HashMap<Node,Node> oldLhsNode2TLhsNode;
	
	private String prefix = "FT_";
	
	private int truleIndex;
	private int oldruleIndex;
	private boolean update=false;
	
	/**
	 * the constructor
	 * @param rule
	 * @see tggeditor.create.rule.CreateRuleCommand
	 */
	public GenerateFTRuleCommand(Rule rule) {
		this.oldRule = rule;
		
		oldRhsNodes2TRhsNodes = new HashMap<Node, Node>();
		oldNacNodes2TLhsNodes = new HashMap<Node, Node>();
		oldLhsNode2TLhsNode = new HashMap<Node, Node>();
	}

	/**
	 * executes the command
	 */
	@Override
	public void execute() {

		tgg = NodeUtil.getLayoutSystem(oldRule);
		
		for (TRule tr : tgg.getTRules()) {
			TransformationSystem trafo = oldRule.getTransformationSystem();
			this.truleIndex = tgg.getTRules().indexOf(tr);
			
			if (tr.getRule().getName().equals(prefix+oldRule.getName())) {
				this.update = true;
				this.oldruleIndex = trafo.getRules().indexOf(oldRule);
				trafo.getRules().remove(tr.getRule());
				tgg.getTRules().remove(tr);
				break;
			} //else { this.update = false; }
		}
		
		//Wenn Regel leer, nichts tun, evtl Warnung
		
		newRule = HenshinFactory.eINSTANCE.createRule();
		newRule.setName(prefix+oldRule.getName());
		
		//erzeugte neun RHS Graph
		tRuleRhs = HenshinFactory.eINSTANCE.createGraph();
		tRuleRhs.setName(oldRule.getRhs().getName());
		
		newRule.setRhs(tRuleRhs);
		
		//erzeugte neun LHS Graph
		tRuleLhs = HenshinFactory.eINSTANCE.createGraph();
		tRuleLhs.setName(oldRule.getLhs().getName());
		
		newRule.setLhs(tRuleLhs);
		
		//Die TRule
		tRule = TGGFactory.eINSTANCE.createTRule();
		tRule.setRule(newRule);
		tRule.setType("ft");
		
		if (this.update==true) {
			tgg.getTRules().add(truleIndex, tRule);
			this.oldRule.getTransformationSystem().getRules().add(oldruleIndex, newRule);
		} else {
			tgg.getTRules().add(tRule);
			this.oldRule.getTransformationSystem().getRules().add(newRule);
		}
		
		setGraphLayout();
		
		//alte Graphen
		Graph tggLHS = oldRule.getLhs();
		Graph tggRHS = oldRule.getRhs();

		/*
		 * Kopieren aller Knoten
		 * sowie Mappings
		 */
		for (Node node : tggRHS.getNodes()) {
			
			boolean source = NodeUtil.isSourceNode(tgg, node.getType());

			NodeLayout oldLayout = NodeUtil.getNodeLayout(node);
			boolean notNew = !oldLayout.isNew();
 
			if (source) {
				
				Node tNodeLHS = copyNode(node, tRuleLhs);
				Node tNodeRHS = copyNode(node, tRuleRhs);

				NodeLayout newLayout = setNodeLayout(tNodeRHS, tNodeLHS, oldLayout);
				newLayout.setLhsTranslated(notNew);
				newLayout.setRhsTranslated(true);
				newLayout.setNew(false);
				tgg.getNodelayouts().add(newLayout);
				
				setMapping(tNodeLHS, tNodeRHS);
				
				oldRhsNodes2TRhsNodes.put(node, tNodeRHS);
				oldLhsNode2TLhsNode.put(oldLayout.getLhsnode(), tNodeLHS);
			} 

			else  {
				Node nodeRHS = copyNode(node, tRuleRhs);
				Node nodeLHS = null;

				if( notNew ) {
					nodeLHS = copyNode(node, tRuleLhs);
					
					setMapping(nodeLHS, nodeRHS);

					oldLhsNode2TLhsNode.put(oldLayout.getLhsnode(), nodeLHS);
				}
				
				tgg.getNodelayouts().add(setNodeLayout(nodeRHS, nodeLHS, oldLayout));
				
				oldRhsNodes2TRhsNodes.put(node, nodeRHS);	
			}
		}
		
		/*
		 * Kopieren aller Edges
		 * Setzen der Referenzen in Edge und Node
		 */
		for (Edge edge : tggRHS.getEdges()) {
			
			Node sourceNode = edge.getSource();
			Node targetNode = edge.getTarget();
		
			EdgeLayout oldEdgeLayout = EdgeUtil.getEdgeLayout(edge);
			EdgeLayout edgeLayout = TGGFactory.eINSTANCE.createEdgeLayout();
			
			boolean notNew = !oldEdgeLayout.isNew();
			
			//nur wenn die Kante zwischen zwei Source-Knoten liegt, wird eine
			//TEdge erzeugt
			if (NodeUtil.isSourceNode(tgg, sourceNode.getType()) 
					&& NodeUtil.isSourceNode(tgg, targetNode.getType())) {
				
				Edge tEdgeLHS = copyEdge(edge,tRuleLhs);
				Edge tEdgeRHS = copyEdge(edge, tRuleRhs);
				
				Node sourceTNodeRHS = oldRhsNodes2TRhsNodes.get(sourceNode);
				Node targetTNodeRHS = oldRhsNodes2TRhsNodes.get(targetNode);

				setReferences(sourceTNodeRHS, targetTNodeRHS, tEdgeRHS, tRuleRhs);

				Node sourceTNodeLHS = findLHSNode(sourceTNodeRHS);
				Node targetTNodeLHS = findLHSNode(targetTNodeRHS);
				
				setReferences(sourceTNodeLHS, targetTNodeLHS, tEdgeLHS, tRuleLhs);
				
				//Referenzen im Edgelayout setzen
				edgeLayout.setLhsedge(tEdgeLHS);
				edgeLayout.setRhsedge(tEdgeRHS);
				
				if(!notNew) {
					edgeLayout.setRhsTranslated(false);
				} else {
					edgeLayout.setRhsTranslated(true);
				}
				edgeLayout.setLhsTranslated(true);
				edgeLayout.setNew(false);
				
			} 
			else {
				
				Edge edgeRHS = copyEdge(edge, tRuleRhs);

				Node sourceNodeRHS = oldRhsNodes2TRhsNodes.get(sourceNode);
				Node targetNodeRHS = oldRhsNodes2TRhsNodes.get(targetNode);
				
				setReferences(sourceNodeRHS, targetNodeRHS, edgeRHS, tRuleRhs);
				
				edgeLayout.setRhsedge(edgeRHS);
			
				//nur falls Kante neu ist, kommt sie auch in LHS
				if(notNew) {
					Edge edgeLHS = copyEdge(edge, tRuleLhs);
					
					Node sourceNodeLHS = findLHSNode(sourceNodeRHS);
					Node targetNodeLHS = findLHSNode(targetNodeRHS);

					setReferences(sourceNodeLHS, targetNodeLHS, edgeLHS, tRuleLhs);
				
					edgeLayout.setLhsedge(edgeLHS);
				}
				
				edgeLayout.setNew(!notNew);
				
			}

			tgg.getEdgelayouts().add(edgeLayout);
		}
		
		/*
		 * copy the NACs
		 */
		if (tggLHS.getFormula() != null) {
			TreeIterator<EObject> iter = tggLHS.getFormula().eAllContents();
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
					
					if(tRuleLhs.getFormula() != null){			
						oldF = tRuleLhs.getFormula();
						newRule.getLhs().setFormula(null);
						And newF = HenshinFactory.eINSTANCE.createAnd();
						newF.setLeft(oldF);
						newF.setRight(newNot);
						newRule.getLhs().setFormula(newF);
						SendNotify.sendAddFormulaNotify(newRule, (EObject)newF);
					}
					else{
						newRule.getLhs().setFormula(newNot);
						SendNotify.sendAddFormulaNotify(newRule, (EObject)newNot);
					}
					
					setNACGraphLayout(nc.getConclusion(), newNacGraph);
					
					newNacGraph = copyGraph(nc.getConclusion(), newNacGraph);
					newNac.getMappings().addAll(copyNacMappings(nacMappings));
				}
			}
		}
		
		List<Parameter> list = oldRule.getParameters();
		for (Parameter pm : list) {
			Parameter newParam = HenshinFactory.eINSTANCE.createParameter();
			newParam.setName(pm.getName());
			newRule.getParameters().add(newParam);
		}
			
		super.execute();
	}

	/*
	 * NacMappings kopieren
	 */
	private List<Mapping> copyNacMappings(EList<Mapping> nacMappings) {
		List<Mapping> newMappings = new ArrayList<Mapping>();
		
		for (Mapping m : nacMappings) {

			Node newMappingImage = oldNacNodes2TLhsNodes.get(m.getImage());
			Node newMappingOrigin = oldLhsNode2TLhsNode.get(m.getOrigin());
			
			Mapping newM = HenshinFactory.eINSTANCE.createMapping(newMappingOrigin, newMappingImage);
			
			newMappings.add(newM);
		}
		
		return newMappings;
	}

	/*
	 * Graph kopieren
	 * mit allen Knoten und Kanten
	 */
	private Graph copyGraph(Graph graph, Graph newGraph) {
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
	
	
	private void setReferences(Node sourceNode, Node targetNode,
			Edge edge, Graph tRuleGraph) {
		
		edge.setSource(sourceNode);
		edge.setTarget(targetNode);
		edge.setGraph(tRuleGraph);
			
		sourceNode.getOutgoing().add(edge);
		targetNode.getIncoming().add(edge);
		
		tRuleGraph.getEdges().add(edge);
	}


	@Override
	public boolean canExecute() {
		return oldRule!=null && !oldRule.getRhs().getNodes().isEmpty();
	}
	
	private Node findLHSNode(Node sourceTNode) {
		for (Mapping m : newRule.getMappings()) {
			if (m.getImage() == sourceTNode) { 
				return m.getOrigin();				
			}
		}
		return null;
	}

	private Node copyNode(Node node, Graph graph) {
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
	
	/*
	 * kreiert ein Mapping, setzt Image und Origin und fügt das Mapping der tRule hinzu
	 */
	private void setMapping(Node tNodeLHS, Node tNodeRHS) {
		Mapping mapping = HenshinFactory.eINSTANCE.createMapping();
		mapping.setImage(tNodeRHS);
		mapping.setOrigin(tNodeLHS);
		
		newRule.getMappings().add(mapping);
		
	}

	private NodeLayout setNodeLayout(Node rhs, Node lhs, NodeLayout oldLayout) {
		NodeLayout layout = TGGFactory.eINSTANCE.createNodeLayout();
		layout.setNode(rhs);
		layout.setLhsnode(lhs);
		layout.setX(oldLayout.getX());
		layout.setY(oldLayout.getY());
		layout.setNew(oldLayout.isNew());
		return layout;
	}

	private Edge copyEdge(Edge edge, Graph graph) {
		Edge tEdge = HenshinFactory.eINSTANCE.createEdge();
		tEdge.setType(edge.getType());
		return tEdge;
	}
	
	private void setGraphLayout() {
		GraphLayout olddivSC = GraphUtil.getGraphLayout(oldRule.getRhs(), true);
		GraphLayout olddivCT = GraphUtil.getGraphLayout(oldRule.getRhs(), false);
		
		if (olddivCT!=null && olddivSC!=null) {
			GraphLayout divSC = TGGFactory.eINSTANCE.createGraphLayout();
			divSC.setIsSC(true);
			divSC.setDividerX(olddivSC.getDividerX());
			divSC.setMaxY(olddivSC.getMaxY());
			divSC.setGraph(newRule.getRhs());
			GraphLayout divCT = TGGFactory.eINSTANCE.createGraphLayout();
			divCT.setIsSC(false);
			divCT.setDividerX(olddivCT.getDividerX());
			divCT.setMaxY(olddivCT.getMaxY());
			divCT.setGraph(newRule.getRhs());
			
			tgg.getGraphlayouts().add(divSC);
			tgg.getGraphlayouts().add(divCT);		
		}
	}
	
	private void setNACGraphLayout(Graph oldNAC, Graph newNAC) {
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