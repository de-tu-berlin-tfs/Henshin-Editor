package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteFoldercommand;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteFTRuleCommand;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.util.SendNotify;


/**
 * this command generates an forward translation rule from a given triple graph
 * grammar rule
 * 
 */
public abstract class ProcessRuleCommand extends Command {

	protected Rule oldRule;

	protected Graph tRuleLhs;

	protected TripleGraph tRuleRhs;

	protected TRule tRule;

	protected Rule newRule;

	protected TGG tgg;

	protected HashMap<Node, Node> oldLhsNodes2TLhsNodes;

	protected HashMap<Node, Node> oldRhsNodes2TRhsNodes;

	protected HashMap<Node, Node> oldNacNodes2TLhsNodes;

	protected String prefix = "UNK_";

	protected int truleIndex;
	protected int oldruleIndex;
	protected boolean update = false;

	protected IndependentUnit container;

	public interface NodeProcessor{
		public boolean filter(Node oldNode,Node newNode);
		public void process(Node oldNode,Node newNode);
	}
	
	public interface EdgeProcessor{
		public boolean filter(Edge oldNode,Edge newNode);
		public void process(Edge oldNode,Edge newNode);
	}
	
	protected HashMap<TripleComponent,NodeProcessor> nodeProcessors = new HashMap<TripleComponent,NodeProcessor>();
	protected HashSet<EdgeProcessor> edgeProcessors = new HashSet<EdgeProcessor>();

	private Copier copier;
	
	protected EObject getCopiedObject(EObject obj){
		return copier.get(obj);
	}
	/**
	 * the constructor
	 * 
	 * @param rule
	 * @see tggeditor.create.rule.CreateRuleCommand
	 */
	public ProcessRuleCommand(Rule rule) {
		this(rule,null);
	}
	
	public abstract IndependentUnit getContainer(IndependentUnit container);
	
	/**
	 * the constructor
	 * 
	 * @param rule
	 * @see tggeditor.create.rule.CreateRuleCommand
	 */
	public ProcessRuleCommand(Rule rule,IndependentUnit container) {
		this.oldRule = rule;

		this.container = container;
		
		oldLhsNodes2TLhsNodes = new HashMap<Node, Node>();
		oldRhsNodes2TRhsNodes = new HashMap<Node, Node>();
		oldNacNodes2TLhsNodes = new HashMap<Node, Node>();
	}

	protected abstract void preProcess();
	
	protected abstract String getRuleMarker();
	/**
	 * executes the command
	 */
	@Override
	public void execute() {

		tgg = NodeUtil.getLayoutSystem(oldRule);

		//
		preProcess();

		// if rule is empty: nothing to do, possibly warning

		// create new rule
		//newRule =  TggFactory.eINSTANCE.createTGGRule();
		

		// the TRule
		// using new marker for the TRule


	    copier = new Copier();
	    EObject result = copier.copy(oldRule);
	    copier.copyReferences();
	   
	    newRule = (Rule) result;
	    newRule.setName(prefix + oldRule.getName());
		tRule = TggFactory.eINSTANCE.createTRule();
		tRule.setRule(newRule);
		tRule.setType(getRuleMarker());
		((TGGRule) newRule).setMarkerType(getRuleMarker());
		((TGGRule) newRule).setIsMarked(true);

		//if (this.update == true) {
		//	// add rule at previous index
		//	tgg.getTRules().add(truleIndex, tRule);
		//	oldRule.getModule().getUnits()
		//			.add(oldruleIndex, newRule);
		//} else {
			// add rule at the end of the list
			tgg.getTRules().add(tRule);
			oldRule.getModule().getUnits().add(newRule);
		//}
		
		IndependentUnit con = (IndependentUnit) getContainer(container);
		if (!con.getSubUnits().contains(newRule))
			con.getSubUnits().add(newRule);
		setGraphLayout();

		// old graphs
		Graph oldLHS = oldRule.getLhs();
		Graph oldRHS = oldRule.getRhs();

		/*
		 * copy all nodes as well as mappings
		 */
		for (Node o : oldRHS.getNodes()) {
			
			NodeProcessor np = nodeProcessors.get(NodeUtil.guessTripleComponent((TNode)o));
			if (np != null && np.filter(o, (Node) copier.get(o)))
				np.process(o, (Node) copier.get(o));

		}

		/*
		 * copy all edges set the references in edge and node
		 */
		for (Edge oldEdgeRHS : oldRHS.getEdges()) {
			for (EdgeProcessor ep : edgeProcessors) {
				if (ep.filter(oldEdgeRHS,  (Edge) copier.get(oldEdgeRHS)))
					ep.process(oldEdgeRHS,  (Edge) copier.get(oldEdgeRHS));
			}
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
					NestedCondition nc = (NestedCondition) o;
					EList<Mapping> nacMappings = nc.getMappings();

					newNacGraph = TggFactory.eINSTANCE.createTripleGraph();
					newNacGraph.setName(nc.getConclusion().getName());

					newNac = HenshinFactory.eINSTANCE.createNestedCondition();
					newNot = HenshinFactory.eINSTANCE.createNot();

					newNac.setConclusion(newNacGraph);
					newNot.setChild(newNac);

					if (tRuleLhs.getFormula() != null) {
						oldF = tRuleLhs.getFormula();
						newRule.getLhs().setFormula(null);
						And newF = HenshinFactory.eINSTANCE.createAnd();
						newF.setLeft(oldF);
						newF.setRight(newNot);
						newRule.getLhs().setFormula(newF);
						SendNotify
								.sendAddFormulaNotify(newRule, (EObject) newF);
					} else {
						newRule.getLhs().setFormula(newNot);
						SendNotify.sendAddFormulaNotify(newRule,
								(EObject) newNot);
					}

					setNACGraphLayout(nc.getConclusion(), newNacGraph);

					newNacGraph = copyGraph(nc.getConclusion(), newNacGraph);
					newNac.getMappings().addAll(copyNacMappings(nacMappings));
				}
			}
		}
