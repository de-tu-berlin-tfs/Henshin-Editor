package tggeditor.actions.validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPart;

import tgg.EdgeLayout;
import tgg.NodeLayout;
import tgg.TGG;
import tgg.TRule;
import tggeditor.dialogs.ValidTestDialog;
import tggeditor.editparts.tree.rule.RuleTreeEditPart;
import tggeditor.util.EdgeUtil;
import tggeditor.util.NodeTypes;
import tggeditor.util.RuleUtil;
import tggeditor.util.NodeTypes.NodeGraphType;
import tggeditor.util.NodeUtil;

/**
 * The class RuleValidAction is shown in the context menu of the tree editor when
 * a rule is selected. It validates the rule if the rule produces a emf consistent
 * graph after its execution. There are also the tgg consistencies checked.
 */
public class RuleValidAction extends SelectionAction {

	/**
	 * the fully qualified class ID
	 */
	public static final String ID = "tggeditor.actions.validate.RuleValidAction";
	/**
	 * the constant DESC for the description
	 */
	private static final String DESC = "Validate Rule";
	/**
	 * the constant TOOLTIP
	 */
	private static final String TOOLTIP = "Validate Rule";
	/**
	 * the rule which will be checked
	 */
	protected Rule rule;
	/**
	 * mapping from rhs node to lhs node
	 */
	private HashMap<Node, Node> rhsNode2lhsNode;
	/**
	 * mapping from rhs edge to lhs edge
	 */
	private HashMap<Edge, Edge> rhsEdge2lhsEdge;
	
