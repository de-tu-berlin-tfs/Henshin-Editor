package de.tub.tfs.henshin.tggeditor.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.impl.MatchImpl;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.interpreter.matching.constraints.BinaryConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.UnaryConstraint;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.dialogs.TextDialog;
import de.tub.tfs.henshin.tggeditor.util.EdgeUtil;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.util.TggHenshinEGraph;
import de.tub.tfs.muvitor.ui.MuvitorActivator;

/**
 * The Class ExecuteOpRulesCommand executes all the given Rules ({@link TRule}) on a given graph. For the
 * execution are mainly the classes from org.eclipse.emf.henshin.interpreter used. The mapping 
 * of the RuleApplication will be checked with the class {@link OpRuleConstraint}.
 * There will be also the layouts for nodes and edges created.
 */
public class ExecuteOpRulesCommand extends CompoundCommand {

	protected String consistencyType=null;
	protected String consistencyTypeLowerCase=null;	/**
	 * The graph on which all the rules will be applied.
	 */
	protected Graph graph;
	/**
	 * The list of the Rules ({@link TRule}).
	 */
	protected List<Rule> opRuleList;
	/**
	 * The created emfEngine with the registered {@link OpRuleConstraint}.
	 */
	protected TGGEngineImpl emfEngine;
	/**
	 * List of the successful RuleApplications.
	 */
	protected ArrayList<RuleApplicationImpl> ruleApplicationList= new ArrayList<RuleApplicationImpl>();

	public ArrayList<RuleApplicationImpl> getRuleApplicationList() {
		return ruleApplicationList;
	}



	public void setRuleApplicationList(
			ArrayList<RuleApplicationImpl> ruleApplicationList) {
		this.ruleApplicationList = ruleApplicationList;
	}


	protected TranslationMaps translationMaps = new TranslationMaps();
	protected HashMap<Node, Boolean> isTranslatedNodeMap = translationMaps.getIsTranslatedNodeMap();
	protected HashMap<Attribute, Boolean> isTranslatedAttributeMap = translationMaps.getIsTranslatedAttributeMap();
	protected HashMap<Edge, Boolean> isTranslatedEdgeMap = translationMaps.getIsTranslatedEdgeMap();

	public TranslationMaps getTranslationMaps() {
		return translationMaps;
	}



	public void setTranslationMaps(TranslationMaps translationMaps) {
		this.translationMaps = translationMaps;
	}
	
	
	/**the constructor
	 * @param graph {@link ExecuteOpRulesCommand#graph}
	 * @param opRuleList {@link ExecuteOpRulesCommand#opRuleList}
	 */
	public ExecuteOpRulesCommand(Graph graph, List<Rule> opRuleList) {
		super();
		this.graph = graph;
		this.opRuleList = opRuleList;
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return (graph != null && !opRuleList.isEmpty());
	}
	
