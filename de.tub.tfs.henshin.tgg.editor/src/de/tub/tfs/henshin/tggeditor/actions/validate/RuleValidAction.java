package de.tub.tfs.henshin.tggeditor.actions.validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tggeditor.TGGEditorMarkerResolutionGenerator.ErrorTypes;
import de.tub.tfs.henshin.tggeditor.TGGEditorMarkerResolutionGenerator.TGGMarkerAttributes;
import de.tub.tfs.henshin.tggeditor.dialogs.ValidTestDialog;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleTreeEditPart;
import de.tub.tfs.henshin.tggeditor.util.NodeTypes;
import de.tub.tfs.henshin.tggeditor.util.NodeTypes.NodeGraphType;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;
import de.tub.tfs.muvitor.ui.IDUtil;


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

	protected static TGGRule rule;


	
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

				rule = (TGGRule) editpart.getModel();

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
		IDUtil.getHostEditor(rule).clearAllMarker();
		List<String> errorMessages = new ArrayList<String>();
		checkRuleValid(errorMessages,rule,true);
		openDialog(errorMessages);
	}

	/**
	 * adds error messages to the given list
	 * @param errorMessages
	 */

	public static void checkRuleValid(List<String> errorMessages, Rule r, boolean withWarnings) {
		rule = (TGGRule) r;

		HashMap<Node, Node> rhsNode2lhsNode;
		HashMap<Edge, Edge> rhsEdge2lhsEdge;
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
			if (!m.getOrigin().getGraph().getRule().equals(rule)){
				IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_ERROR, m, rule.getName(), "The Mapping " + m.toString() + ": is inconsistent.");
				try {
					marker.setAttribute(TGGMarkerAttributes.errorType, ErrorTypes.InconsistentMapping.name());
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (!m.getImage().getGraph().getRule().equals(rule)){
				IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_ERROR, m, rule.getName(), "The Mapping " + m.toString() + ": is inconsistent.");
				try {
					marker.setAttribute(TGGMarkerAttributes.errorType, ErrorTypes.InconsistentMapping.name());
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		for (NestedCondition c : rule.getLhs().getNestedConditions()) {
			for (Mapping m : c.getMappings()) {
				if (m.getImage() == null || m.getOrigin() == null){
					IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_ERROR, m, rule.getName(), "The Mapping " + m.toString() + ": is inconsistent.");
					try {
						marker.setAttribute(TGGMarkerAttributes.errorType, ErrorTypes.InconsistentMapping.name());
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					continue;
				}				
				if (!m.getOrigin().getGraph().getRule().equals(rule)){
					IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_ERROR, m, rule.getName(), "The Mapping " + m.toString() + ": is inconsistent.");
					try {
						marker.setAttribute(TGGMarkerAttributes.errorType, ErrorTypes.InconsistentMapping.name());
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (!m.getImage().getGraph().getRule().equals(rule)){
					IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_ERROR, m, rule.getName(), "The Mapping " + m.toString() + ": is inconsistent.");
					try {
						marker.setAttribute(TGGMarkerAttributes.errorType, ErrorTypes.InconsistentMapping.name());
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
		for (Edge edge : rule.getRhs().getEdges()) {
			if (rhsNode2lhsNode.containsKey(edge.getSource())
					&& rhsNode2lhsNode.containsKey(edge.getTarget())) {
				Node sourceLhs = rhsNode2lhsNode.get(edge.getSource());
				Node targetLhs = rhsNode2lhsNode.get(edge.getTarget());
				
				if (sourceLhs == null || targetLhs == null){
					
				}
				
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
		
		
		// check for missing mappings, there should not be any deleted item
		List<String> errors = new ArrayList<String>();
		
		for (Node node : rule.getLhs().getNodes()) {
			TNode tnode = (TNode) node;
			if (!RuleUtil.NEW.equals(tnode.getMarkerType()) &&
				!RuleUtil.Translated.equals(tnode.getMarkerType()) &&
				tnode.getMarkerType() != null){
				errors.add("The node " + node.getName() + " doesn't have a valid marker.");
				IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_WARNING, node, rule.getName(), "The node " + node.getName() + ": doesn't have a valid marker.");
				try {
					marker.setAttribute(TGGMarkerAttributes.errorType, ErrorTypes.WrongMarker.name());
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (node.getType() == null){
				errors.add("The node " + node.getName() + " doesn't have a valid Type.");
				IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_ERROR, node, rule.getName(), "The node " + node.getName() + ": doesn't have a valid Type.");
				try {
					marker.setAttribute(TGGMarkerAttributes.errorType, ErrorTypes.MissingType.name());
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for (Attribute attr : node.getAttributes()) {
				if (attr.getType() == null){
					errors.add("The attribute " + attr.toString() + " of node " + node.getName() + ": doesn't have a valid Type.");
					IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_ERROR, attr, rule.getName(), "The attribute " + attr.toString() + " of node " + node.getName() + ": doesn't have a valid Type.");
					try {
						marker.setAttribute(TGGMarkerAttributes.errorType, ErrorTypes.MissingType.name());
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		for (Node node : rule.getRhs().getNodes()) {
			if (node.getType() == null){
				errors.add("The node " + node.getName() + ": doesn't have a valid Type.");
				IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_ERROR, node, rule.getName(), "The node " + node.getName() + " doesn't have a valid Type.");
				try {
					marker.setAttribute(TGGMarkerAttributes.errorType, ErrorTypes.MissingType.name());
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for (Attribute attr : node.getAttributes()) {
				if (attr.getType() == null){
					errors.add("The attribute " + attr.toString() + " of node " + node.getName() + ": doesn't have a valid Type.");
					IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_ERROR, attr, rule.getName(), "The attribute " + attr.toString() + " of node " + node.getName() + ": doesn't have a valid Type.");
					try {
						marker.setAttribute(TGGMarkerAttributes.errorType, ErrorTypes.MissingType.name());
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		for (Edge edge : rule.getLhs().getEdges()) {
			if (edge.getType() == null){
				errors.add("The edge " + edge.toString() + " doesn't have a valid Type.");
				IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_ERROR, edge, rule.getName(), "The edge " + edge.toString() + " doesn't have a valid Type.");
				try {
					marker.setAttribute(TGGMarkerAttributes.errorType, ErrorTypes.MissingType.name());
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		for (Edge edge : rule.getRhs().getEdges()) {
			if (edge.getType() == null){
				errors.add("The edge " + edge.toString() + " doesn't have a valid Type.");
				IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_ERROR, edge, rule.getName(), "The edge " + edge.toString() + " doesn't have a valid Type.");
				try {
					marker.setAttribute(TGGMarkerAttributes.errorType, ErrorTypes.MissingType.name());
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		validateTGG(errors, createdNodes, createdEdges, deletedNodes, deletedEdges);
		
		List<String> warnings = new ArrayList<String>();

		if(withWarnings)
			checkWarnings(rhsNode2lhsNode, createdNodes, deletedNodes,
				createdEdges, deletedEdges, changeEdgesOld2New, warnings);
		
		
		if(!errors.isEmpty() || !warnings.isEmpty()) {
			errorMessages.add("=== Rule: " + rule.getName() +  " ======================");

			if(!errors.isEmpty()) {
				errorMessages.add("--- ### ERRORS ### ---------------");
				errorMessages.addAll(errors);
			}
		
			if(!warnings.isEmpty()) {
				errorMessages.add("--- Warnings ---------------------");
				errorMessages.addAll(warnings);
			}
		
		
		}

		
		
		
		
	}

	/**
	 * @param rhsNode2lhsNode
	 * @param createdNodes
	 * @param deletedNodes
	 * @param createdEdges
	 * @param deletedEdges
	 * @param changeEdgesOld2New
	 * @param warnings
	 */
	private static void checkWarnings(HashMap<Node, Node> rhsNode2lhsNode,
			List<Node> createdNodes, List<Node> deletedNodes,
			List<Edge> createdEdges, List<Edge> deletedEdges,
			Map<Edge, Edge> changeEdgesOld2New, List<String> warnings) {
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
				if (type != NodeGraphType.CORRESPONDENCE){
					warnings.add("The node " + node.getName() + ": "
							+ node.getType().getName()
							+ " will have no containment edge. ");
					IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_WARNING, node, rule.getName(), "The node " + node.getName() + ": "
							+ node.getType().getName()
							+ " will have no containment edge. ");
				}
			}
			if (count > 1) {
				warnings.add("The node " + node.getName() + ": "
						+ node.getType().getName()
						+ " will have more than one containment edge. ");
				IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_WARNING, node,rule.getName(), "The node " + node.getName() + ": "
						+ node.getType().getName()
						+ " will have more than one containment edge. ");
			}
		}

		// Delete an object node together with its containment edge only.
		for (Node node : deletedNodes) {
			boolean contaimentDeletion = false;
			for (Edge edge : node.getIncoming()) {
				if (edge.getType().isContainment()
						&& deletedEdges.contains(edge)) {
					contaimentDeletion = true;
					break;
				}
			}
			if (!contaimentDeletion) {
				warnings.add("The node " + node.getName() + ": "
						+ (node.getType() == null ? "null" : node.getType().getName())
						+ " will be deleted without containment edge. ");
				IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_WARNING, node, rule.getName(), "The node " + node.getName() + ": "
						+ (node.getType() == null ? "null" : node.getType().getName())
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
						warnings.add("The containment edge "
								+ (edge.getType() == null ? "null" : edge.getType().getName())
								+ " will be created without a corresponding node. ");
						IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_WARNING, edge, rule.getName(), "The containment edge "
								+ (edge.getType() == null ? "null" : edge.getType().getName())
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
				warnings.add("The containment edge " + edge.getType().getName()
						+ " will be deleted without a corresponding node. ");
				IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_WARNING, edge, rule.getName(), "The containment edge " + edge.getType().getName()
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
				warnings.add("New Container is not in LHS defined.");
				IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_WARNING, edge.getGraph(), rule.getName(), "New Container is not in LHS defined.");
			} else {
				if (!((contains(o, n) && !contains(m, n)) || contains(n, o))) {
					warnings
							.add("The graph may contain cycles after executing the rule.");
					IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_WARNING, edge.getGraph(), rule.getName(), "The graph may contain cycles after executing the rule.");
					
				}
			}
		}
		
/*		//check for tgg consistency
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
*/
	}

	/**
	 * @param errorMessages
	 * @param deletedNodes
	 * @param deletedEdges
	 */
	private static void validateTGG(List<String> errorMessages, List<Node> createdNodes, List<Edge> createdEdges,
			List<Node> deletedNodes, List<Edge> deletedEdges) {
		boolean errorOccurred = false;

		if (rule.getMarkerType() != null)
			// each TGG rule must create at least one element, otherwise the operational rules will not terminate
			if (rule.getMarkerType().equals(RuleUtil.TGG_RULE)) {
				// determine whether rule creates any attribute
				boolean ruleCreatesAttribute = false;
				for (Node n : rule.getRhs().getNodes()) {
					for (Attribute at : n.getAttributes()) {
					TAttribute a = (TAttribute) at;	
					if (a.getMarkerType() != null
							&& a.getMarkerType().equals(RuleUtil.NEW) )
						ruleCreatesAttribute = true;
					}
				}
				if (createdNodes.size() == 0 && createdEdges.size() == 0 && !ruleCreatesAttribute) {
					IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_ERROR, rule, rule.getName(), "The rule does not create any node nor edge nor attribute. The execution of "
							+ "operational rules will not terminate.");
					errorMessages
					.add("The rule does not create any node nor edge nor attribute. The execution of "
							+ "operational rules will not terminate.");
				}
			} 
			// each operational TGG rule must contain at least one translation marker, otherwise it will not terminate
			else if (rule.getMarkerType().equals(RuleUtil.TGG_FT_RULE)) {
				// determine whether rule contains any translation marker
				boolean ftRuleContainsTRMarker = false;
				// check nodes
				for (Node no : rule.getLhs().getNodes()) {
					TNode n = (TNode) no; 
					if (n.getMarkerType() != null
							&& n.getMarkerType().equals(RuleUtil.Translated) )
						ftRuleContainsTRMarker = true;
					// check attributes
					for (Attribute at : n.getAttributes()) {
						TAttribute a = (TAttribute) at;	
							if (a.getMarkerType() != null
								
								&& a.getMarkerType()
										.equals(RuleUtil.Translated) )
							ftRuleContainsTRMarker = true;
					}

				}
				// check edges
				for (Edge ed : rule.getLhs().getEdges()) {
					TEdge e =(TEdge) ed;
					if (e.getMarkerType() != null
							&& e.getMarkerType().equals(RuleUtil.Translated) )
						ftRuleContainsTRMarker = true;
				}
				if (!ftRuleContainsTRMarker) {
					IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_ERROR, rule, rule.getName(), "The operational rule does not contain any translation marker. The execution of "
							+ "this rule will not terminate.");
					errorMessages
							.add("The operational rule does not contain any translation marker. The execution of "
									+ "this rule will not terminate.");
				}
			}

		// check marking of created nodes
		for(Node n: createdNodes){
			TNode node = (TNode) n;
			if (RuleUtil.NEW.equals(node.getMarkerType())){
				
			} else {
				IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_ERROR, node, rule.getName(), "The node " + node.getName() + ": "
						+ (node.getType() == null ? "null" : node.getType().getName())
						+ " is created, but the marker is missing. This is inconsistent. Please correct using the marking tool.");
				try {
					marker.setAttribute(TGGMarkerAttributes.errorType, ErrorTypes.MissingMarker.name());
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				errorMessages.add("The node " + node.getName() + ": "
						+ (node.getType() == null ? "null" : node.getType().getName())
						+ " is created, but the marker is missing. This is inconsistent. Please correct using the marking tool.");
			}
		}

		for(Node node: deletedNodes){
			
			IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_ERROR, node, rule.getName(), "The node " + node.getName() + ": "
					+ (node.getType() == null ? "null" : node.getType().getName())
					+ " is deleted. This is inconsistent to a TGG. Please correct using the marking tool.");
			try {
				marker.setAttribute(TGGMarkerAttributes.errorType, ErrorTypes.NodeDeleted.name());
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			errorMessages.add("The node " + node.getName() + ": "
					+ (node.getType() == null ? "null" : node.getType().getName())
					+ " is deleted. This is inconsistent to a TGG. Please correct using the marking tool.");
		}

		for(Edge edge: deletedEdges){
			IMarker marker = IDUtil.getHostEditor(rule).createErrorMarker(IMarker.SEVERITY_ERROR, edge, rule.getName(), "The edge "
					+ (edge.getType() == null ? "null" : edge.getType().getName())
					+ " is deleted. This is inconsistent to a TGG. Please correct using the marking tool.");
			
			try {
				marker.setAttribute(TGGMarkerAttributes.errorType, ErrorTypes.EdgeDeleted.name());
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			errorMessages.add("The edge "
					+ (edge.getType() == null ? "null" : edge.getType().getName())
					+ " is deleted. This is inconsistent to a TGG. Please correct using the marking tool.");
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
	private static boolean contains(Node node1, Node node2) {
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
	 * @param errorMessages
	 */
	protected void openDialog(List<String> errorMessages) {
		if (errorMessages.size() == 0) {
			errorMessages.add("Everything Ok!");
		}
		ValidTestDialog vD = new ValidTestDialog(getWorkbenchPart().getSite()
				.getShell(), SWT.NULL, errorMessages);
		vD.open();
	}

}
