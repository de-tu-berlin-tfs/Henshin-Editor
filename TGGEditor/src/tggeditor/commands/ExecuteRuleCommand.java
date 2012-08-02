package tggeditor.commands;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.interpreter.util.HenshinEGraph;
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
import tgg.TGG;
import tgg.TGGFactory;
import tggeditor.util.EdgeUtil;
import tggeditor.util.NodeUtil;
import de.tub.tfs.muvitor.ui.MuvitorActivator;

/**
 * The Class ExecuteRuleCommand executes the given rule on a given graph. For the
 * execution are mainly the classes from org.eclipse.emf.henshin.interpreter used.
 * There will be also the layouts for nodes and edges created.
 */
public class ExecuteRuleCommand extends Command {

	/** The graph on which the rule is executed. */
	private Graph graph;

	/** The rule which will be executed. */
	private Rule rule;

	/** The assignments for the execution. */
	private Map<String, Object> assignments;

	/** The rule application providing the method apply() for execution. */
	private RuleApplication ruleApplication;

	/** The henshin graph needed from ruleApplication. */
	private HenshinEGraph henshinGraph;

	/** The layout system. */
	private TGG layoutSystem;


	/**
	 * Instantiates a new execute rule command.
	 * 
	 * @param graph
	 *            the graph
	 * @param rule
	 *            the rule
	 * @param assignments
	 *            the assignments
	 */
	public ExecuteRuleCommand(Graph graph, Rule rule,
			Map<String, Object> assignments) {
		super();
		this.graph = graph;
		this.rule = rule;
		this.assignments = assignments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return graph != null && rule != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		henshinGraph = new HenshinEGraph(graph);
		EngineImpl emfEngine = new EngineImpl();
		ruleApplication = new RuleApplicationImpl(emfEngine,henshinGraph, rule,null);
		if (assignments != null) {
			for (Entry<String, Object> entry : assignments.entrySet()) {
				ruleApplication.setParameterValue(entry.getKey(),entry.getValue());
			}
			
			
		}
		try {
			if (!ruleApplication.execute(null)) {
				MessageDialog.openError(null, "Execute Failure", 
						"The rule ["+ rule.getName() + "] couldn't be applied.");
			} else {
				layoutSystem = NodeUtil.getLayoutSystem(graph);
				createNodeLayouts(ruleApplication, henshinGraph, 0);
				createEdgeLayouts();
			}
		} catch (RuntimeException ex){
			ex.printStackTrace();
			ErrorDialog.openError(Display.getDefault().getActiveShell(), "Execute Failure", "The rule ["+ rule.getName() + "] couldn't be applied.", new Status(IStatus.ERROR,MuvitorActivator.PLUGIN_ID,ex.getMessage(),ex.getCause()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		ruleApplication.undo(null);
		// undoDeleteEdges();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		ruleApplication.redo(null);
		// deleteAllEdges(edges);
	}

	/**
	 * Creates the node layouts for the new nodes in the graph. The coordinates 
	 * are calculated for each new node in the graph. relative to the next node. 
	 * 
	 * @param ruleApplication the rule application with the applied rule
	 * @param henshinGraph henshingraph on which the rule was aplied
	 * @param deltaY adds the value to the y coordinate of all generated layouts
	 */
	protected static void createNodeLayouts(RuleApplication ruleApplication,
			HenshinEGraph henshinGraph, int deltaY) {
		
		Rule rule = ruleApplication.getRule();
		
		EList<Node> ruleNodes = rule.getRhs().getNodes();
		ArrayList<NodeLayout> newRuleNodeLayouts = new ArrayList<NodeLayout>();
		ArrayList<NodeLayout> oldRuleNodeLayouts = new ArrayList<NodeLayout>();
		for (Node rn : ruleNodes) {
			NodeLayout rnl = NodeUtil.getNodeLayout(rn);
			if (rnl.isNew()) {
				newRuleNodeLayouts.add(rnl);
			} else {
				oldRuleNodeLayouts.add(rnl);
			}
		}
		
		Match comatch = ruleApplication.getResultMatch();
		Map<EObject, Node> eObject2graphNode = henshinGraph.getObject2NodeMap();
		for (NodeLayout newRnl : newRuleNodeLayouts) {
			
			//find next oldRuleNode
			Point newRnlPoint = new Point(newRnl.getX(), newRnl.getY());
			NodeLayout nextRnl = newRnl;
			double bestDistance = Double.MAX_VALUE;
			for (NodeLayout oldRnl : oldRuleNodeLayouts) {
				Point oldRnlP = new Point(oldRnl.getX(), oldRnl.getY());
				double curDistance = newRnlPoint.getDistance(oldRnlP);
				if (curDistance < bestDistance) {
					bestDistance = curDistance;
					nextRnl = oldRnl;
				}
			}
			
			//get layout of nextGraphNode
			Node nextRuleNode = nextRnl.getNode();
			EObject nextGraphEObject = comatch.getNodeTarget(nextRuleNode);
			Node nextGraphNode = eObject2graphNode.get(nextGraphEObject);
			NodeLayout nextGnl = NodeUtil.getNodeLayout(nextGraphNode);
						
			//get newGraphNode
			Node newRuleNode = newRnl.getNode();
			EObject newGraphEObject = comatch.getNodeTarget(newRuleNode);

			Node newGraphNode = eObject2graphNode.get(newGraphEObject);	
			//get layout of newGraphNode
			NodeLayout newGnl = NodeUtil.getNodeLayout(newGraphNode);
			
			//set Point for newGraphNode as nextGraphNode.Point+distance
			int dX, dY;
			if (nextRnl == newRnl) {
				dX = newRnl.getX();
				dY = newRnl.getY();
			} else {
				dX = newRnl.getX() - nextRnl.getX();
				dY = newRnl.getY() - nextRnl.getY();
			}
			int x = nextGnl.getX() + dX;
			int y = nextGnl.getY() + dY;
			
			newGnl.setY(y+deltaY);
			newGnl.setX(x);
		}
	}

	/**
	 * Creates an edge layout for each new edge in the graph.
	 */
	private void createEdgeLayouts() {
		for (Edge e : graph.getEdges()) {
			EdgeLayout el = EdgeUtil.getEdgeLayout(e);
			if (el == null) {
				el = TGGFactory.eINSTANCE.createEdgeLayout();
				el.setRhsedge(e);
				layoutSystem.getEdgelayouts().add(el);
			}
		}
	}

}
