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
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tgg.TripleGraph;
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
public class ExecuteBPpgCommand extends ExecutePpgCommand {

	
	/**the constructor
	 * @param graph {@link ExecuteBPpgCommand#graph}
	 * @param opRuleList {@link ExecuteBPpgCommand#opRuleList}
	 */
	public ExecuteBPpgCommand(Graph graph, List<Rule> opRuleList) {
		super(graph,opRuleList);
	}
	
	
	@Override
	protected void fillTranslatedMaps() {
		// fills translated maps with all given elements of the graph, initial
		// value is false = not yet translated
		// component(s) that shall be marked
		for (Node node : graph.getNodes()) {
			if (isInMarkedComponent(node)) {
				if(RuleUtil.Translated_Graph.equals(((TNode)node).getMarkerType()))
					isTranslatedNodeMap.put(node, true);
				else isTranslatedNodeMap.put(node, false);
				for (Attribute a : node.getAttributes()) {
					if(RuleUtil.Translated_Graph.equals(((TAttribute)a).getMarkerType()))
						isTranslatedAttributeMap.put(a, true);
					else isTranslatedAttributeMap.put(a, false);
				}
				for (Edge e : node.getOutgoing()) {
					if (isInMarkedComponent(e.getTarget()))
						// source and target nodes of edge are in marked component
						if(RuleUtil.Translated_Graph.equals(((TEdge)e).getMarkerType()))
							isTranslatedEdgeMap.put(e, true);
						else isTranslatedEdgeMap.put(e, false);
				}

			}
		}
	}

	@Override
	protected boolean isInMarkedComponent(Node node){
		return NodeUtil.isTargetNode(node);
	};


}
