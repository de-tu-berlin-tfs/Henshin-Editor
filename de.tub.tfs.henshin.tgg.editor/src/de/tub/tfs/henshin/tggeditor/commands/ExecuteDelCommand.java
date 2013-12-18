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
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteAttributeCommand;
import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteEdgeCommand;
import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteNodeCommand;
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
public abstract class ExecuteDelCommand extends CompoundCommand {


	protected Graph graph;
	
	/**the constructor
	 * @param graph {@link ExecuteDelCommand#graph}
	 * @param opRuleList {@link ExecuteDelCommand#opRuleList}
	 */
	public ExecuteDelCommand(Graph graph, List<Rule> opRuleList) {
		super();
		this.graph = graph;
		//add(new ExecuteCCRulesCommand(graph, opRuleList));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return graph!=null;
	}

	@Override
	public void execute() {
		removeInconsistentElements();
		restrictMarkersToMarkedComponent();
		
		// execute sub commands only if there is at least one deletion command was added
		if(super.canExecute()) 
			super.execute();
	}

	private void restrictMarkersToMarkedComponent() {
		// fills translated maps with all given elements of the graph, initial
		// value is false = not yet translated
		// component(s) that shall be marked
		TNode tNode;
		for (Node node : graph.getNodes()) {
			if (node instanceof TNode) {
				tNode = (TNode) node;
				if (!isInTranslationComponent(node)) {
					removeMarkers(tNode);
				}
			}
		}
	}

	private void removeMarkers(TNode tNode) {
		tNode.setMarkerType(null);

		// handle attributes
		for (Attribute a : tNode.getAttributes()) {
			((TAttribute) a).setMarkerType(null);
		}

		// handle edges
		// node shall not be marked
		for (Edge e : tNode.getOutgoing()) {
			((TEdge) e).setMarkerType(null);
		}
		for (Edge e : tNode.getIncoming()) {
			((TEdge) e).setMarkerType(null);
		}
	}	

	private void removeInconsistentElements() {
		for (Node n : graph.getNodes()) {
			if (!isInTranslationComponent(n)) {
				// node is not in translation component - thus, it may have to
				// be deleted
				if (RuleUtil.Not_Translated_Graph.equals(((TNode) n)
						.getMarkerType()))
					// node is not consistent, thus delete it
					add(new DeleteNodeCommand(n));
				else // node is consistent, thus check its attributes and edges
				{
					for (Attribute a : n.getAttributes()) {
						if (RuleUtil.Not_Translated_Graph
								.equals(((TAttribute) a).getMarkerType()))
							// attribute is not consistent, thus delete it
							add(new DeleteAttributeCommand(a));
					}
					for (Edge e : n.getOutgoing()) {
						if (RuleUtil.Not_Translated_Graph.equals(((TEdge) e)
								.getMarkerType()))
							// edge is not consistent, thus delete it
							add(new DeleteEdgeCommand(e));
					}
				}

			}
			else // node is in translation component, handle the outgoing edges that may point outside the translation component
				for (Edge e : n.getOutgoing()) {
					if (!isInTranslationComponent(e.getTarget()) &&
							RuleUtil.Not_Translated_Graph.equals(((TEdge) e)
							.getMarkerType()))
						// edge point outside the translation component and edge is not consistent, thus delete it
						add(new DeleteEdgeCommand(e));
				}
		}
	}
	
	protected abstract boolean isInTranslationComponent(Node node);
	
	


}