/*
		List<Parameter> list = oldRule.getParameters();
		for (Parameter pm : list) {
			Parameter newParam = HenshinFactory.eINSTANCE.createParameter();
			newParam.setName(pm.getName());
			newRule.getParameters().add(newParam);
		}
*/
		super.execute();
	}

	protected void setEdgeMarker(Edge newEdgeRHS, Edge oldEdgeRHS,
			String markerType) {
		((TEdge) newEdgeRHS).setMarkerType(markerType);
		((TEdge) newEdgeRHS).setIsMarked(((TEdge) oldEdgeRHS).getIsMarked());
	}

	protected void setAttributeMarker(Attribute newAttRHS,
			Attribute oldAttribute, String markerType) {
		((TAttribute) newAttRHS).setMarkerType(markerType);
		((TAttribute) newAttRHS).setIsMarked(((TAttribute) oldAttribute).getIsMarked());
	}

	protected Attribute copyAtt(Attribute att, Node newNode) {
		Attribute newAtt = TggFactory.eINSTANCE.createTAttribute();
		newAtt.setType(att.getType());
		newAtt.setValue(att.getValue());
		newAtt.setNode(newNode);
		newNode.getAttributes().add(newAtt);
		return newAtt;
	}

	/*
	 * copy NacMappings
	 */
	protected List<Mapping> copyNacMappings(EList<Mapping> nacMappings) {
		List<Mapping> newMappings = new ArrayList<Mapping>();

		for (Mapping m : nacMappings) {

			Node newMappingImage = oldNacNodes2TLhsNodes.get(m.getImage());
			Node newMappingOrigin = oldLhsNodes2TLhsNodes.get(m.getOrigin());

			Mapping newM = HenshinFactory.eINSTANCE.createMapping(
					newMappingOrigin, newMappingImage);

			newMappings.add(newM);
		}

		return newMappings;
	}

	/*
	 * copy graph with all nodes and edges
	 */
	protected Graph copyGraph(Graph graph, Graph newGraph) {
		newGraph.setName(graph.getName());

		for (Node n : graph.getNodes()) {
			TNode oldNode = (TNode) n;
			boolean source = NodeUtil.isSourceNode(oldNode);

			if (source) {
				Node tNode = copyNode(oldNode, newGraph);
				setNodeLayout(tNode, oldNode);

				oldNacNodes2TLhsNodes.put(oldNode, tNode);
			}
			// afterwards, the remaining nodes outside the source component
			else {
				Node newNode = copyNode(oldNode, newGraph);
				setNodeLayout(newNode, oldNode);

				oldNacNodes2TLhsNodes.put(oldNode, newNode);
			}
		}

		for (Edge edge : graph.getEdges()) {

			TNode sourceNode = (TNode) edge.getSource();
			TNode targetNode = (TNode) edge.getTarget();

			// only if the edge connects two source nodes, a new
			// TEdge will be created
			if (NodeUtil.isSourceNode(sourceNode)
					&& NodeUtil.isSourceNode(targetNode)) {

				Edge tEdge = copyEdge(edge, newGraph);

				Node sourceTNode = oldNacNodes2TLhsNodes.get(sourceNode);
				Node targetTNode = oldNacNodes2TLhsNodes.get(targetNode);

				setReferences(sourceTNode, targetTNode, tEdge, newGraph);

				((TEdge) tEdge).setMarkerType(RuleUtil.Translated);
				((TEdge) tEdge).setIsMarked(false);
			} else {
				Edge newEdge = copyEdge(edge, newGraph);

				Node newSourceNode = oldNacNodes2TLhsNodes.get(sourceNode);
				Node newTargetNode = oldNacNodes2TLhsNodes.get(targetNode);

				setReferences(newSourceNode, newTargetNode, newEdge, newGraph);

				((TEdge) newEdge).setMarkerType(RuleUtil.NEW);
				((TEdge) newEdge).setIsMarked(false);
			}

		}
		return newGraph;
	}

	protected void setReferences(Node sourceNode, Node targetNode, Edge edge,
			Graph tRuleGraph) {

		edge.setSource(sourceNode);
		edge.setTarget(targetNode);
		edge.setGraph(tRuleGraph);

		// followin lines are performed automatically by EMF
//		sourceNode.getOutgoing().add(edge);
//		targetNode.getIncoming().add(edge);
//
//		tRuleGraph.getEdges().add(edge);
	}

	@Override
	public boolean canExecute() {
		return oldRule != null && !oldRule.getRhs().getNodes().isEmpty();
	}

	protected Node copyNode(Node originalNode, Graph destinationGraph) {
		Node newNode = TggFactory.eINSTANCE.createTNode();
		newNode.setName(originalNode.getName());
		newNode.setType(originalNode.getType());

		for (Attribute att : originalNode.getAttributes()) {
			Attribute newAtt = TggFactory.eINSTANCE.createTAttribute();
			newAtt.setType(att.getType());
			newAtt.setValue(att.getValue());
			newAtt.setNode(newNode);
			newNode.getAttributes().add(newAtt);
		}

		newNode.setGraph(destinationGraph);
		destinationGraph.getNodes().add(newNode);

		return newNode;
	}

	protected Node copyNodePure(Node originalNode, Graph destinationGraph) {
		Node newNode = TggFactory.eINSTANCE.createTNode();
		newNode.setName(originalNode.getName());
		newNode.setType(originalNode.getType());

		newNode.setGraph(destinationGraph);
		destinationGraph.getNodes().add(newNode);

		return newNode;
	}

	/*
	 * creates a mapping, sets image and origin and adds the mapping to the
	 * tRule
	 */
	protected void setMapping(Node tNodeLHS, Node tNodeRHS) {
		Mapping mapping = HenshinFactory.eINSTANCE.createMapping();
		mapping.setImage(tNodeRHS);
		mapping.setOrigin(tNodeLHS);

		newRule.getMappings().add(mapping);

	}

	protected void setNodeLayoutAndMarker(Node rhsNode, Node oldNode,
			String markerType) {
		setNodeLayout(rhsNode,oldNode);
		setNodeMarker(rhsNode,oldNode,markerType);
	}

	
	protected void setNodeMarker(Node rhsNode, Node oldNode,
			String markerType) {
		((TNode) rhsNode).setMarkerType(markerType);
		((TNode) rhsNode).setIsMarked(((TNode) oldNode).getIsMarked());
	}

	protected void setNodeLayout(Node rhsNode, Node oldNode) {
		((TNode) rhsNode).setX(((TNode) oldNode).getX());
		((TNode) rhsNode).setY(((TNode) oldNode).getY());
	}

	protected Edge copyEdge(Edge edge, Graph graph) {
		Edge tEdge = TggFactory.eINSTANCE.createTEdge();
		tEdge.setType(edge.getType());
		return tEdge;
	}

	protected void setGraphLayout() {
			TripleGraph oldTripleGraph = (TripleGraph) oldRule.getRhs();
			TripleGraph newTripleGraph = (TripleGraph) newRule.getRhs();
			newTripleGraph.setDividerSC_X(oldTripleGraph.getDividerSC_X());
			newTripleGraph.setDividerCT_X(oldTripleGraph.getDividerCT_X());
			newTripleGraph.setDividerMaxY(oldTripleGraph.getDividerMaxY());
	}

	protected void setNACGraphLayout(Graph oldNAC, Graph newNAC) {
		TripleGraph oldTripleGraph = (TripleGraph) oldNAC;
		TripleGraph newTripleGraph = (TripleGraph) newNAC;
		newTripleGraph.setDividerSC_X(oldTripleGraph.getDividerSC_X());
		newTripleGraph.setDividerCT_X(oldTripleGraph.getDividerCT_X());
		newTripleGraph.setDividerMaxY(oldTripleGraph.getDividerMaxY());
	}
}