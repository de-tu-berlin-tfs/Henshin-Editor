package tggeditor.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.interpreter.info.RuleInfo;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;
import org.eclipse.emf.henshin.interpreter.util.HenshinEGraph;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import tgg.EdgeLayout;
import tgg.NodeLayout;
import tgg.TRule;
import tggeditor.util.EdgeUtil;
import tggeditor.util.NodeTypes;
import tggeditor.util.NodeTypes.NodeGraphType;
import tggeditor.util.NodeUtil;
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
	private ArrayList<RuleApplication> ruleApplicationList;

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

		
		final HashMap<Node, Boolean> isTranslatedNodeMap = new HashMap<Node, Boolean>();
		HashMap<Edge, Boolean> isTranslatedEdgeMap = new HashMap<Edge, Boolean>();
		
		emfEngine = new EngineImpl(){
			@Override
			protected void createUserConstraints(RuleInfo ruleInfo, Node node) {
				Variable variable = ruleInfo.getVariableInfo().getNode2variable().get(node);
				variable.userConstraints.add(new FTRuleConstraint(node, isTranslatedNodeMap));
			}
			
		};

		ruleApplicationList = new ArrayList<RuleApplication>();
		//check if any rule can be applied
		RuleApplication ruleApplication = null;
		try {
			
			boolean foundApplication = true;
			while(foundApplication) {
				foundApplication = false;
				//apply all rules on graph
				for (Rule rule : fTRuleList) {
					ruleApplication = new RuleApplicationImpl(emfEngine,henshinGraph, rule,null);
					
					/*Apply a rule as long as it's possible and add each successfull application to 
					 * ruleApplicationlist. Then fill the isTranslatedTable*/ 
					while(ruleApplication.execute(null)) {
						foundApplication = true;
						ruleApplicationList.add(ruleApplication);
						
						ExecuteRuleCommand.createNodeLayouts(ruleApplication, henshinGraph, 
								(ruleApplicationList.size()-1)*40);
						
						//fill isTranslatedNodeMap
						Match comatch = ruleApplication.getResultMatch();
						List<Node> comatchedRuleNodes = rule.getLhs().getNodes();
						for (Node ruleNode : comatchedRuleNodes) {
							NodeLayout ruleNL = NodeUtil.getNodeLayout(ruleNode);
							if ((ruleNL.getLhsTranslated() != null) && !ruleNL.getLhsTranslated()) {
							//will be added when lhsNode.isTranslated == false
								EObject eObject = comatch.getNodeTarget(ruleNode);
								Node graphNode = eObject2Node.get(eObject);
								isTranslatedNodeMap.put(graphNode, true);
								
								
								//fill isTranslatedEdgeMap
								//scan the incoming edges for <tr>
								for (Edge ruleEdge : ruleNode.getIncoming()) {
									EdgeLayout ruleEL = EdgeUtil.getEdgeLayout(ruleEdge);
									//diese if Abfrage ist evtl. nicht nötig, da alle edges an einem tr node mit tr markiert sind
//									if ((ruleEL.getLhsTranslated() != null) && !ruleEL.getLhsTranslated()) {
										Node ruleSource = ruleEdge.getSource();
										eObject = comatch.getNodeTarget(ruleSource);
										Node graphSource = eObject2Node.get(eObject);
										Node graphTarget = graphNode;
										
										//find matching graph edge (to the rule edge)
										Edge graphEdge = findEdge(graphSource, graphTarget, ruleEdge.getType());
										
										isTranslatedEdgeMap.put(graphEdge, true);
//									}
								}
								//scan the outgoing edges for <tr>
								for (Edge ruleEdge : ruleNode.getOutgoing()) {
									EdgeLayout ruleEL = EdgeUtil.getEdgeLayout(ruleEdge);
									//diese if Abfrage ist evtl. nicht nötig, da alle edges an einem tr node mit tr markiert sind
//									if ((ruleEL.getLhsTranslated() != null) && !ruleEL.getLhsTranslated()) {
										Node ruleTarget = ruleEdge.getTarget();
										eObject = comatch.getNodeTarget(ruleTarget);
										Node graphTarget = eObject2Node.get(eObject);
										Node graphSource = graphNode;
										
										//find matching graph edge (to the rule edge)
										Edge graphEdge = findEdge(graphSource, graphTarget, ruleEdge.getType());
										
										isTranslatedEdgeMap.put(graphEdge, true);
//									}
								}
							}
						}
						
						ruleApplication = new RuleApplicationImpl(emfEngine,henshinGraph, rule,null);
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
			if (!isTranslatedNodeMap.containsKey(node) && 
					isSourceNode(node)) {
				String errorString = "The node "+node.getName()+":"+node.getType().getName()+
						" was not translated.";
				errorMessages.add(errorString);
			}
		}
		for (Edge edge : graph.getEdges()) {
			if (!isTranslatedEdgeMap.containsKey(edge) &&
				isSourceEdge(edge)) {
				String errorString = "The edge "+edge.getType().getName()+":"+edge.getSource().getType().getName()+"->"+
				edge.getTarget().getType().getName()+" was not translated.";
				errorMessages.add(errorString);
			}
		}
		openDialog(errorMessages);
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
		for (String m : errorMessages) {
			errorString += m+"\n";
		}
		if (!ruleApplicationList.isEmpty()) {
			errorString+="\nThe following Rule(s) were applied:";
			for (RuleApplication ra : ruleApplicationList) {
				errorString+="\n"+ra.getRule().getName();
			}
		} else {
			errorString+="\nNo Rules were applied.";
		}
		MessageDialog.openInformation(null, "Source Consistency Check", errorString);
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
		for (RuleApplication rp : ruleApplicationList) {
			rp.redo(null);
		}
	}

}
