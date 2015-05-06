package de.tub.tfs.henshin.tggeditor.actions.execution.constraint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.MatchImpl;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Constraint;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.NestedConstraint;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.interpreter.TggTransformation;
import de.tub.tfs.henshin.tgg.interpreter.impl.TggEngineOperational;
import de.tub.tfs.henshin.tgg.interpreter.impl.TggHenshinEGraph;
import de.tub.tfs.henshin.tgg.interpreter.impl.TggTransformationImpl;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.dialogs.TextDialog;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphFolder;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphFolderTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphTreeEditPart;

public class ValidateConstraintsAction extends SelectionAction {

	public static final String ID = "tggeditor.actions.execution.constraint.ValidateConstraints";
	private TGG tgg;
	private Graph graph;
	
	public ValidateConstraintsAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Validate Constraints on Graph");
		setToolTipText("Validate Constraints on Graph");
	}

	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selecObject = selectedObjects.get(0);
				
		if ((selecObject instanceof GraphTreeEditPart)
				&& ((GraphTreeEditPart)selecObject).getParent() instanceof GraphFolderTreeEditPart) {
			tgg = ((GraphFolder)((GraphFolderTreeEditPart)((GraphTreeEditPart)selecObject).getParent()).getModel()).getTgg();
			graph = (Graph)((GraphTreeEditPart)selecObject).getModel();
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		final TggHenshinEGraph henshinGraph = new TggHenshinEGraph(graph);
		TggTransformation trafo = new TggTransformationImpl();
		TggEngineOperational emfEngine = new TggEngineOperational(henshinGraph,trafo);
		
		List<Constraint> unsatisfiedConstraints = new ArrayList<Constraint>();
		
		for (Constraint c : tgg.getConstraints()) {
			if (c.isEnabled() && !isConstraintSatisfied(c.getRoot(), henshinGraph, emfEngine)) {
				unsatisfiedConstraints.add(c);
			}
		}
		
		Shell shell = new Shell();
		TextDialog dialog = null;
		if (unsatisfiedConstraints.isEmpty()) {
			dialog = new TextDialog(shell, "SUCCESSFUL", "The graph satisfies all graph constraints", "");
		} else {
			String cs = "";
			for (Constraint c : unsatisfiedConstraints) {
				cs += "[" + c.getComponent() + "] " + c.getName() + "\n";
			}
			dialog = new TextDialog(shell, "FAILED", "The graph does not satisfy the following graph constraints", cs);
		}
		dialog.open();
		shell.dispose();
	}
	
	private boolean isConstraintSatisfied(Formula root, TggHenshinEGraph henshinGraph, TggEngineOperational emfEngine) {
		if (root instanceof And) {
			return isConstraintSatisfied(((And)root).getLeft(), henshinGraph, emfEngine) && isConstraintSatisfied(((And)root).getRight(), henshinGraph, emfEngine);
		}
		if (root instanceof Or) {
			return isConstraintSatisfied(((Or)root).getLeft(), henshinGraph, emfEngine) || isConstraintSatisfied(((Or)root).getRight(), henshinGraph, emfEngine);
		}
		if (root instanceof Not) {
			return !isConstraintSatisfied(((Not)root).getChild(), henshinGraph, emfEngine);
		}
		if (root instanceof NestedConstraint) {
			// FIXME: Constraints are transformed into rules with application conditions in order to use the existing API of the henshin interpreter for finding matches - as this involves creating deep copies of graphs, from a performance point of view, this needs to be refactored in the future
			List<EObject> matchedNodes = new ArrayList<EObject>();
			
			Rule rule = nestedConstraint2Rule((NestedConstraint)root);
			int matchesConstraint = 0;
			int matches = 0;
			Iterator<Match> matchesIterator = emfEngine
					.findMatches(rule, henshinGraph, new MatchImpl(rule))
					.iterator();
			while (matchesIterator.hasNext()) {
				matchesConstraint++;
				Match m = matchesIterator.next();
				matchedNodes.addAll(m.getNodeTargets());
			}
					
			rule.getLhs().setFormula(null);
			
			matchesIterator = emfEngine
					.findMatches(rule, henshinGraph, new MatchImpl(rule))
					.iterator();
			while (matchesIterator.hasNext()) {
				matches++;
				Match m = matchesIterator.next();
				for (EObject n : m.getNodeTargets()) {
					if (!matchedNodes.remove(n)) {
						matchedNodes.add(n);
					} else {
						TNode node = (TNode)henshinGraph.getObject2NodeMap().get(n);
						node.setMarkerType(RuleUtil.SAT_CONSTRAINT);
					}
				}
			}
			
			// mark nodes that causes unsatisfaction of constraints
			for (EObject n : matchedNodes) {
				TNode node = (TNode)henshinGraph.getObject2NodeMap().get(n);
				node.setMarkerType(RuleUtil.NOT_SAT_CONSTRAINT);
			}
			
			return matches == matchesConstraint;
		}
		return false;
	}
	
	private Rule nestedConstraint2Rule(NestedConstraint constraint) {
		Rule rule = HenshinFactory.eINSTANCE.createRule();
		
		Graph premise = constraint.getPremise();
		Graph conclusion = ((NestedCondition)premise.getFormula()).getConclusion();
		MappingList mappings = ((NestedCondition)premise.getFormula()).getMappings();
		
		Graph newPremiseLHS = HenshinFactory.eINSTANCE.createGraph();
		Graph newConclusion = HenshinFactory.eINSTANCE.createGraph();
		NestedCondition condition = HenshinFactory.eINSTANCE.createNestedCondition();
		condition.setConclusion(newConclusion);
		newPremiseLHS.setFormula(condition);
		
		rule.setLhs(newPremiseLHS);
		rule.setCheckDangling(false);
		rule.setInjectiveMatching(true);
		
		HashMap<Node, Node> premiseNodeCreationMapping = new HashMap<Node, Node>();
		for (Node node : premise.getNodes()) {
			Node newNode = TggFactory.eINSTANCE.createTNode();
			newNode.setGraph(newPremiseLHS);
			newNode.setType(node.getType());
			newNode.setName(node.getName());
			premiseNodeCreationMapping.put(node, newNode);
			for (Attribute a : node.getAttributes()) {
				Attribute newAttr = TggFactory.eINSTANCE.createTAttribute();
				newAttr.setNode(newNode);
				newAttr.setType(a.getType());
				newAttr.setValue(a.getValue());
			}
		}
		for (Edge edge : premise.getEdges()) {
			Edge newEdge = TggFactory.eINSTANCE.createTEdge();
			newEdge.setSource(premiseNodeCreationMapping.get(edge.getSource()));
			newEdge.setTarget(premiseNodeCreationMapping.get(edge.getTarget()));
			newEdge.setType(edge.getType());
		}
		
		HashMap<Node, Node> conclusionNodeCreationMapping = new HashMap<Node, Node>();
		for (Node node : conclusion.getNodes()) {
			Node newNode = TggFactory.eINSTANCE.createTNode();
			newNode.setGraph(newConclusion);
			newNode.setType(node.getType());
			newNode.setName(node.getName());
			conclusionNodeCreationMapping.put(node, newNode);
			for (Attribute a : node.getAttributes()) {
				Attribute newAttr = TggFactory.eINSTANCE.createTAttribute();
				newAttr.setNode(newNode);
				newAttr.setType(a.getType());
				newAttr.setValue(a.getValue());
			}
		}
		for (Edge edge : conclusion.getEdges()) {
			Edge newEdge = TggFactory.eINSTANCE.createTEdge();
			newEdge.setSource(conclusionNodeCreationMapping.get(edge.getSource()));
			newEdge.setTarget(conclusionNodeCreationMapping.get(edge.getTarget()));
			newEdge.setType(edge.getType());
		}
		
		for (Mapping m : mappings) {
			Mapping mapping = HenshinFactory.eINSTANCE.createMapping(premiseNodeCreationMapping.get(m.getOrigin()), conclusionNodeCreationMapping.get(m.getImage()));
			condition.getMappings().add(mapping);
		}
		
		return rule;
	}
	
}
