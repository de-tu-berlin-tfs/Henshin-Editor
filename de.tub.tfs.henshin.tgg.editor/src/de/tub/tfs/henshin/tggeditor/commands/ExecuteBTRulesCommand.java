package de.tub.tfs.henshin.tggeditor.commands;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.MatchImpl;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.interpreter.info.RuleInfo;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;
import org.eclipse.emf.henshin.interpreter.util.HenshinEGraph;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.tub.tfs.henshin.tgg.NodeLayout;
import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.dialogs.TextDialog;
import de.tub.tfs.henshin.tggeditor.util.ExceptionUtil;
import de.tub.tfs.henshin.tggeditor.util.NodeTypes;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.NodeTypes.NodeGraphType;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.util.TggHenshinEGraph;
import de.tub.tfs.muvitor.ui.MuvitorActivator;

/**
 * The Class ExecuteFTRulesCommand executes all the given Rules ({@link TRule}) on a given graph. For the
 * execution are mainly the classes from org.eclipse.emf.henshin.interpreter used. The mapping 
 * of the RuleApplication will be checked with the class {@link FTRuleConstraint}.
 * There will be also the layouts for nodes and edges created.
 */
public class ExecuteBTRulesCommand extends Command {

	/**
	 * The graph on which all the rules will be applied.
	 */
	private Graph graph;
	/**
	 * The list of the Rules ({@link TRule}).
	 */
	private List<Rule> fTRuleList;
	/**
	 * The created emfEngine with the registered {@link FTRuleConstraint}.
	 */
	private EngineImpl emfEngine;
	/**
	 * List of the successful RuleApplications.
	 */
	private ArrayList<RuleApplicationImpl> ruleApplicationList;

	private HashMap<Node, Boolean> isTranslatedNodeMap = new HashMap<Node, Boolean>();
	private HashMap<Attribute, Boolean> isTranslatedAttributeMap = new HashMap<Attribute, Boolean>();
	private HashMap<Edge, Boolean> isTranslatedEdgeMap = new HashMap<Edge, Boolean>();

	
	
	/**the constructor
	 * @param graph {@link ExecuteBTRulesCommand#graph}
	 * @param fTRuleList {@link ExecuteBTRulesCommand#fTRuleList}
	 */
	public ExecuteBTRulesCommand(Graph graph, List<Rule> fTRuleList) {
		super();
		this.graph = graph;
		this.fTRuleList = fTRuleList;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return (graph != null && !fTRuleList.isEmpty());
	}
	
