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
public class ExecuteFDelCommand extends ExecuteDelCommand {

	protected Graph graph;
	
	/**the constructor
	 * @param graph {@link ExecuteFDelCommand#graph}
	 * @param opRuleList {@link ExecuteFDelCommand#opRuleList}
	 */
	public ExecuteFDelCommand(Graph graph, List<Rule> opRuleList) {
		super(graph,opRuleList);
	}

	
	protected boolean isInTranslationComponent(Node node){
		return NodeUtil.isSourceNode(node);
	};
	
	


}