	/** Executes all the rules on the graph as long as it's possible. The choosing of the sequence 
	 * of RuleApplications is determinated by the order in the {@link ExecuteOpRulesCommand#opRuleList}. 
	 * So when you execute the command twice without changing the order in the list, the same sequence
	 * of applications is chosen.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		
		final TggHenshinEGraph henshinGraph = new TggHenshinEGraph(graph);
		Map<EObject, Node> eObject2Node = henshinGraph.getObject2NodeMap();
		emfEngine = new TGGEngineImpl(henshinGraph,isTranslatedNodeMap,isTranslatedAttributeMap,isTranslatedEdgeMap){

			@Override
			public UnaryConstraint createUserConstraints(Node node) {
				return new OpRuleNodeConstraint(node, isTranslatedNodeMap, henshinGraph);
			}
			
			@Override
			public UnaryConstraint createUserConstraints(Attribute attribute) {
				return new OpRuleAttributeConstraint(attribute,isTranslatedAttributeMap,henshinGraph);
			}
			
			@Override
			public BinaryConstraint createUserConstraints(Edge edge) {
				return new OpRuleEdgeConstraint(edge, isTranslatedNodeMap, isTranslatedEdgeMap, henshinGraph);
			}
		};
		
		
		
		// ruleApplicationList 
		// input graph has to be marked initially to avoid confusion if source and target meta model coincide
		fillTranslatedMaps();
		
		applyRules(henshinGraph, eObject2Node);
		setGraphMarkers();
		
	}

	private void setGraphMarkers() {
		for (Node n : graph.getNodes()) {
			TNode node = (TNode) n;
			if (isTranslatedNodeMap.containsKey(node)) {
				// set marker type to mark the translated nodes
				node.setMarkerType(RuleUtil.Not_Translated_Graph);

				if (isTranslatedNodeMap.get(node)) {
					// mark the translated node
					node.setMarkerType(RuleUtil.Translated_Graph);
				}
				// check contained attributes
				for (Attribute at : node.getAttributes()) {
					// set marker type to mark the translated attributes
					TAttribute a = (TAttribute) at;
					a.setMarkerType(RuleUtil.Not_Translated_Graph);
					if(!isTranslatedAttributeMap.containsKey(a))
						System.out.println("Inconsistent marking: attribute" + a.getType() + "=" + a.getValue() 
								+ " is not marked, but its container node is marked.");
					else if (isTranslatedAttributeMap.get(a)) {
						// mark the translated attribute
						a.setMarkerType(RuleUtil.Translated_Graph);
					}
				}
			}
			else // node is not in marked component 
				{
				node.setMarkerType(null);
				for (Attribute at : node.getAttributes()) {
					TAttribute a = (TAttribute) at;
					a.setMarkerType(null);
				}
				
				
			}
		}
		for (Edge e : graph.getEdges()) {
			TEdge edge = (TEdge) e;
			if (isTranslatedEdgeMap.containsKey(edge)) {
				// set marker type to mark the translated attributes
				edge.setMarkerType(RuleUtil.Not_Translated_Graph);

				if (isTranslatedEdgeMap.get(edge)) {
					// mark the translated edge
					edge.setMarkerType(RuleUtil.Translated_Graph);
				}
			}
			else // edge is not in marked component - delete markers
			{
				edge.setMarkerType(null);
			}
		}
		return;
	}		
	



	protected void fillTranslatedMaps() {
		// fills translated maps with all given elements of the graph, initial
		// value is false = not yet translated
		// component(s) that shall be marked
		TNode tNode = null;
		for (Node node : graph.getNodes()) {
			if (node instanceof TNode)
				tNode = (TNode) node;

			if (tNode != null && tNode.getMarkerType() != null) {
				// node is marked
				if (RuleUtil.Translated_Graph.equals(((TNode) node)
						.getMarkerType()))
					isTranslatedNodeMap.put(node, true);
				else
					isTranslatedNodeMap.put(node, false);
				for (Attribute a : node.getAttributes()) {
					if (RuleUtil.Translated_Graph.equals(((TAttribute) a)
							.getMarkerType()))
						isTranslatedAttributeMap.put(a, true);
					else
						isTranslatedAttributeMap.put(a, false);
				}
				for (Edge e : node.getOutgoing()) {
					if (((TEdge) e).getMarkerType() != null)
						// source and target nodes of edge are in marked
						// component
						if (RuleUtil.Translated_Graph.equals(((TEdge) e)
								.getMarkerType()))
							isTranslatedEdgeMap.put(e, true);
						else
							isTranslatedEdgeMap.put(e, false);
				}
			}
		}
	}

	/**
	 * @param henshinGraph
	 * @param eObject2Node
	 */
	private void applyRules(TggHenshinEGraph henshinGraph,
			Map<EObject, Node> eObject2Node) {
		// check if any rule can be applied
		RuleApplicationImpl ruleApplication = null;
		try {

			boolean foundApplication = true;
			while (foundApplication) {
				foundApplication = false;
				//ruleApplication = new RuleApplicationImpl(emfEngine);
				// apply all rules on graph
				for (Rule rule : opRuleList) {
					
					ruleApplication = new RuleApplicationImpl(emfEngine);

					/*
					 * Apply a rule as long as it's possible and add each
					 * successful application to ruleApplicationlist. Then fill
					 * the isTranslatedTable
					 */
					ruleApplication.setRule(rule);
					ruleApplication.setEGraph(henshinGraph);
					Boolean matchesToCheck = true;
					while (matchesToCheck) {
						Iterator<Match> matchesIterator = emfEngine
								.findMatches(rule, henshinGraph,
										new MatchImpl(rule)).iterator();
						if (!matchesIterator.hasNext())
							matchesToCheck = false;
						while (matchesIterator.hasNext()) {
							ruleApplication.setPartialMatch(matchesIterator
									.next());
							try {
							foundApplication = executeOneStep(henshinGraph,
									eObject2Node, ruleApplication,
									foundApplication, rule);
							} catch (RuntimeException ex){
								matchesToCheck = false;
								ex.printStackTrace();
							}
							emfEngine.postProcess(ruleApplication.getResultMatch());
						}


					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorDialog.openError(
					Display.getDefault().getActiveShell(),
					"Execute Failure",
					"The rule [" + ruleApplication.getRule().getName()
							+ "] couldn't be applied.",
					new Status(IStatus.ERROR, MuvitorActivator.PLUGIN_ID, ex
							.getMessage(), ex.getCause()));
		}
	}

	/**
	 * @param henshinGraph
	 * @param eObject2Node
	 * @param ruleApplication
	 * @param foundApplication
	 * @param rule
	 * @return
	 */
	private boolean executeOneStep(TggHenshinEGraph henshinGraph,
			Map<EObject, Node> eObject2Node,
			RuleApplicationImpl ruleApplication, boolean foundApplication,
			Rule rule) {
		if (ruleApplication.execute(null)) {
			foundApplication = true;
			// position the new nodes according to rule
			// positions
			ruleApplicationList.add(ruleApplication);
			createNodePositions(ruleApplication, henshinGraph,
					(ruleApplicationList.size() - 1) * 40);

			// fill isTranslatedNodeMap
			List<Node> rhsNodes = rule.getRhs().getNodes();
			Match resultMatch = ruleApplication.getResultMatch();

			for (Node n : rhsNodes) {
				TNode ruleNodeRHS = (TNode) n;
				EObject eObject = resultMatch.getNodeTarget(ruleNodeRHS);
				TNode graphNode = (TNode) eObject2Node.get(eObject);
				graphNode.setGuessedSide(ruleNodeRHS.getGuessedSide());
				if (ruleNodeRHS.getMarkerType() != null
						&& ruleNodeRHS.getMarkerType().equals(
								RuleUtil.Translated)) {
					isTranslatedNodeMap.put(graphNode, true);
					fillTranslatedAttributeMap(ruleNodeRHS, graphNode,
							eObject2Node, isTranslatedAttributeMap);
					fillTranslatedEdgeMap(ruleNodeRHS, graphNode, resultMatch,
							eObject2Node, isTranslatedEdgeMap);
				} else // context node, thus check whether
						// the edges and attributes are
						// translated
				{
					fillTranslatedAttributeMap(ruleNodeRHS, graphNode,
							eObject2Node, isTranslatedAttributeMap);
					fillTranslatedEdgeMap(ruleNodeRHS, graphNode, resultMatch,
							eObject2Node, isTranslatedEdgeMap);
				}
			}
		} else {
			throw new RuntimeException("Match NOT applicable!");
		}
		return foundApplication;
	}

	
	
	



	protected void fillTranslatedAttributeMap(Node ruleNodeRHS, Node graphNode, Map<EObject, Node> eObject2Node,
			HashMap<Attribute, Boolean> isTranslatedAttributeMap) {
		//fill isTranslatedAttributeMap
		//scan the contained attributes for <tr>
		for (Attribute ruleAttribute : ruleNodeRHS.getAttributes()) {
			String isMarked=((TAttribute) ruleAttribute).getMarkerType();
				if (isMarked!=null && isMarked.equals(RuleUtil.Translated)) {
					//find matching graph attribute (to the rule attribute)
					Attribute graphAttribute = NodeUtil.findAttribute(graphNode, ruleAttribute.getType());
					isTranslatedAttributeMap.put(graphAttribute, true);
			}
		}			
	}



	
	
	protected void fillTranslatedEdgeMap(Node ruleNode, Node graphNode, Match resultMatch, Map<EObject, Node> eObject2Node, HashMap<Edge, Boolean> isTranslatedEdgeMap) {
		//fill isTranslatedEdgeMap
		EObject eObject;
		//scan the outgoing edges for <tr>
		for (Edge ruleEdge : ruleNode.getOutgoing()) {
			if (RuleUtil.Translated.equals(((TEdge) ruleEdge).getMarkerType())) {
				Node ruleTarget = ruleEdge.getTarget();
				eObject = resultMatch.getNodeTarget(ruleTarget);
				Node graphTarget = eObject2Node.get(eObject);
				Node graphSource = graphNode;
				
				//find matching graph edge (to the rule edge)
				Edge graphEdge = EdgeUtil.findEdge(graphSource, graphTarget, ruleEdge.getType());
				
				isTranslatedEdgeMap.put(graphEdge, true);
			}
		}		
	}


	


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		for (int i = ruleApplicationList.size()-1; i>=0; i--) {
			ruleApplicationList.get(i).undo(null);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		for (RuleApplicationImpl rp : ruleApplicationList) {
			rp.redo(null);
		}
	}
	
	
	/**
	 * Creates the node layouts for the new nodes in the graph. The coordinates 
	 * are calculated for each new node in the graph. relative to the next node. 
	 * 
	 * @param ruleApplication the rule application with the applied rule
	 * @param henshinGraph henshingraph on which the rule was aplied
	 * @param deltaY adds the value to the y coordinate of all generated layouts
	 */
	protected static void createNodePositions(RuleApplication ruleApplication,
			TggHenshinEGraph henshinGraph, int deltaY) {
		
		Rule rule = ruleApplication.getRule();
		
		EList<Node> ruleNodes = (EList<Node>) rule.getRhs().getNodes();
		// store rule nodes in two lists of preserved and created nodes
		ArrayList<TNode> createdRuleNodes = new ArrayList<TNode>();
		ArrayList<TNode> preservedRuleNodes = new ArrayList<TNode>();
		TNode rNode;
		for (Node rn : ruleNodes) {
			rNode = (TNode) rn;
			if (NodeUtil.isNew(rNode)) {
				createdRuleNodes.add(rNode);
			} else {
				preservedRuleNodes.add(rNode);
			}
		}
		
		Match comatch = ruleApplication.getResultMatch();
		Map<EObject, Node> eObject2graphNode = henshinGraph.getObject2NodeMap();
		for (TNode createdRuleNode : createdRuleNodes) {
			
			//find next preservedRuleNode
			Point createdRnPoint = new Point(createdRuleNode.getX(), createdRuleNode.getY());
			TNode closestRn = createdRuleNode;
			double bestDistance = Double.MAX_VALUE;
			for (TNode preservedRn : preservedRuleNodes) {
				Point preservedRnP = new Point(preservedRn.getX(), preservedRn.getY());
				double curDistance = createdRnPoint.getDistance(preservedRnP);
				if (curDistance < bestDistance) {
					bestDistance = curDistance;
					closestRn = preservedRn;
				}
			}
			
			//get graph node at closest position
			EObject closestGraphEObject = comatch.getNodeTarget(closestRn);
			TNode closestGraphNode = (TNode) eObject2graphNode.get(closestGraphEObject);
						
			//get created graph node
			EObject createdGraphEObject = comatch.getNodeTarget(createdRuleNode);
			TNode createdGraphNode = (TNode) eObject2graphNode.get(createdGraphEObject);	

			//set Point for created graph node as closestGraphNode.Point+distance
			int dX, dY;
			if (closestRn == createdRuleNode) { 
				// there is no preserved rule node, thus use the position of the rule node
				dX = createdRuleNode.getX();
				dY = createdRuleNode.getY();
			} else {
				dX = createdRuleNode.getX() - closestRn.getX();
				dY = createdRuleNode.getY() - closestRn.getY();
			}
			int x = closestGraphNode.getX() + dX;
			int y = closestGraphNode.getY() + dY;
			
			if (NodeUtil.isCorrespondenceNode(createdGraphNode)){
				if (((TripleGraph)createdGraphNode.getGraph()).getDividerSC_X() > x){
					x = ((TripleGraph)createdGraphNode.getGraph()).getDividerSC_X() + 20;
				}
				if (((TripleGraph)createdGraphNode.getGraph()).getDividerCT_X() < x){
					x = ((TripleGraph)createdGraphNode.getGraph()).getDividerSC_X() + 20;
				}
			} else if (NodeUtil.isTargetNode(createdGraphNode)){
				if (((TripleGraph)createdGraphNode.getGraph()).getDividerCT_X() > x){
					x = ((TripleGraph)createdGraphNode.getGraph()).getDividerCT_X() + 20;
				}
			}
			
			createdGraphNode.setY(y+deltaY);
			createdGraphNode.setX(x);
		}
	}

}