	/** Executes all the rules on the graph as long as it's possible. The choosing of the sequence 
	 * of RuleApplications is determinated by the order in the {@link ExecuteBTRulesCommand#fTRuleList}. 
	 * So when you execute the command twice without changing the order in the list, the same sequence
	 * of applications is chosen.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		
		TggHenshinEGraph henshinGraph = new TggHenshinEGraph(graph);
		Map<EObject, Node> eObject2Node = henshinGraph.getObject2NodeMap();
		emfEngine = new EngineImpl(){
			@Override
			protected void createUserConstraints(RuleInfo ruleInfo, Node node) {
				Variable variable = ruleInfo.getVariableInfo().getNode2variable().get(node);
				variable.userConstraints.add(new BTRuleConstraintE(node, isTranslatedNodeMap, isTranslatedAttributeMap, isTranslatedEdgeMap));
			}
		};
		
		
		ruleApplicationList = new ArrayList<RuleApplicationImpl>();

		applyRules(henshinGraph, eObject2Node);
		
		
		//source consistency check
		List<String> errorMessages = checkTargetConsistency();
		openDialog(errorMessages);
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
				for (Rule rule : fTRuleList) {
					
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

							foundApplication = executeOneStep(henshinGraph,
									eObject2Node, ruleApplication,
									foundApplication, rule);
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
		}
		return foundApplication;
	}

	private List<String> checkTargetConsistency() {
		List<String> errorMessages = new ArrayList<String>();
		for (Node n : graph.getNodes()) {
			TNode node = (TNode) n;
			if (NodeUtil.isTargetNode(node)){
				// set marker type to mark the translated nodes
				node.setMarkerType(RuleUtil.Not_Translated_Graph);
				

				if (!isTranslatedNodeMap.containsKey(node)) {
					String errorString = "The node ["+node.getName()+":"+node.getType().getName()+
							"] was not translated.";
					errorMessages.add(errorString);
				}
				else
					// mark the translated node
					
					node.setMarkerType(RuleUtil.Translated_Graph);
				// check contained attributes
				for (Attribute at: node.getAttributes()){
					// set marker type to mark the translated attributes
					TAttribute a =(TAttribute) at;
					a.setMarkerType(RuleUtil.Not_Translated_Graph);


					if (!isTranslatedAttributeMap.containsKey(a)) {
						String errorString = "The attribute ["+ a.getType().getName() + "=" + a.getValue()  +  "] of node [" 
								+ node.getName() + ":"+node.getType().getName()+
								"] was not translated.";
						errorMessages.add(errorString);
					} else {
						// mark the translated attribute
						a.setMarkerType(RuleUtil.Translated_Graph);
					}
					
					
				}
				
				
				
			}
		}
		for (Edge e : graph.getEdges()) {
			TEdge edge = (TEdge) e;
			if (isTargetEdge(edge) && isTargetNode(edge.getTarget()) && isTargetNode(edge.getSource()) ) {
				// set marker type to mark the translated attributes
				edge.setMarkerType(RuleUtil.Translated_Graph);
				edge.setIsMarked(false);

				if (!isTranslatedEdgeMap.containsKey(edge)) {
					String errorString = "The edge ["
							+ edge.getType().getName() + ":"
							+ edge.getSource().getType().getName() + "->"
							+ edge.getTarget().getType().getName()
							+ "] was not translated.";
					errorMessages.add(errorString);
				} else
					// mark the translated edge
					edge.setIsMarked(true);
			}
		}
		return errorMessages;
	}
	
	


	private void fillTranslatedAttributeMap(Node ruleNodeRHS, Node graphNode, Map<EObject, Node> eObject2Node,
			HashMap<Attribute, Boolean> isTranslatedAttributeMap) {
		//fill isTranslatedAttributeMap
		//scan the contained attributes for <tr>
		for (Attribute ruleAttribute : ruleNodeRHS.getAttributes()) {
			String isMarked=((TAttribute) ruleAttribute).getMarkerType();
				if (isMarked!=null && isMarked.equals(RuleUtil.Translated)) {
					//find matching graph attribute (to the rule attribute)
					Attribute graphAttribute = findAttribute(graphNode, ruleAttribute.getType());
					isTranslatedAttributeMap.put(graphAttribute, true);
			}
		}			
	}


	/**
	 * Find the attribute with a specific type. Is just working 
	 * when there is not more than one one type of attribute in a node.
	 * @param graphNode source node
	 * @param type type of the attribute
	 * @return the corresponding attribute of graphNode 
	 */
	public static Attribute findAttribute(Node graphNode, EAttribute type) {
		for (Attribute a : graphNode.getAttributes()) {
			if (a.getType() == type) {
				return a;
			}
		}
		return null;
	}

	
	
	
	private void fillTranslatedEdgeMap(Node ruleNode, Node graphNode, Match resultMatch, Map<EObject, Node> eObject2Node, HashMap<Edge, Boolean> isTranslatedEdgeMap) {
		//fill isTranslatedEdgeMap
		EObject eObject;
		//scan the outgoing edges for <tr>
		for (Edge ruleEdge : ruleNode.getOutgoing()) {
			if ((((TEdge) ruleEdge).getIsMarked()!= null) && ((TEdge) ruleEdge).getIsMarked()) {
				Node ruleTarget = ruleEdge.getTarget();
				eObject = resultMatch.getNodeTarget(ruleTarget);
				Node graphTarget = eObject2Node.get(eObject);
				Node graphSource = graphNode;
				
				//find matching graph edge (to the rule edge)
				Edge graphEdge = findEdge(graphSource, graphTarget, ruleEdge.getType());
				
				isTranslatedEdgeMap.put(graphEdge, true);
			}
		}		
	}

	/**
	 * Find the edge between a source node and a target node with a specific type. Is just working 
	 * when there is not more than one one type of edge between the two nodes allowed.
	 * @param source source node
	 * @param target target node
	 * @param type type of the edge
	 * @return edge between the source and the target node with a specific type
	 */
	private Edge findEdge(Node source, Node target, EReference type) {
		if(source==null) {ExceptionUtil.error("Source node of edge is missing"); return null;}
		for (Edge e : source.getOutgoing()) {
			if (e.getType() == type &&
					e.getTarget() == target) {
				return e;
			}
		}
		return null;
	}

	/**
	 * Checks if a edge is a source edge.
	 * @param edge
	 * @return true if it is a source edge, else false
	 */
	private boolean isTargetEdge(Edge edge) {
		NodeGraphType type = NodeTypes.getEdgeGraphType(edge);
		return type == NodeGraphType.TARGET;
	}


