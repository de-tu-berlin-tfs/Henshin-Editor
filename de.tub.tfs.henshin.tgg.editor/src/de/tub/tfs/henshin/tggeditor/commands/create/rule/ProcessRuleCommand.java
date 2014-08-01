package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
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
import de.tub.tfs.henshin.tgg.interpreter.RuleUtil;
import de.tub.tfs.henshin.tggeditor.util.GraphOptimizer;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;


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

//	protected HashMap<Node, Node> oldNacNodes2TLhsNodes;

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
		public boolean filter(Edge oldNode);
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
//		oldNacNodes2TLhsNodes = new HashMap<Node, Node>();
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

		copier = new Copier();
		EObject result = copier.copy(oldRule);
		copier.copyReferences();

		newRule = (Rule) result;
		newRule.setCheckDangling(false);
		newRule.setName(prefix + oldRule.getName());
		((TGGRule) newRule).setMarkerType(getRuleMarker());
		((TGGRule) newRule).setIsMarked(true);

		// add new rule to the module
		oldRule.getModule().getUnits().add(newRule);

		// create tRule
		tRule = TggFactory.eINSTANCE.createTRule();
		tRule.setRule(newRule);
		tRule.setType(getRuleMarker());
		tgg.getTRules().add(tRule);

		
		IndependentUnit con = (IndependentUnit) getContainer(container);
		if (!con.getSubUnits().contains(newRule))
			con.getSubUnits().add(newRule);
		setGraphLayout();

		// old graphs
		Graph oldRHS = oldRule.getRhs();
		EList<Graph> graphsToProcess = RuleUtil.getNACGraphs(oldRule);
		graphsToProcess.add(oldRHS);
		
		

		/*
		 * process all nodes in the RHS and NAC graphs
		 */
		for (Graph g : graphsToProcess) {
			for (Node o : g.getNodes()) {

				NodeProcessor np = nodeProcessors.get(NodeUtil
						.guessTripleComponent((TNode) o));
				if (np != null && np.filter(o, (Node) copier.get(o)))
					np.process(o, (Node) copier.get(o));
				((TNode) copier.get(o)).setComponent(NodeUtil
						.guessTripleComponent((TNode) o));
				TNode lhs = (TNode) RuleUtil.getLHSNode(o);
				if (lhs != null)
					lhs.setComponent(NodeUtil.guessTripleComponent((TNode) o));
			}

			/*
			 * process all edges in RHS and NAC graphs
			 */
			for (Edge ruleEdge : g.getEdges()) {
				for (EdgeProcessor ep : edgeProcessors) {
					if (ep.filter(ruleEdge))
						ep.process(ruleEdge,  (Edge) copier.get(ruleEdge));
				}
			}
		
		}

		super.execute();
		GraphOptimizer.optimize(newRule.getLhs());
	}

	protected void setEdgeMarker(Edge newEdgeRHS,
			String markerType) {
		if(newEdgeRHS!=null)
		((TEdge) newEdgeRHS).setMarkerType(markerType);
	}

	protected void setAttributeMarker(Attribute newAttRHS,
			String markerType) {
		((TAttribute) newAttRHS).setMarkerType(markerType);
	
	}

	protected Attribute copyAtt(Attribute att, Node newNode) {
		Attribute newAtt = TggFactory.eINSTANCE.createTAttribute();
		newAtt.setType(att.getType());
		newAtt.setValue(att.getValue());
		newAtt.setNode(newNode);
		newNode.getAttributes().add(newAtt);
		return newAtt;
	}

//	/*
//	 * copy NacMappings
//	 */
//	protected List<Mapping> copyNacMappings(EList<Mapping> nacMappings) {
//		List<Mapping> newMappings = new ArrayList<Mapping>();
//
//		for (Mapping m : nacMappings) {
//
//			Node newMappingImage = oldNacNodes2TLhsNodes.get(m.getImage());
//			Node newMappingOrigin = oldLhsNodes2TLhsNodes.get(m.getOrigin());
//
//			Mapping newM = HenshinFactory.eINSTANCE.createMapping(
//					newMappingOrigin, newMappingImage);
//
//			newMappings.add(newM);
//		}
//
//		return newMappings;
//	}

//	/*
//	 * copy graph with all nodes and edges
//	 */
//	protected Graph copyGraph(Graph graph, Graph newGraph) {
//		newGraph.setName(graph.getName());
//		for (Node n : graph.getNodes()) {
//			TNode oldNode = (TNode) n;
//			Node tNode = copyNode(oldNode, newGraph);
//			setNodeLayout(tNode, oldNode);
//			oldNacNodes2TLhsNodes.put(oldNode, tNode);
//		}
//
//		for (Edge edge : graph.getEdges()) {
//			TNode sourceNode = (TNode) edge.getSource();
//			TNode targetNode = (TNode) edge.getTarget();
//			Edge tEdge = copyEdge(edge, newGraph);
//			Node sourceTNode = oldNacNodes2TLhsNodes.get(sourceNode);
//			Node targetTNode = oldNacNodes2TLhsNodes.get(targetNode);
//			setReferences(sourceTNode, targetTNode, tEdge, newGraph);
//			((TEdge) tEdge).setMarkerType(null);
//		}
//		return newGraph;
//	}

	protected void setReferences(Node sourceNode, Node targetNode, Edge edge,
			Graph tRuleGraph) {
		edge.setSource(sourceNode);
		edge.setTarget(targetNode);
		edge.setGraph(tRuleGraph);
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
		((TNode)newNode).setComponent(NodeUtil.guessTripleComponent((TNode) originalNode));
		return newNode;
	}

	protected Node copyNodePure(Node originalNode, Graph destinationGraph) {
		Node newNode = TggFactory.eINSTANCE.createTNode();
		newNode.setName(originalNode.getName());
		newNode.setType(originalNode.getType());
		
		((TNode)newNode).setComponent(NodeUtil.guessTripleComponent((TNode) originalNode));
		
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
		setNodeMarker(rhsNode,markerType);
	}

	
	protected void setNodeMarker(Node rhsNode, String markerType) {
		((TNode) rhsNode).setMarkerType(markerType);
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