	/**
	 * the constructor
	 * @param part editpart of workbench where the Action is to be registered
	 */
	public RuleValidAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText(DESC);
		setDescription(DESC);
		setToolTipText(TOOLTIP);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selectedObject = selectedObjects.get(0);
		if ((selectedObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selectedObject;
			if ((editpart instanceof RuleTreeEditPart)) {
				rule = (Rule) editpart.getModel();
				TGG tgg = NodeUtil.getLayoutSystem(rule);
				for (TRule tr : tgg.getTRules()) {
					if (tr.getRule() == rule) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		List<String> fehlerMeldungen = new ArrayList<String>();
		checkRuleValid(fehlerMeldungen);
		openDialog(fehlerMeldungen);
	}

	/**
	 * adds error messages to the given list
	 * @param errorMessages
	 */
	protected void checkRuleValid(List<String> errorMessages) {
		rhsNode2lhsNode = new HashMap<Node, Node>();
		rhsEdge2lhsEdge = new HashMap<Edge, Edge>();
		List<Node> createdNodes = new ArrayList<Node>();
		List<Node> deletedNodes = new ArrayList<Node>();
		List<Edge> createdEdges = new ArrayList<Edge>();
		List<Edge> deletedEdges = new ArrayList<Edge>();
		Map<Edge, Edge> changeEdgesOld2New = new HashMap<Edge, Edge>();
		createdNodes.addAll(rule.getRhs().getNodes());
		deletedNodes.addAll(rule.getLhs().getNodes());
		for (Mapping m : rule.getMappings()) {
			createdNodes.remove(m.getImage());
			deletedNodes.remove(m.getOrigin());
			rhsNode2lhsNode.put(m.getImage(), m.getOrigin());
		}
		for (Edge edge : rule.getRhs().getEdges()) {
			if (rhsNode2lhsNode.containsKey(edge.getSource())
					&& rhsNode2lhsNode.containsKey(edge.getTarget())) {
				Node sourceLhs = rhsNode2lhsNode.get(edge.getSource());
				Node targetLhs = rhsNode2lhsNode.get(edge.getTarget());
				for (Edge edgeLhs : sourceLhs.getOutgoing()) {
					if (edgeLhs.getTarget() == targetLhs
							&& edgeLhs.getType() == edge.getType()
							&& !rhsEdge2lhsEdge.containsValue(edgeLhs)) {
						rhsEdge2lhsEdge.put(edge, edgeLhs);
						break;
					}
				}
			}
		}
		createdEdges.addAll(rule.getRhs().getEdges());
		createdEdges.removeAll(rhsEdge2lhsEdge.keySet());
		deletedEdges.addAll(rule.getLhs().getEdges());
		deletedEdges.removeAll(rhsEdge2lhsEdge.values());

		// Create a new object node together with its containment edge.
		for (Node node : createdNodes) {
			int count = 0;
			for (Edge edge : node.getIncoming()) {
				if (edge.getType().isContainment()) {
					count++;
				}
			}
			if (count == 0) {
				NodeGraphType type = NodeTypes.getNodeGraphType(node);
				if (type != NodeGraphType.CORRESPONDENCE && 
						!node.getType().getName().equals("ClassDiagram") && !node.getType().getName().equals("Database")){
					errorMessages.add("The node " + node.getName() + ": "
							+ node.getType().getName()
							+ " will have no containment edge. ");
				}
			}
			if (count > 1) {
				errorMessages.add("The node " + node.getName() + ": "
						+ node.getType().getName()
						+ " will have more than one containment edge. ");
			}
		}

		// Delete an object node together with its containment edge only.
		for (Node node : deletedNodes) {
			boolean contaimentLoeschen = false;
			for (Edge edge : node.getIncoming()) {
				if (edge.getType().isContainment()
						&& deletedEdges.contains(edge)) {
					contaimentLoeschen = true;
					break;
				}
			}
			if (!contaimentLoeschen) {
				errorMessages.add("The node " + node.getName() + ": "
						+ node.getType().getName()
						+ " will be deleted without containment edge. ");
			}
		}

		// Create a containment edge together with its contained object node or
		// change the container.
		Iterator<Edge> iter = createdEdges.iterator();
		while (iter.hasNext()) {
			Edge edge = iter.next();
			if (edge.getType().isContainment()) {
				if (!createdNodes.contains(edge.getTarget())) {
					Node nodeLhs = rhsNode2lhsNode.get(edge.getTarget());
					boolean chenged = false;
					for (Edge edgeLhs : nodeLhs.getIncoming()) {
						if (deletedEdges.contains(edgeLhs)
								&& edgeLhs.getType().isContainment()) {
							changeEdgesOld2New.put(edgeLhs, edge);
							deletedEdges.remove(edgeLhs);
							iter.remove();
							chenged = true;
							break;
						}
					}
					if (!chenged) {
						errorMessages.add("The containment edge "
								+ edge.getType().getName()
								+ " will be created without a corresponding node. ");
					}
				}
			}
		}

		// Delete a containment edge together with its contained object node (or
		// change the container).
		for (Edge edge : deletedEdges) {
			if (edge.getType().isContainment()
					&& !deletedNodes.contains(edge.getTarget())) {
				errorMessages.add("The containment edge " + edge.getType().getName()
						+ " will be deleted without a corresponding node. ");
			}
		}

		// Create cycle-capable containment edges only, if the old and the new
		// container are both transitively contained in the same container.

		for (Edge edge : changeEdgesOld2New.keySet()) {
			Node o = edge.getSource();
			Node m = edge.getTarget();
			Node n = rhsNode2lhsNode.get(changeEdgesOld2New.get(edge)
					.getSource());
			if (n == null) {
				errorMessages.add("New Container is not in LHS defined.");
			} else {
				if (!((contains(o, n) && !contains(m, n)) || contains(n, o))) {
					errorMessages
							.add("The graph may contain cycles after executing the rule.");
				}
			}
		}
		
		//check for tgg consistency
		for (Node node:rule.getRhs().getNodes()) {
			NodeGraphType graphType = NodeTypes.getNodeGraphType(node);
			if (graphType == NodeGraphType.CORRESPONDENCE) {
				if (node.getIsMarked()!=null && node.getIsMarked()) {
					for (Edge edge : node.getAllEdges()) {
						if (edge.getIsMarked()!=null && edge.getIsMarked()){
							errorMessages.add("The node \""+
									node.getName()+": "+ node.getType().getName()+
									"\" must not be marked with " + RuleUtil.NEW + " !");
						}
					}
				} else { // node is not marked
					for (Edge edge : node.getAllEdges()) {
						Node otherNode = 	edge.getTarget();
						if ((edge.getIsMarked()!=null && edge.getIsMarked() ) 
								|| (otherNode.getIsMarked()!=null && otherNode.getIsMarked())){
							errorMessages.add("The node \""+
									node.getName()+": "+ node.getType().getName()+
									"\" must be marked with " + RuleUtil.NEW + " !");
						}
					}
				}
			}
		}
	}
	
	/**
	 * Contains.
	 * 
	 * @param node1
	 *            the node1
	 * @param node2
	 *            the node2
	 * @return true, if successful
	 */
	private boolean contains(Node node1, Node node2) {
		for (Edge edge : node2.getIncoming()) {
			if (edge.getType().isContainment()) {
				if (edge.getSource() == node1) {
					return true;
				}
				return contains(node1, edge.getSource());
			}
		}
		return false;
	}
	
	/**
	 * opens the dialog with the given error messages, if no error messages given 
	 * opens the dialog with a check message
	 * @param fehlerMeldungen
	 */
	protected void openDialog(List<String> fehlerMeldungen) {
		if (fehlerMeldungen.size() == 0) {
			fehlerMeldungen.add("Everything Ok!");
		}
		ValidTestDialog vD = new ValidTestDialog(getWorkbenchPart().getSite()
				.getShell(), SWT.NULL, fehlerMeldungen);
		vD.open();
	}

}
