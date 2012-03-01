/**
 * 
 */
package de.tub.tfs.henshin.editor.actions.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.ui.dialog.ValidTestDialog;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.editor.util.ResourceUtil;

/**
 * The Class RuleValidAction.
 */
public class ValidateRuleAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.RuleValidAction"; //$NON-NLS-N$

	/** The Constant DESC. */
	static private final String DESC = "Validate";

	/** The Constant TOOLTIP. */
	static private final String TOOLTIP = "Validate Rule";

	static private final ImageDescriptor ICON = ResourceUtil.ICONS.CHECK
			.descr(18);

	/** The rule. */
	protected Rule rule;

	/** The rhs node2lhs node. */
	private Map<Node, Node> rhsNode2lhsNode;

	/** The rhs edge2lhs edge. */
	private Map<Edge, Edge> rhsEdge2lhsEdge;

	/**
	 * Instantiates a new rule valid action.
	 * 
	 * @param part
	 *            the part
	 */
	public ValidateRuleAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText(DESC);
		setDescription(DESC);
		setToolTipText(TOOLTIP);
		setImageDescriptor(ICON);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();

		rule = null;

		if (selectedObjects.size() == 1) {
			Object selectedObject = selectedObjects.get(0);

			if ((selectedObject instanceof EditPart)) {
				Object model = ((EditPart) selectedObject).getModel();

				if (model instanceof Rule) {
					rule = (Rule) model;
				}
			}
		}

		return rule != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		rhsNode2lhsNode = new HashMap<Node, Node>();
		rhsEdge2lhsEdge = new HashMap<Edge, Edge>();
		List<String> meldungen = new ArrayList<String>();
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
				meldungen.add("The node " + node.getName() + ": "
						+ node.getType().getName()
						+ " does not have any containment edges.");
			}
			if (count > 1) {
				meldungen.add("The node " + node.getName() + ": "
						+ node.getType().getName()
						+ " has too many containment edges.");
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
				meldungen.add("Der Knoten " + node.getName() + ": "
						+ node.getType().getName()
						+ " soll ohne containment Kante gelöscht werden. ");
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
						meldungen.add("Die Containment Kante "
								+ edge.getType().getName()
								+ " wird ohne zugehörigen Knoten erstellt. ");
					}
				}
			}
		}

		// Delete a containment edge together with its contained object node (or
		// change the container).
		for (Edge edge : deletedEdges) {
			if (edge.getType().isContainment()
					&& !deletedNodes.contains(edge.getTarget())) {
				meldungen.add("Containment Kante " + edge.getType().getName()
						+ " wird ohne zugehörigen Knoten gelöscht. ");
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
				meldungen.add("Neue Container ist in LHS nicht definiert.");
			} else {
				if (!((contains(o, n) && !contains(m, n)) || contains(n, o))) {
					meldungen
							.add("Der Graph kann nach dem Ausführen Zyklen enthalten.");
				}
			}
		}

		for (Node rhsNode : rule.getRhs().getNodes()) {
			if (rhsNode.getType().isAbstract()) {
				List<Mapping> mappings = ModelUtil.getReferences(rhsNode,
						Mapping.class, rule,
						HenshinPackage.Literals.MAPPING__IMAGE);

				if (mappings.isEmpty()) {
					meldungen.add("RHS Node " + rhsNode.getName()
							+ " of abstract type :"
							+ rhsNode.getType().getName()
							+ " does not have any mapping.");
				}
			}
		}

		if (meldungen.size() == 0) {
			meldungen.add("Validation completed successfully.");
		}

		ValidTestDialog vD = new ValidTestDialog(getWorkbenchPart().getSite()
				.getShell(), SWT.NULL, meldungen);

		vD.open();
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

}
