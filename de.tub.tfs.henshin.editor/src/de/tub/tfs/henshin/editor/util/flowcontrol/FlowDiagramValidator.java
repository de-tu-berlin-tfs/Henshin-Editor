/**
 * FlowDiagramValidator.java
 *
 * Created 18.01.2012 - 15:41:52
 */
package de.tub.tfs.henshin.editor.util.flowcontrol;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.HenshinGraph;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.jface.dialogs.MessageDialog;

import de.tub.tfs.henshin.editor.util.JavaUtil;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.henshin.model.flowcontrol.Transition;

/**
 * @author nam
 * 
 */
public class FlowDiagramValidator {
	private List<Rule> rules;

	private FlowDiagram diagram;

	private List<FlowElement> failed;

	/**
	 * @param rules
	 * @param diagram
	 */
	public FlowDiagramValidator(List<Rule> rules, FlowDiagram diagram) {
		super();
		this.rules = rules;
		this.diagram = diagram;
		this.failed = new LinkedList<FlowElement>();
	}

	/**
     * 
     */
	public void run() {
		Graph absyGraph = HenshinFactory.eINSTANCE.createGraph();
		Map<EObject, Node> model2Node = new HashMap<EObject, Node>();

		Node root = HenshinFactory.eINSTANCE.createNode();

		root.setType(FlowControlPackage.Literals.FLOW_CONTROL_SYSTEM);

		failed.clear();

		absyGraph.getNodes().add(root);

		addNode(diagram, absyGraph, model2Node);
		addEdge(root, model2Node.get(diagram),
				FlowControlPackage.Literals.FLOW_CONTROL_SYSTEM__UNITS,
				absyGraph);

		for (FlowElement e : diagram.getElements()) {
			addNode(e, absyGraph, model2Node);

			addEdge(model2Node.get(diagram), model2Node.get(e),
					FlowControlPackage.Literals.FLOW_DIAGRAM__ELEMENTS,
					absyGraph);

			addEdge(model2Node.get(e), model2Node.get(diagram),
					FlowControlPackage.Literals.FLOW_ELEMENT__DIAGRAM,
					absyGraph);
		}

		addEdge(model2Node.get(diagram), model2Node.get(diagram.getStart()),
				FlowControlPackage.Literals.FLOW_DIAGRAM__START, absyGraph);

		for (Transition t : diagram.getTransitions()) {
			addNode(t, absyGraph, model2Node);

			addEdge(model2Node.get(diagram), model2Node.get(t),
					FlowControlPackage.Literals.FLOW_DIAGRAM__TRANSITIONS,
					absyGraph);

			addEdge(model2Node.get(t), model2Node.get(t.getNext()),
					FlowControlPackage.Literals.TRANSITION__NEXT, absyGraph);
			addEdge(model2Node.get(t.getNext()), model2Node.get(t),
					FlowControlPackage.Literals.FLOW_ELEMENT__IN, absyGraph);

			addEdge(model2Node.get(t), model2Node.get(t.getPrevous()),
					FlowControlPackage.Literals.TRANSITION__PREVOUS, absyGraph);

			if (t.isAlternate()) {
				addEdge(model2Node.get(t.getPrevous()),
						model2Node.get(t),
						FlowControlPackage.Literals.CONDITIONAL_ELEMENT__ALT_OUT,
						absyGraph);
			} else {
				addEdge(model2Node.get(t.getPrevous()), model2Node.get(t),
						FlowControlPackage.Literals.FLOW_ELEMENT__OUT,
						absyGraph);
			}
		}

		HenshinGraph henshinGraph = new HenshinGraph(absyGraph);
		EmfEngine engine = new EmfEngine(henshinGraph);

		int failed = 0;
		int numRules = rules.size();
		boolean successful = false;
		int idx = 0;

		while (!successful && failed < numRules) {
			Rule r = rules.get(idx++ % numRules);
			RuleApplication validationApp = new RuleApplication(engine, r);

			if (validationApp.apply()) {
				if (absyGraph.getNodes().size() == 1) {
					if (absyGraph
							.getNodes()
							.get(0)
							.getType()
							.equals(FlowControlPackage.Literals.FLOW_CONTROL_SYSTEM)) {
						successful = true;
					}
				}

				failed = 0;
			} else {
				failed++;
			}
		}

		if (successful) {
			MessageDialog.openInformation(null, "Validation Result",
					"Validation completed successfully.");
		} else {
			MessageDialog.openError(null, "Validation Result",
					"Validation failed.");

			Map<Node, EObject> node2Model = JavaUtil.swapKeysValues(model2Node);

			for (Node n : absyGraph.getNodes()) {
				EObject model = node2Model.get(n);

				if (model instanceof FlowElement) {
					this.failed.add((FlowElement) model);
				}
			}
		}
	}

	/**
	 * @return the failed
	 */
	public Collection<FlowElement> getFailed() {
		return Collections.unmodifiableCollection(failed);
	}

	/**
	 * @param src
	 * @param target
	 * @param type
	 * @param absyGraph
	 */
	private void addEdge(Node src, Node target, EReference type, Graph absyGraph) {
		Edge newEdge = HenshinFactory.eINSTANCE.createEdge();

		newEdge.setSource(src);
		newEdge.setTarget(target);
		newEdge.setType(type);

		absyGraph.getEdges().add(newEdge);
	}

	/**
	 * @param e
	 * @param absyGraph
	 * @param model2Node
	 */
	private void addNode(EObject e, Graph absyGraph,
			Map<EObject, Node> model2Node) {
		Node newNode = HenshinFactory.eINSTANCE.createNode();

		newNode.setType(e.eClass());
		absyGraph.getNodes().add(newNode);

		model2Node.put(e, newNode);
	}
}
