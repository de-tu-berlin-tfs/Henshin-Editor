package de.tub.tfs.henshin.tggeditor.commands;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import de.tub.tfs.henshin.tgg.NodeLayout;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tggeditor.util.ExceptionUtil;
import de.tub.tfs.henshin.tggeditor.util.NodeTypes;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.NodeTypes.NodeGraphType;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;
import de.tub.tfs.muvitor.ui.MuvitorActivator;

/**
 * The Class ExecuteFTRulesCommand executes all the given Rules ({@link TRule}) on a given graph. For the
 * execution are mainly the classes from org.eclipse.emf.henshin.interpreter used. The mapping 
 * of the RuleApplication will be checked with the class {@link FTRuleConstraint}.
 * There will be also the layouts for nodes and edges created.
 */
public class ExecuteFTRulesCommand extends Command {

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
	 * @param graph {@link ExecuteFTRulesCommand#graph}
	 * @param fTRuleList {@link ExecuteFTRulesCommand#fTRuleList}
	 */
	public ExecuteFTRulesCommand(Graph graph, List<Rule> fTRuleList) {
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
	 * of RuleApplications is determinated by the order in the {@link ExecuteFTRulesCommand#fTRuleList}. 
	 * So when you execute the command twice without changing the order in the list, the same sequence
	 * of applications is chosen.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		
		HenshinEGraph henshinGraph = new HenshinEGraph(graph);
		Map<EObject, Node> eObject2Node = henshinGraph.getObject2NodeMap();
//		emfEngine = new EmfEngine(henshinGraph);
//		// changed by FH
//		emfEngine.registerUserConstraint(FTRuleConstraintE.class, isTranslatedNodeMap, isTranslatedAttributeMap, isTranslatedEdgeMap);
		emfEngine = new EngineImpl(){
			@Override
			protected void createUserConstraints(RuleInfo ruleInfo, Node node) {
				Variable variable = ruleInfo.getVariableInfo().getNode2variable().get(node);
				variable.userConstraints.add(new FTRuleConstraintE(node, isTranslatedNodeMap, isTranslatedAttributeMap, isTranslatedEdgeMap));
			}
		};
		
		
		ruleApplicationList = new ArrayList<RuleApplicationImpl>();
		//check if any rule can be applied
		RuleApplicationImpl ruleApplication = null;
		try {
			
			boolean foundApplication = true;
			while(foundApplication) {
				foundApplication = false;
				ruleApplication = new RuleApplicationImpl(emfEngine);
				//apply all rules on graph
				for (Rule rule : fTRuleList) {
					ruleApplication.setRule(rule);
					ruleApplication.setEGraph(henshinGraph);
					
					/*Apply a rule as long as it's possible and add each successful application to 
					 * ruleApplicationlist. Then fill the isTranslatedTable*/ 
					while(ruleApplication.execute(null)) {
						foundApplication = true;
						// position the new nodes according to rule positions
						ruleApplicationList.add(ruleApplication);
						createNodePositions(ruleApplication, henshinGraph,(ruleApplicationList.size()-1)*40);			
						
						//fill isTranslatedNodeMap
						List<Node> rhsNodes = rule.getRhs().getNodes();
						Match resultMatch = ruleApplication.getResultMatch();
						
						for (Node ruleNodeRHS : rhsNodes) {
							EObject eObject = resultMatch.getNodeTarget(ruleNodeRHS);
							Node graphNode = eObject2Node.get(eObject);
							if (ruleNodeRHS.getMarkerType()!=null && ruleNodeRHS.getMarkerType().equals(RuleUtil.Translated)
									&& ruleNodeRHS.getIsMarked()!=null && ruleNodeRHS.getIsMarked()
									) {
								isTranslatedNodeMap.put(graphNode, true);
								fillTranslatedAttributeMap(ruleNodeRHS,graphNode,eObject2Node,isTranslatedAttributeMap);
								fillTranslatedEdgeMap(ruleNodeRHS,graphNode,resultMatch,eObject2Node,isTranslatedEdgeMap);
							}
							else // context node, thus check whether the edges and attributes are translated
							{	
								fillTranslatedAttributeMap(ruleNodeRHS,graphNode,eObject2Node,isTranslatedAttributeMap);
								fillTranslatedEdgeMap(ruleNodeRHS,graphNode,resultMatch,eObject2Node,isTranslatedEdgeMap);
							}
						}
						
						ruleApplication = new RuleApplicationImpl(emfEngine);
						ruleApplication.setRule(rule);
						ruleApplication.setEGraph(henshinGraph);

					}
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorDialog.openError(Display.getDefault().getActiveShell(), "Execute Failure", "The rule ["+ ruleApplication.getRule().getName() + "] couldn't be applied.", new Status(IStatus.ERROR,MuvitorActivator.PLUGIN_ID,ex.getMessage(),ex.getCause()));
		}
		
		
		//source consistency check
		List<String> errorMessages = new ArrayList<String>();
		for (Node node : graph.getNodes()) {
			if (isSourceNode(node)){
				// set marker type to mark the translated nodes
				node.setMarkerType(RuleUtil.Translated_Graph);
				node.setIsMarked(false);

				if (!isTranslatedNodeMap.containsKey(node)) {
					String errorString = "The node ["+node.getName()+":"+node.getType().getName()+
							"] was not translated.";
					errorMessages.add(errorString);
				}
				else
					// mark the translated node
					node.setIsMarked(true);

				// check contained attributes
				for (Attribute a: node.getAttributes()){
					// set marker type to mark the translated attributes
					a.setMarkerType(RuleUtil.Translated_Graph);
					a.setIsMarked(false);
					
					if (!isTranslatedAttributeMap.containsKey(a)) {
						String errorString = "The attribute ["+ a.getType().getName() + "=" + a.getValue()  +  "] of node [" 
									+ node.getName() + ":"+node.getType().getName()+
								"] was not translated.";
						errorMessages.add(errorString);
					}
					else
						// mark the translated attribute
						a.setIsMarked(true);
					
				}
				
				
				
			}
		}
		for (Edge edge : graph.getEdges()) {
			if (isSourceEdge(edge) && isSourceNode(edge.getTarget()) && isSourceNode(edge.getSource()) ) {
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
		openDialog(errorMessages);
		markGraph();
	}
	
	
	private void markGraph() {
		for (Node n: graph.getNodes()){
			// mark all nodes
			// TODO
		}
		
	}

	private void fillTranslatedAttributeMap(Node ruleNodeRHS, Node graphNode, Map<EObject, Node> eObject2Node,
			HashMap<Attribute, Boolean> isTranslatedAttributeMap) {
		//fill isTranslatedAttributeMap
		//scan the contained attributes for <tr>
		for (Attribute ruleAttribute : ruleNodeRHS.getAttributes()) {
			Boolean isMarked=ruleAttribute.getIsMarked();
				if (isMarked!=null && isMarked) {
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
			if ((ruleEdge.getIsMarked()!= null) && ruleEdge.getIsMarked()) {
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
	private boolean isSourceEdge(Edge edge) {
		NodeGraphType type = NodeTypes.getEdgeGraphType(edge);
		return type == NodeGraphType.SOURCE;
	}

	/**
	 * Checks if a node is a source node.
	 * @param node
	 * @return true if it is a source node, else false
	 */
	private boolean isSourceNode(Node node) {
		NodeGraphType type = NodeTypes.getNodeGraphType(node);
		return type == NodeGraphType.SOURCE;
	}

	/**
	 * opens the dialog with the given error messages, if no error messages given 
	 * opens the dialog with a check message
	 * @param errorMessages
	 */
	protected void openDialog(List<String> errorMessages) {
		if (errorMessages.size() == 0) {
			errorMessages.add("Source Consistency Check was succsessful.");
		} else {
			errorMessages.add("Source Consistency Check failed!");
		}
		String errorString = "";
		int i=0;
		for (String m : errorMessages) {
			i++;
			if (i<11) errorString += m+"\n";
			if (i==11) errorString += "Only the first 10 items are shown. \n";
		}
		if (!ruleApplicationList.isEmpty()) {
			errorString+="\nThe following Rule(s) were applied:";
			for (RuleApplicationImpl ra : ruleApplicationList) {
				errorString+="\n"+ra.getRule().getName();
			}
		} else {
			errorString+="\nNo Rules were applied.";
		}
//		MessageDialog.openInformation(null, "Source Consistency Check", errorString);		
//		errorDialog("Source Consistency Check", errorString);
		String title = "Source Consistency Check"; 
		
		ResizableMessageDialog dialog = new ResizableMessageDialog(title,errorString);
		dialog.open();

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
			HenshinEGraph henshinGraph, int deltaY) {
		
		Rule rule = ruleApplication.getRule();
		
		EList<Node> ruleNodes = rule.getRhs().getNodes();
		// store rule nodes in two lists of preserved and created nodes
		ArrayList<Node> createdRuleNodes = new ArrayList<Node>();
		ArrayList<Node> preservedRuleNodes = new ArrayList<Node>();
		for (Node rn : ruleNodes) {
			if (NodeUtil.isNew(rn)) {
				createdRuleNodes.add(rn);
			} else {
				preservedRuleNodes.add(rn);
			}
		}
		
		Match comatch = ruleApplication.getResultMatch();
		Map<EObject, Node> eObject2graphNode = henshinGraph.getObject2NodeMap();
		for (Node createdRuleNode : createdRuleNodes) {
			
			//find next preservedRuleNode
			Point createdRnPoint = new Point(createdRuleNode.getX(), createdRuleNode.getY());
			Node closestRn = createdRuleNode;
			double bestDistance = Double.MAX_VALUE;
			for (Node preservedRn : preservedRuleNodes) {
				Point preservedRnP = new Point(preservedRn.getX(), preservedRn.getY());
				double curDistance = createdRnPoint.getDistance(preservedRnP);
				if (curDistance < bestDistance) {
					bestDistance = curDistance;
					closestRn = preservedRn;
				}
			}
			
			//get graph node at closest position
			EObject closestGraphEObject = comatch.getNodeTarget(closestRn);
			Node closestGraphNode = eObject2graphNode.get(closestGraphEObject);
						
			//get created graph node
			EObject createdGraphEObject = comatch.getNodeTarget(createdRuleNode);
			Node createdGraphNode = eObject2graphNode.get(createdGraphEObject);	

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
			
			createdGraphNode.setY(y+deltaY);
			createdGraphNode.setX(x);
		}
	}

}