	/**
	 * Checks if a node is a source node.
	 * @param node
	 * @return true if it is a source node, else false
	 */
	private static boolean isCorNode(Node node) {
		NodeGraphType type = NodeTypes.getNodeGraphType(node);
		return type == NodeGraphType.CORRESPONDENCE;
	}
	
	/**
	 * Checks if a node is a source node.
	 * @param node
	 * @return true if it is a source node, else false
	 */
	private static boolean isTargetNode(Node node) {
		
		return NodeUtil.isTargetNode((TNode) node);
	}

	/**
	 * opens the dialog with the given error messages, if no error messages given 
	 * opens the dialog with a check message
	 * @param errorMessages
	 */
	protected void openDialog(List<String> errorMessages) {

		String errorString = "";
		if (errorMessages.size() == 0) {
			errorString = "Source Consistency Check was succsessful.\n";
		} else {
			errorString = "Source Consistency Check failed!\n";
		}

		if (!ruleApplicationList.isEmpty()) {
			errorString+="\nThe following Rule(s) were applied:\n";
			for (RuleApplicationImpl ra : ruleApplicationList) {
				errorString+="\n"+ra.getRule().getName();
			}
		} else {
			errorString+="\nNo Rules were applied.\n";
		}
		
		errorString += "\n\n===============================================\n\n";
		
		for (String m : errorMessages) {
			
			errorString += m+"\n";
			
		}
		
//		MessageDialog.openInformation(null, "Source Consistency Check", errorString);		
//		errorDialog("Source Consistency Check", errorString);
		String title = "Source Consistency Check"; 
		Shell shell = new Shell();
		TextDialog dialog = new TextDialog(shell, title, "Results of source consistency check:", errorString);

		dialog.open();
		
		shell.dispose();
//		final MessageDialog dialog = new MessageDialog(Display.getDefault().getActiveShell(),
//				title, null, errorString,
//				MessageDialog.WARNING, new String[] { "OK" }, 0);

		
	}
	
	private void errorDialog(String title, String errorString) {
		   Date date = new Date();
		   SimpleDateFormat format = new SimpleDateFormat();
		   String[] patterns = new String[] {
		      "EEEE", "yyyy", "MMMM", "h 'o''clock'"};
		   String[] prefixes = new String[] {
		      "Today is ", "The year is ", "It is ", "It is "};
		   String[] msg = new String[patterns.length];
		   for (int i = 0; i < msg.length; i++) {
		      format.applyPattern(patterns[i]);
		      msg[i] = prefixes[i] + format.format(date);
		   }
		   final String PID = "TGGEditor";// ExamplesPlugin.ID;
		   MultiStatus info = new MultiStatus(PID, 1, msg[0], null);
		   info.add(new Status(IStatus.INFO, PID, 1, msg[1], null));
		   info.add(new Status(IStatus.INFO, PID, 1, msg[2], null));
		   info.add(new Status(IStatus.INFO, PID, 1, msg[3], null));
//		   ErrorDialog.openError(window.getShell(), title, errorString, info);
		   ErrorDialog.openError(null, title, errorString, info);
		
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
		
		EList<TNode> ruleNodes = (EList)rule.getRhs().getNodes();
		// store rule nodes in two lists of preserved and created nodes
		ArrayList<TNode> createdRuleNodes = new ArrayList<TNode>();
		ArrayList<TNode> preservedRuleNodes = new ArrayList<TNode>();
		for (TNode rn : ruleNodes) {
			if (NodeUtil.isNew(rn)) {
				createdRuleNodes.add(rn);
			} else {
				preservedRuleNodes.add(rn);
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
			
			if (isCorNode(createdGraphNode)){
				if (((TripleGraph)createdGraphNode.getGraph()).getDividerSC_X() > x){
					x = ((TripleGraph)createdGraphNode.getGraph()).getDividerSC_X() + 20;
				}
				if (((TripleGraph)createdGraphNode.getGraph()).getDividerCT_X() < x){
					x = ((TripleGraph)createdGraphNode.getGraph()).getDividerSC_X() + 20;
				}
			} else if (isTargetNode(createdGraphNode)){
				if (((TripleGraph)createdGraphNode.getGraph()).getDividerCT_X() > x){
					x = ((TripleGraph)createdGraphNode.getGraph()).getDividerCT_X() + 20;
				}
			}
			
			createdGraphNode.setY(y+deltaY);
			createdGraphNode.setX(x);
		}
	}

}
